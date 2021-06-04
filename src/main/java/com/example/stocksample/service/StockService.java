package com.example.stocksample.service;

import com.example.stocksample.dao.StockRepository;
import com.example.stocksample.dao.model.Stock;
import com.example.stocksample.dto.ApiResponseDto;
import com.example.stocksample.dto.StockRequestDto;
import com.example.stocksample.exception.StockAlreadyExistsException;
import com.example.stocksample.exception.StockNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public ApiResponseDto save(StockRequestDto request) {
        requiredNotExistsStockOrElseThrows(request.getName());

        Stock stock = Stock.builder().from(request).build();
        stockRepository.save(stock);

        return ApiResponseDto.builder().ok().build();
    }

    public ApiResponseDto getStockBy(Long id) {
        return stockRepository.findById(id).map(
                stock -> ApiResponseDto.builder().ok().result(stock).build()
        ).orElseThrow(StockNotFoundException::new);
    }

    public ApiResponseDto getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return ApiResponseDto.builder().ok().result(stocks).build();
    }

    public ApiResponseDto updateStock(Long id, StockRequestDto request) {
        return stockRepository.findById(id).map(
                stock -> {

                    Optional.ofNullable(request.getName()).ifPresent(stock::setName);
                    Optional.ofNullable(request.getPrice()).ifPresent(stock::setCurrentPrice);

                    stockRepository.save(stock);
                    return ApiResponseDto.builder().ok().build();
                }
        ).orElseThrow(StockNotFoundException::new);
    }

    public ApiResponseDto deleteStock(Long id) {
        return stockRepository.findById(id).map(
                stock -> {
                    stockRepository.delete(stock);
                    return ApiResponseDto.builder().ok().build();
                }
        ).orElseThrow(StockNotFoundException::new);
    }

    private void requiredNotExistsStockOrElseThrows(String name) {
        if (stockRepository.findByName(name).isPresent()) {
            throw new StockAlreadyExistsException();
        }
    }
}
