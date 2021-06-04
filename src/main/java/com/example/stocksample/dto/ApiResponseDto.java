package com.example.stocksample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private Object result;
    private List<ApiErrorDto> errors;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<ApiErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiErrorDto> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponseDto that = (ApiResponseDto) o;
        return status == that.status && Objects.equals(result, that.result) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, result, errors);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ApiResponseDto apiResponseDto = new ApiResponseDto();

        public Builder status(HttpStatus httpStatus) {
            apiResponseDto.status = httpStatus;
            return this;
        }

        public Builder result(Object result) {
            apiResponseDto.result = result;
            return this;
        }

        public Builder errors(ApiErrorDto... error) {
            apiResponseDto.errors = Arrays.asList(error);
            return this;
        }

        public Builder errors(List<ApiErrorDto> errors) {
            apiResponseDto.errors = errors;
            return this;
        }


        public Builder ok() {
            apiResponseDto.status = HttpStatus.ACCEPTED;
            return this;
        }

        public Builder badRequest() {
            apiResponseDto.status = HttpStatus.BAD_REQUEST;
            return this;
        }

        public ApiResponseDto build() {
            return apiResponseDto;
        }
    }
}
