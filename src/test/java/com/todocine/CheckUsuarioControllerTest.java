package com.todocine;

import com.todocine.controller.UsuarioController;
import com.todocine.dto.UsuarioDTO;
import com.todocine.exceptions.BadRequestException;
import com.todocine.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "test")
public class CheckUsuarioControllerTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioControllerTest.class);
    @Mock
    private UsuarioService serviceMock;

    @InjectMocks
    private UsuarioController controller;

    @Test
    void inserException() {
        LOG.info("insertException");
        UsuarioDTO user1 = new UsuarioDTO("test", "1234");
        Mockito.doThrow(new BadRequestException("Ya existe el usuario")).when(serviceMock).insertUsuario(user1);

        try {
            controller.insertUsuario(user1);
        } catch (BadRequestException ex) {

            assertAll(
                    () -> assertEquals("Ya existe el usuario", ex.getReason()),
                    () -> assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode())
            );
        }
    }

}
