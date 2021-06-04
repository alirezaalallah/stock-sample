package com.example.stocksample.controller;

import com.example.stocksample.dao.model.Stock;
import com.example.stocksample.dto.ApiErrorDto;
import com.example.stocksample.dto.ApiResponseDto;
import com.example.stocksample.dto.Reason;
import com.example.stocksample.dto.StockRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String STOCK_SERVICE_URL = "/api/stocks";
    private static final String GET_ONE_STOCK_URL = "/api/stocks/{id}";
    private static final String DELETE_ONE_STOCK_URL = "/api/stocks/{id}";
    private static final String UPDATE_ONE_STOCK_URL = "/api/stocks/{id}";

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/givenDuplicateStockName_thenGetStockAlreadyExistsError.sql"})
    public void givenDuplicateStockNameToCreate_thenGetStockAlreadyExistsError() throws Exception {
        StockRequestDto requestDto = StockRequestDto.builder().name("Adrian").price(new BigDecimal("10000")).build();
        ApiErrorDto expectedError = ApiErrorDto.builder().reason(Reason.STOCK_ALREADY_IS_EXISTS).message("Stock is already exists").build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().errors(expectedError).badRequest().build();

        mvc.perform(post(STOCK_SERVICE_URL).content(asJsonString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    public void givenInvalidStockDataToCreate_thenGetInvalidValueErrors() throws Exception {
        StockRequestDto requestDto = StockRequestDto.builder().name("       ").price(new BigDecimal("1000.00033")).build();

        mvc.perform(post(STOCK_SERVICE_URL).content(asJsonString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Price should be in digit format integer = 10, fraction = 2")))
                .andExpect(content().string(containsString("name must not be blank")))
                .andExpect(content().string(containsString("BAD_REQUEST")))
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

    @Test
    public void givenNotExistsStockIdToFindById_thenGetStockNotFoundError() throws Exception {
        String notExistsStockId = "20";
        ApiErrorDto expectedError = ApiErrorDto.builder().reason(Reason.STOCK_NOT_FOUND).message("Stock not found").build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().errors(expectedError).notFound().build();

        mvc.perform(get(GET_ONE_STOCK_URL, notExistsStockId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/givenValidStockIdToDelete_thenDeleteSuccessfully.sql"})
    public void givenValidStockIdToDelete_thenDeleteSuccessfully() throws Exception {
        String stockId = "4";
        ApiResponseDto expectedResponse = ApiResponseDto.builder().ok().build();

        mvc.perform(delete(DELETE_ONE_STOCK_URL, stockId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/givenValidStockIdToUpdate_thenUpdateStockSuccessfully.sql"})
    public void givenValidStockIdToUpdate_thenUpdateStockSuccessfully() throws Exception {
        String stockId = "10";
        StockRequestDto requestDto = StockRequestDto.builder().name("update-stock").price(new BigDecimal("2000")).build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().ok().build();

        mvc.perform(put(UPDATE_ONE_STOCK_URL, stockId).content(asJsonString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    @Test
    public void givenValidStockDataToCreate_thenStockStoreCreatSuccessfully() throws Exception {
        StockRequestDto requestDto = StockRequestDto.builder().name("Milad").price(new BigDecimal("90000")).build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().ok().build();
        mvc.perform(post(STOCK_SERVICE_URL).content(asJsonString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(expectedResponse)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/givenRequestAllStocks_thenGetAllStocks.sql"})
    public void givenRequestAllStocks_thenGetAllStocks() throws Exception {
        List<Stock> stocks = new ArrayList<>() {{
            add(Stock.builder().uniqueId(0L).name("Hassan").currentPrice(new BigDecimal("10000.00")).lastUpdate(LocalDateTime.parse("2021-06-04T00:00:00")).build());
            add(Stock.builder().uniqueId(1L).name("Hossein").currentPrice(new BigDecimal("20000.00")).lastUpdate(LocalDateTime.parse("2021-06-03T00:00:00")).build());
            add(Stock.builder().uniqueId(2L).name("Ali").currentPrice(new BigDecimal("50000.00")).lastUpdate(LocalDateTime.parse("2021-06-02T00:00:00")).build());
        }};

        ApiResponseDto expectedResponse = ApiResponseDto.builder().ok().result(stocks).build();

        mvc.perform(get(STOCK_SERVICE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)))
        ;
    }

    @Test
    @Sql({"classpath:scripts/cleanup.sql", "classpath:scripts/givenRequestOneStockById_thenGetStockSuccessfully.sql"})
    public void givenRequestOneStockById_thenGetStockSuccessfully() throws Exception {
        Stock stock = Stock.builder().uniqueId(1L).name("Ali").currentPrice(new BigDecimal("50000.00")).lastUpdate(LocalDateTime.parse("2021-06-02T00:00:00")).build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().ok().result(stock).build();

        String stockId = "1";
        mvc.perform(get(GET_ONE_STOCK_URL, stockId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)))
        ;
    }

    @Test
    public void givenInvalidFormatStockId_thenGetInvalidValueError() throws Exception {
        String invalidFormatStockId = "abc";
        ApiErrorDto expectedError = ApiErrorDto.builder().reason(Reason.INVALID_VALUE).param("id").message("Stock id should be digit").build();
        ApiResponseDto expectedResponse = ApiResponseDto.builder().errors(expectedError).badRequest().build();

        mvc.perform(get(GET_ONE_STOCK_URL, invalidFormatStockId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(expectedResponse)));
    }

    public String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}