package com.scholanova.projectstore.repositories;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.models.Stocks;
import com.scholanova.projectstore.models.Store;
import com.scholanova.projectstore.repositories.StocksRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(StocksRepository.class)
@JdbcTest
class StocksRepositoryTest {

	  
    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final Integer storeId = 2;
    
    @BeforeEach
    void startStore() {
    	
    	String storeName = "Auchan";
    	Store store = new Store (storeId, storeName);
    	insertStore(store);
    }
    
    @AfterEach
    void cleanUp() {
    	JdbcTestUtils.deleteFromTables(jdbcTemplate, "STOCKS");
    	JdbcTestUtils.deleteFromTables(jdbcTemplate, "STORES");
        
    }

        @Test
        void whenNoStocksWithThatId_thenThrowsException() throws Exception {
            // Given
            Integer id = 1000;

            // When & Then
            assertThrows(ModelNotFoundException.class, () -> {
            	stocksRepository.getById(id);
            });
        }

        @Test
        void whenStocksExists_thenReturnsTheStocks() throws Exception {
            // Given
            Integer id = 1;
            Stocks stocks = new Stocks(id, "Pomme", "Fruit", 20);
            insertStocks(stocks, storeId);

            // When
            Stocks extractedStocks = stocksRepository.getById(id);

            // Then
            assertThat(extractedStocks).isEqualToComparingFieldByField(stocks);
        }


        @Test
        void whenCreateStocks_thenStocksIsInDatabaseWithId() {
            // Given
            String stocksName = "Pomme";
            String type = "Fruit";
            Integer value = 20;
            Stocks stocksToCreate = new Stocks(null, stocksName, type, value);

            // When
            Stocks createdStocks = stocksRepository.create(stocksToCreate, storeId);

            // Then
            assertThat(createdStocks.getId()).isNotNull();
            assertThat(createdStocks.getName()).isEqualTo(stocksName);
            assertThat(createdStocks.getType()).isEqualTo(type);
            assertThat(createdStocks.getValue()).isEqualTo(value);
        }

    private void insertStore(Store store) {
        String query = "INSERT INTO STORES " +
                "(ID, NAME) " +
                "VALUES ('%d', '%s')";
        jdbcTemplate.execute(
                String.format(query, store.getId(), store.getName()));
    }
    
    private void insertStocks(Stocks stocks, Integer idStore) {
        String query = "INSERT INTO STOCKS " +
                "(ID, NAME, TYPE, VALUE, IDSTORE) " +
                "VALUES ('%d', '%s', '%s', '%d', '%d')";
        jdbcTemplate.execute(
                String.format(query, stocks.getId(), stocks.getName(), stocks.getType(), stocks.getValue(), idStore));
    }
    
    
//    @Nested
//    class Test_delete {
//
//        @Test
//        void whenNoStockssWithThatId_thenThrowsException() throws Exception {
//            // Given
//            Integer id = 1;
//
//            // When & Then
//            assertThrows(ModelNotFoundException.class, () -> {
//                StocksRepository.delete(id);
//            });
//        }
//
//        @Test
//        void whenStocksExists_thenReturnsNothing() throws Exception {
//            // Given
//            Integer id = 1;
//            Stocks Stocks = new Stocks(id, "Carrefour");
//            insertStocks(Stocks);
//
//            // When
//            StocksRepository.delete(id);
//
//            // Then
//            assertThrows(ModelNotFoundException.class, () -> {
//                StocksRepository.getById(id);
//            });
//        }
//    }
}