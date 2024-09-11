package com.example.Product.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
        List<Product> findAllByProductName(String productName);
        Optional<Product> findByProductName(String productName);
        Optional<Product> findById(Long id);
        List<Product> findByProductNameContaining(String productName); //existing method

        @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "LOWER(p.productBrand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "LOWER(p.productCategory) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        public List<Product> findEverywhere(@Param("keyword") String keyword);        //custom method to find an entry

        @Query("SELECT p FROM Product p WHERE " +
                "LOWER(CONCAT(p.productName, ' ', p.productBrand, ' ', p.productCategory)) " +
                "LIKE LOWER(CONCAT('%', :keyword, '%'))")
        public List<Product> findEverywhereCombined(@Param("keyword") String keyword);        //custom method to find an entry but all the fields combined
}
