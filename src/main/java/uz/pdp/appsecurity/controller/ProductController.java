package uz.pdp.appsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appsecurity.entity.Product;
import uz.pdp.appsecurity.repository.ProductRepository;

import java.util.Optional;

/*
*     @PreAuthorize()  metodga kirishdan oldin tekshiradi
 * */

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PreAuthorize(value = "hasAnyRole('MANAGER', 'DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PreAuthorize(value = "hasRole('DIRECTOR')")
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

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'MANAGER', 'DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getProduct(@PathVariable Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent())
            return ResponseEntity.ok(optionalProduct.get());
        return ResponseEntity.notFound().build();
    }

}
