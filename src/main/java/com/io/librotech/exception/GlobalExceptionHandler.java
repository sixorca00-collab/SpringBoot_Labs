package com.io.librotech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Error de negocio");
        problemDetail.setType(URI.create("https://librotech.com/docs/errores/bad-request"));
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "los datos no estan adecuados");
        problemDetail.setTitle("Error de validacion");
        problemDetail.setType(URI.create("https://librotech.com/docs/errores/validacion"));

        //Extraemos los errores:
        Map<String, String> errores = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        problemDetail.setProperty("Errores detallados:", errores);
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}
