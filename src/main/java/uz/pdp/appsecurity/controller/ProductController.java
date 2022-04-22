package uz.pdp.appsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appsecurity.entity.Product;
import uz.pdp.appsecurity.repository.ProductRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public HttpEntity<?> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id, @RequestBody Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product editingProduct = optionalProduct.get();
            editingProduct.setName(product.getName());
            return ResponseEntity.ok(productRepository.save(editingProduct));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProduct(@PathVariable Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent())
            return ResponseEntity.ok(optionalProduct.get());
        return ResponseEntity.notFound().build();
    }

}
