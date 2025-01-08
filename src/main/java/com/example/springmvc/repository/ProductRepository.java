package com.example.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springmvc.entity.ProductEntity;
 
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

}
