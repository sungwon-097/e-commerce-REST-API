package com.cos.security1.controller;

import com.cos.security1.model.Product;
import com.cos.security1.repository.ProductRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/product")
@Api(tags = "ROLE : Manager or Admin")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/")
    public String addProducts(Product product){
        int beforePrice = product.getBeforePrice();
        int price = product.getPrice();
        String description = product.getDescription();
        String name = product.getName();

        Product productEntity = productRepository.findByName(name);
        if(productEntity == null){
            productEntity = Product.builder()
                    .beforePrice(beforePrice)
                    .price(price)
                    .score(0)
                    .description(description)
                    .name(name)
                    .build();
            productRepository.save(productEntity);
        }
        return "redirect:/product/";
    }
    @DeleteMapping("/{id}")
    public @ResponseBody
    Optional<List<Product>> deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        List<Product> products = productRepository.findAll();
        return Optional.of(products);
    }
}