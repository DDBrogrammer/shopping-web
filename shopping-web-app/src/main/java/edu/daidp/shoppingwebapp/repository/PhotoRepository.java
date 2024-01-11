package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Photo;
import edu.daidp.shoppingwebapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface PhotoRepository  extends JpaRepository<Photo,Long> {
   void deleteByPathContaining(String path);
   Set<Photo> findPhotosByProduct(Product product);
}
