package com.todocine.service.impl;

import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl {

    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Usuario) {
            return ((Usuario) principal).getId();
        }

        return null;  // Si no se encuentra un UsuarioDetails
    }

}