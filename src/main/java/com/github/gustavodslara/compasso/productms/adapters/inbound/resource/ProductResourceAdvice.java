package com.github.gustavodslara.compasso.productms.adapters.inbound.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice(assignableTypes = ProductResource.class)
public class ProductResourceAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody resourceNotFoundException(MethodArgumentNotValidException ex, WebRequest request) {
        return new ExceptionBody(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }


}

@AllArgsConstructor
class ExceptionBody {
    @JsonProperty("status_code")
    private int statusCode;

    private String message;

}
