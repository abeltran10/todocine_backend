package com.todocine;

import com.todocine.controller.UsuarioController;
import com.todocine.exceptions.BadRequestException;
import com.todocine.model.Usuario;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CheckUsuarioControllerTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckUsuarioControllerTest.class);
    @Mock
    private UsuarioService serviceMock;

    @InjectMocks
    private UsuarioController controller;

    @Test
    void inserException() throws BadRequestException {
        BadRequestException exception = null;
        Usuario user1 = new Usuario("test", "1234");
        Mockito.doThrow(new BadRequestException(HttpStatus.BAD_REQUEST, "Ya existe el usuario")).when(serviceMock).insertUsuario(user1);
        try {
            Usuario usuario = controller.insertUsuario(user1);
        } catch (BadRequestException ex) {
            exception = ex;
        }


        BadRequestException finalException = exception;
        LOG.info(finalException.toString());

        assertTrue(finalException.getMessage().contains("Ya existe el usuario"));


    }

}
