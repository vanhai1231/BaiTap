package com.hutech.demo.service;
import com.hutech.demo.model.Product;
import com.hutech.demo.repository.ProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // Retrieve a product by its id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    // Add a new product to the database
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    // Update an existing product
    public Product updateProduct(@NotNull Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);
    }
    public String saveImage(MultipartFile image) throws IOException {
        String folder = "/images/";
        Path path = Paths.get(folder);

        // Ensure the directory exists
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // Save the image file
        byte[] bytes = image.getBytes();
        Path filePath = path.resolve(image.getOriginalFilename());
        Files.write(filePath, bytes);
        return "/images/" + image.getOriginalFilename();
    }
    // Delete a product by its id
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }
    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

}