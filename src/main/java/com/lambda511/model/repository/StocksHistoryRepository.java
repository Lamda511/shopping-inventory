package com.lambda511.model.repository;

import com.lambda511.model.StockHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by blitzer on 29.06.2016.
 */
@Repository
public interface StocksHistoryRepository extends JpaRepository<StockHistory, Long> {

    Slice<StockHistory> findByProductIdOrderByDateDesc(Long id, Pageable pageable);

    @Modifying
    @Query("DELETE FROM StockHistory sh WHERE sh.product.id = :productId")
    void deleteStockHistoryByProductId(@Param("productId") Long productId);
}
