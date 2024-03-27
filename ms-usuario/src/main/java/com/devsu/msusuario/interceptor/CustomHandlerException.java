package com.devsu.msusuario.interceptor;

import com.devsu.msusuario.entity.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomHandlerException {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlerFormatError(ConstraintViolationException ex) {
        var response = ApiResponse.builder()
                .message("Error en validacion de datos")
                .response(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handlerDuplicateError(DataIntegrityViolationException ex) {
        var response = ApiResponse.builder()
                .message("Ya se ha registrado el documento de identidad")
                .response(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handlerDuplicateError(HttpMessageNotReadableException ex) {
        var response = ApiResponse.builder()
                .message("Ha ingresado valores no permitidos")
                .response(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
