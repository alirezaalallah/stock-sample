package com.example.stocksample.dao.model;

import com.example.stocksample.dto.StockRequestDto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_Id")
    private Long uniqueId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @Basic
    @Column(name = "last_updated")
    private LocalDateTime lastUpdate;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @PreUpdate
    public void update() {
        this.lastUpdate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(uniqueId, stock.uniqueId) && Objects.equals(name, stock.name) && Objects.equals(currentPrice, stock.currentPrice) && Objects.equals(lastUpdate, stock.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, name, currentPrice, lastUpdate);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Stock stock = new Stock();

        public Builder from(StockRequestDto request) {
            stock.name = request.getName();
            stock.currentPrice = request.getPrice();
            stock.lastUpdate = LocalDateTime.now();
            return this;
        }

        public Builder uniqueId(Long uniqueId) {
            stock.uniqueId = uniqueId;
            return this;
        }

        public Builder name(String name) {
            stock.name = name;
            return this;
        }

        public Builder currentPrice(BigDecimal currentPrice) {
            stock.currentPrice = currentPrice;
            return this;
        }

        public Builder lastUpdate(LocalDateTime lastUpdated) {
            stock.lastUpdate = lastUpdated;
            return this;
        }

        public Stock build() {
            return stock;
        }
    }

}
