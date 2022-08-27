package com.cos.security1.controller;

import com.cos.security1.model.Product;
import com.cos.security1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public @ResponseBody List<Product> retrieveAllProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/name={name}")
    public @ResponseBody Product retrieveProductsByName(@PathVariable String name){
        Product product = productRepository.findByName(name);
        product.setViews(product.getViews()+1);
        return product;
    }
}