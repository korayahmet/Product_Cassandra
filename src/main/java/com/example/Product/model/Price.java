package com.example.Product.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("prices_new")
public class Price {

    @Id
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @Column("productId")
    private String productId;

    private List<Double> values;
    private List<String> dates;

}
