package com.example.springmvc.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springmvc.entity.ProductEntity;
import com.example.springmvc.model.ProductModel;
import com.example.springmvc.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    // Homepage
    @GetMapping("/homepage")
    public String homepage() {
        return "home";
    }

    // About Us page
    @GetMapping("/aboutus")
    public String about() {
        return "about";
    }

    // Contact Us page
    @GetMapping("/contactus")
    public String contact() {
        return "contact";
    }

    
    // Form to search a product by ID
    @GetMapping("/searchform")
    public String getSearchForm() {
        return "search-product";
    }

    // Search a product by ID and display the result
    @PostMapping("/searchbyid")
    public String searchById(@RequestParam Long id, Model model) {
        ProductEntity product = productService.searchById(id);
        model.addAttribute("product", product);
        return "search-product";
    }

    // Form to add a new product
    @GetMapping("/addproduct")
    public String addProductForm(Model model) {
    	ProductModel productModel =new ProductModel();
    	productModel.setMadeIn("INDIA");
    	productModel.setQuantity(2);
    	productModel.setDiscountRate(10.5);
    	model.addAttribute("productModel", productModel);
       return "add-product";
    }

    // List all products
    @GetMapping("/getallproducts")
    public String getAllProducts(Model model) {
        List<ProductEntity> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    // Save a new product
    /*
     @PostMapping("/saveproduct")
     public String saveProduct(ProductModel productModel) {
     productService.saveProductDetails(productModel);
        return "success";
    }
     
     
     */
    @PostMapping("/saveproduct")
    public String saveProduct(@Valid ProductModel productModel, BindingResult bindingResult, Model model) {

        HashMap<String, String> validationError = new HashMap<String, String>();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                validationError.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("validationErrors", validationError);
            return "add-product";
        }
        productService.saveProductDetails(productModel);

        return "redirect:/getallproducts";
    }


    // Delete a product by ID
    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/getallproducts";
    }

    // Get the edit form for a product by ID
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        ProductEntity product = productService.editProductById(id);
        model.addAttribute("product", product);
        model.addAttribute(id);
        return "edit-product";
    }

    // Update the product after editing
    @PostMapping("/updateproduct")
	public String updateProduct(ProductEntity product) {
	    productService.updateProductDetails(product);
	    return "redirect:/getallproducts"; 
	}

}
