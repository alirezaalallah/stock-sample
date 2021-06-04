package com.example.stocksample.dao;

import com.example.stocksample.dao.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
    Optional<Stock> findByName(String name);
    List<Stock> findAll();
}
