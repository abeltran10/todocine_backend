package com.todocine.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocine.service.CaptchaService;
import com.todocine.utils.CaptchaDTO;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${captcha.secret.key}")
    private String secretKey;

    @Value("${captcha.verify-url}")
    private String verifyUrl;

    private final OkHttpClient httpClient = new OkHttpClient();


    @Override
    public boolean isValidToken(String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        if (token == null || token.isEmpty()) {
            return false;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("secret", secretKey)
                .add("response", token)
                .build();

        Request request = new Request.Builder()
                .url(verifyUrl)
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return false;
            }

            String responseString = response.body().string();
            CaptchaDTO captchaResponse = objectMapper.readValue(responseString, CaptchaDTO.class);

            return captchaResponse != null
                    && captchaResponse.isSuccess()
                    && captchaResponse.getScore() >= 0.5; //Umbral de confianza

        } catch (IOException e) {
            return false;
        }
    }

}
