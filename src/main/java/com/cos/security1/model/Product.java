package com.cos.security1.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer views;
    private Integer price;
    private Integer beforePrice;

    @CreationTimestamp
    private Timestamp submitDate;

    @Builder
    public Product(Long id, String name, String description, Integer views, Integer price, Integer beforePrice, Timestamp submitDate) {
        this.id = id;
        this.name = name;
        this.views = views;
        this.description = description;
        this.price = price;
        this.beforePrice = beforePrice;
        this.submitDate = submitDate;
    }
}