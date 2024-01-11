package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Photo;
import edu.daidp.shoppingwebapp.entity.Product;
import edu.daidp.shoppingwebapp.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface VideoRepository extends JpaRepository<Video,Long> {
    void deleteByPathContaining(String path);

    Set<Video> findVideosByProduct(Product product);
}
