package com.example.stocksample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDto implements Serializable {
    private static final long serialVersionUID = 1234567L;

    private Reason reason;
    private String message;
    private String param;

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiErrorDto that = (ApiErrorDto) o;
        return reason == that.reason && Objects.equals(message, that.message) && Objects.equals(param, that.param);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, message, param);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ApiErrorDto error = new ApiErrorDto();

        public Builder reason(Reason reason) {
            error.reason = reason;
            return this;
        }

        public Builder message(String message) {
            error.message = message;
            return this;
        }

        public Builder param(String param) {
            error.param = param;
            return this;
        }

        public ApiErrorDto build() {
            return error;
        }

    }
}
