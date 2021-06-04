package com.example.stocksample.controller;

import com.example.stocksample.dto.ApiResponseDto;
import com.example.stocksample.dto.StockRequestDto;
import com.example.stocksample.dto.validation.CreateApiGroup;
import com.example.stocksample.dto.validation.StockId;
import com.example.stocksample.dto.validation.UpdateApiGroup;
import com.example.stocksample.service.StockService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/stocks")
@Validated
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto> add(@Validated(CreateApiGroup.class) @RequestBody StockRequestDto stockRequestDto) {
        ApiResponseDto response = stockService.save(stockRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto> getAll() {
        ApiResponseDto response = stockService.getAllStocks();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto> findById(@PathVariable @StockId String id) {
        ApiResponseDto response = stockService.getStockBy(Long.valueOf(id));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto> update(@PathVariable @StockId String id,
                                                 @Validated(UpdateApiGroup.class) @RequestBody StockRequestDto stockRequestDto) {
        ApiResponseDto response = stockService.updateStock(Long.valueOf(id), stockRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto> delete(@PathVariable @StockId String id) {
        ApiResponseDto response = stockService.deleteStock(Long.valueOf(id));
        return ResponseEntity.ok(response);
    }
}