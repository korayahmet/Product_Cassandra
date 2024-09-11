package com.example.Product.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
// import org.springframework.data.cassandra.repository.Query;

import com.example.Product.model.Price;

public interface PriceRepository extends CassandraRepository<Price, UUID>{
    // @Query("SELECT * FROM prices WHERE productId=?0")
    Price findByProductId(String productId);

    // @Query("SELECT * FROM prices WHERE productId=?0")
    Optional<Price> findAllByProductId(String productId);
}
