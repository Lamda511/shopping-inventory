package com.lambda511.model.repository;

import com.lambda511.model.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by blitzer on 22.04.2016.
 */

@Repository
public interface ProductsCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    ProductCategory findById(Integer id);

    Slice<ProductCategory> findByNameIgnoreCaseContaining(String name, Pageable pageable);
}
