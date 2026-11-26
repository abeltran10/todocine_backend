package com.todocine.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comentarios")
@Validated // <-- CRUCIAL para que ConstraintViolationException funcione en parámetros
public class ComentarioController {
}
