package uz.pdp.appsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appsecurity.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
