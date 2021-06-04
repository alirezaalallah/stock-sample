package com.example.stocksample.exception;

import com.example.stocksample.dto.ApiErrorDto;
import com.example.stocksample.dto.Reason;
import com.example.stocksample.dto.ApiResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class StockExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Reason reason = Reason.INVALID_VALUE;

        List<ApiErrorDto> errors = ex.getBindingResult().getAllErrors()
                .stream().map(
                        e -> ApiErrorDto.builder()
                                .reason(reason)
                                .message(e.getDefaultMessage())
                                .param(((FieldError) e).getField())
                                .build()
                ).collect(Collectors.toList());

        ApiResponseDto response = ApiResponseDto.builder().errors(errors).badRequest().build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(StockNotFoundException ex, WebRequest request) {
        Reason reason = Reason.STOCK_NOT_FOUND;
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(reason)
                .message(ex.getMessage())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(StockAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(StockAlreadyExistsException ex, WebRequest request) {
        Reason reason = Reason.STOCK_ALREADY_IS_EXISTS;
        ApiErrorDto error = ApiErrorDto.builder()
                .reason(reason)
                .message(ex.getMessage())
                .build();
        ApiResponseDto response = ApiResponseDto.builder().errors(error).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<ApiErrorDto> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();

            String param = propertyPath.contains(".") ?
                    propertyPath.substring(propertyPath.indexOf(".") + 1) :
                    propertyPath;

            errors.add(
                    ApiErrorDto.builder()
                            .reason(Reason.INVALID_VALUE)
                            .message(violation.getMessage())
                            .param(param)
                    .build());
        }

        ApiResponseDto response = ApiResponseDto.builder().errors(errors).badRequest().build();
        return ResponseEntity.badRequest().body(response);
    }
}
