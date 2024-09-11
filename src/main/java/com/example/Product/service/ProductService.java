package com.example.Product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Product.Exceptions.ExceptionsWithMessageConflict;
import com.example.Product.Exceptions.ExceptionsWithMessageNotFound;
import com.example.Product.model.Product;
import com.example.Product.repo.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    public final ProductRepository productRepository;

    //Search with a name & without a productname - If a productname is provided, return only that product
    public List<Product> getProducts(String productName){
        if (productName == null){
            return productRepository.findAll();
        } else {
            return productRepository.findAllByProductName(productName);
        }
    }

    //Check if a product already exists with the chosen productname before creating a product
    public Product createProduct(Product newproduct){
        Optional<Product> productByName = productRepository.findByProductName(newproduct.getProductName());
        if(productByName.isPresent()){
            throw new ExceptionsWithMessageConflict("Product already exists with name: " + newproduct.getProductName());
        }
        return productRepository.save(newproduct);
    }

    //Delete the product with given id if it exists or throw a not found status
    public void deleteProduct(Long productId){
        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
        } else {
            throw new ExceptionsWithMessageNotFound("Product with the id \"" + productId + "\" does not exist.");
        }
    }

    //Update product with given values (based on product's id)
    public void updateProduct(Product updatedproduct){
        //product oldproduct = productRepository.findById(productId)
        //                                .orElseThrow(() -> new ExceptionsWithMessage("product not found with id: " + productId));
        if (productRepository.existsById(updatedproduct.getId())){
            productRepository.save(updatedproduct);
        } else {
            throw new ExceptionsWithMessageNotFound("Product with the id \"" + updatedproduct.getId() + "\" does not exist.");
        }
    }

    //Find a product from parts of its name, if no name is given return all products
    public List<Product> searchProducts(String productName){
        if (productName == null){
            return productRepository.findAll();
        } else {
            return productRepository.findEverywhereCombined(productName);
        }
    }

    //!Alternative method to getProductById
    // public Product getProductById(Long productId) {
    //     Optional<Product> productByIdOptional = productRepository.findById(productId);      //find the product by the id
    //     if (productByIdOptional.isPresent()){
    //         Product productById = productByIdOptional.get();            //convert from optional if it exists
    //         return productById;     //return the Product back
    //     }
    //     return null;
    // }

    public Product getProductById(Long productId) {
        // Retrieve the product from the database using productRepository
        return productRepository.findById(productId).orElse(null);
    }
}
