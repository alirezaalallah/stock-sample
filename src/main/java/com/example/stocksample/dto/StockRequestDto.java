package com.example.stocksample.dto;

import com.example.stocksample.dto.validation.CreateApiGroup;
import com.example.stocksample.dto.validation.UpdateApiGroup;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class StockRequestDto implements Serializable {
    private static final long serialVersionUID = 12345678L;

    @NotNull(message = "name is required", groups = CreateApiGroup.class)
    @Pattern(regexp = "^(?!\\s*$).+", message = "name must not be blank", groups = {CreateApiGroup.class, UpdateApiGroup.class})
    private String name;

    @NotNull(message = "price is required", groups = CreateApiGroup.class)
    @DecimalMin(value = "0.0", message = "Minimum value for price is 0.0", groups = {CreateApiGroup.class, UpdateApiGroup.class})
    @Digits(integer = 10, fraction = 2, message = "Price should be in digit format integer = 10, fraction = 2", groups = {CreateApiGroup.class, UpdateApiGroup.class})
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockRequestDto that = (StockRequestDto) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private StockRequestDto request = new StockRequestDto();

        public Builder name(String name) {
            request.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            request.price = price;
            return this;
        }

        public StockRequestDto build() {
            return request;
        }

    }
}
