package com.lambda511.model.repository;

import com.lambda511.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by blitzer on 29.06.2016.
 */
@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    Slice<Product> findByNameIgnoreCaseContainingAndBarcodeIgnoreCaseContainingOrderByNameAsc(String name, String barcode, Pageable pageable);

    Slice<Product> findByStockLastUpdateDateBetweenOrderByStockLastUpdateDateDesc(Date startDate, Date endDate, Pageable pageable);

    Slice<Product> findByImageFileNameNotNullOrderByStockLastUpdateDateDesc(Pageable pageable);

    @Query("SELECT DISTINCT p.barcode " +
            "FROM Product p " +
            "WHERE (p.name LIKE %:name% AND p.barcode LIKE %:barcode%) " +
            "ORDER BY p.barcode ASC")
    Slice<String> findBarcodeByNameAndBarcode(@Param("name") String name, @Param("barcode") String barcode, Pageable pageable);

    @Query("SELECT DISTINCT p.name " +
            "FROM Product p " +
            "WHERE (p.name LIKE %:name% AND p.barcode LIKE %:barcode%) " +
            "ORDER BY p.name ASC")
    Slice<String> findNameByNameAndBarcode(@Param("name") String name, @Param("barcode") String barcode, Pageable pageable);

}
