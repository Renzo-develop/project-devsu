package com.devsu.mstarjeta.interceptor;

import com.devsu.libreria.ApiResponse;
import com.devsu.mstarjeta.exception.InvalidBalanceException;
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
                .message("Ya se ha registrado el numero de cuenta")
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

//    @ExceptionHandler(InvalidBalanceException.class)
//    public ResponseEntity<Object> invalidBalanceError(InvalidBalanceException ex) {
//        var response = ApiResponse.builder()
//                .message("El saldo de la cuenta no puede ser negativo")
//                .response(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

}
