package com.example.springmvc.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springmvc.entity.ProductEntity;
import com.example.springmvc.model.ProductModel;
import com.example.springmvc.repository.ProductRepository;
 
@Service
public class ProductService {
	@Autowired
    ProductRepository productRepository;
	
	public ProductEntity searchById(Long id) {
		Optional<ProductEntity> optionalData = productRepository.findById(id);
		if(optionalData.isPresent())
		{	ProductEntity product = optionalData.get();
			return product;
		}
		else
		{	return null;
		}
	}

	public List<ProductEntity> getAllProducts() {
		List<ProductEntity> products = productRepository.findAll();
		return products;
	}

	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
		
	}
	
	public void saveProductDetails(ProductModel productModel) {
        double stockValue = productModel.getPrice() * productModel.getQuantity();
        double discountPrice = productModel.getPrice() * productModel.getDiscountRate() / 100;
        double taxPrice = productModel.getPrice() * 0.18;
        double offerPrice = productModel.getPrice() - discountPrice;
        double finalPrice = offerPrice + taxPrice;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productModel.getName());
        productEntity.setBrand(productModel.getBrand());
        productEntity.setMadeIn(productModel.getMadeIn());
        productEntity.setPrice(productModel.getPrice());
        productEntity.setQuantity(productModel.getQuantity());
        productEntity.setDiscountRate(productModel.getDiscountRate());
        productEntity.setDiscountPrice(discountPrice);
        productEntity.setOfferPrice(offerPrice);
        productEntity.setFinalPrice(finalPrice);
        productEntity.setStockValue(stockValue);
        productEntity.setTaxPrice(taxPrice);

        productRepository.save(productEntity);
    }

	public ProductEntity editProductById(Long id) {
		Optional<ProductEntity> product =productRepository.findById(id);
		if(product.isPresent()) {
			return product.get();
		}
		else {
			
			return null;
		}
		
		
	}

	
	

	public void updateProductDetails(ProductEntity updatedProduct) {
	   
		Optional<ProductEntity> optionalProduct = productRepository.findById(updatedProduct.getId());
	   
	    if (optionalProduct.isPresent()) {
	        ProductEntity existingProduct = optionalProduct.get();

	        // Update fields with new values
	        existingProduct.setName(updatedProduct.getName());
	        existingProduct.setBrand(updatedProduct.getBrand());
	        existingProduct.setMadeIn(updatedProduct.getMadeIn());
	        existingProduct.setPrice(updatedProduct.getPrice());
	        existingProduct.setQuantity(updatedProduct.getQuantity());
	        existingProduct.setDiscountRate(updatedProduct.getDiscountRate());

	        // Recalculate derived fields
	        double discountPrice = updatedProduct.getPrice() * updatedProduct.getDiscountRate() / 100;
	        double taxPrice = updatedProduct.getPrice() * 0.18;
	        double offerPrice = updatedProduct.getPrice() - discountPrice;
	        double finalPrice = offerPrice + taxPrice;
	        double stockValue = updatedProduct.getPrice() * updatedProduct.getQuantity();

	        existingProduct.setDiscountPrice(discountPrice);
	        existingProduct.setOfferPrice(offerPrice);
	        existingProduct.setFinalPrice(finalPrice);
	        existingProduct.setStockValue(stockValue);
	        existingProduct.setTaxPrice(taxPrice);

	        // Save the updated product back to the repository
	        productRepository.save(existingProduct);
	    }
	}


	 

}

