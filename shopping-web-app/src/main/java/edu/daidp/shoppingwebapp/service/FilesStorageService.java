package edu.daidp.shoppingwebapp.service;

import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.controller.FilesController;
import edu.daidp.shoppingwebapp.entity.Photo;
import edu.daidp.shoppingwebapp.entity.Product;
import edu.daidp.shoppingwebapp.entity.Video;
import edu.daidp.shoppingwebapp.repository.PhotoRepository;
import edu.daidp.shoppingwebapp.repository.ProductRepository;
import edu.daidp.shoppingwebapp.repository.VideoRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FilesStorageService {

    private final ProductRepository productRepository;

    private final VideoRepository videoRepository;
    private final PhotoRepository photoRepository;


    private final String ROOT_PATH = "uploads";

    private final Path rootPath = Paths.get(ROOT_PATH);

    public FilesStorageService(ProductRepository productRepository, VideoRepository videoRepository,
                               PhotoRepository photoRepository) {
        this.productRepository = productRepository;
        this.videoRepository = videoRepository;
        this.photoRepository = photoRepository;
    }

    public void init() {
        try {
            Path path = Files.createDirectories(rootPath);

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file, Long productId) throws NoContentFoundException {
        Optional<Product> product = productRepository.findById(BigInteger.valueOf(productId));
        if (product.isEmpty()) {
            throw new NoContentFoundException("Product not fount");
        }
        try {
            // Get the current year and month
            String folderName = String.valueOf(productId);

            // Create a folder based on the year and month if it doesn't exist
            Path productFolderPath = this.rootPath.resolve(folderName.toString());
            if (!Files.exists(productFolderPath)) {
                Files.createDirectories(productFolderPath);
            }

            // Check file type (image or video) based on the file extension
            boolean isImage = isImageByExtension(file);
            boolean isVideo = isVideoByExtension(file);

            // Define folders for images and videos
            Path imagesFolderPath = productFolderPath.resolve("images");
            Path videosFolderPath = productFolderPath.resolve("videos");

            // Create folders for images and videos if they don't exist
            if (!Files.exists(imagesFolderPath)) {
                Files.createDirectories(imagesFolderPath);
            }
            if (!Files.exists(videosFolderPath)) {
                Files.createDirectories(videosFolderPath);
            }

            // Determine the appropriate folder to save the file based on its type
            Path destinationFolder = productFolderPath; // default to the month folder
            if (isImage) {
                destinationFolder = imagesFolderPath;
            } else if (isVideo) {
                destinationFolder = videosFolderPath;
            }

            // Copy the file into the determined folder with a unique filename
            String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());
            Path filePath = destinationFolder.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Print file information after saving
            System.out.println("File saved successfully.\nPath: " + filePath.toString() +
                                       "\nFilename: " + filePath.getFileName() +
                                       "\nFile Size: " + Files.size(filePath) + " bytes");
            String filename = filePath.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", String.valueOf(productId), filename).build().toString();
            if (isImage) {

                photoRepository.save(Photo.builder().product(product.get()).name(filePath.getFileName().toString()).extension(
                        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                                .toLowerCase()).url(url).path(filePath.toString()).build());
            } else if (isVideo) {
                videoRepository.save(Video.builder().name(filePath.getFileName().toString()).product(product.get()).extension(
                        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                                .toLowerCase()).url(url).path(filePath.toString()).build());
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException("Failed to save the file: " + e.getMessage());
        }
    }

    private boolean isImageByExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return isImageByExtension(filename);
    }

    private boolean isImageByExtension(String filename) {
        if (filename != null) {
            String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("jfif") || extension.equals(
                    "png") || extension.equals("gif") /* Add other image extensions */;
        }
        return false;
    }

    private boolean isVideoByExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            return extension.equals("mp4") || extension.equals("avi") || extension.equals(
                    "mov") /* Add other video extensions */;
        }
        return false;
    }

    private String generateUniqueFilename(String originalFilename) {
        // Generate a unique filename here (you can use UUID or any unique logic)
        // For simplicity, appending current timestamp to the filename
        return System.currentTimeMillis() + "_" + originalFilename;
    }

    public Resource load(Long productId,String filename) {
        try {
            Path file=null;
            if(isImageByExtension(filename)){
                file = rootPath.resolve(String.valueOf(productId)).resolve("images").resolve(filename);
            }else {
                file = rootPath.resolve(String.valueOf(productId)).resolve("videos").resolve(filename);
            }
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean delete(Long productId,String filename) {
        try {
            Path file=null;
            if(isImageByExtension(filename)){
                file = rootPath.resolve(String.valueOf(productId)).resolve("images").resolve(filename);
                photoRepository.deleteByPathContaining(filename);
            }else {
                file = rootPath.resolve(String.valueOf(productId)).resolve("videos").resolve(filename);
                videoRepository.deleteByPathContaining(filename);
            }
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootPath.toFile());
    }

    public Stream<Path> loadAll(Long productId) {
        try {
            Path  videoPath=this.rootPath.resolve( String.valueOf( productId)).resolve("videos");
            Path  photoPath=this.rootPath.resolve( String.valueOf( productId)).resolve("images");
            Stream<Path> images= Files.walk(photoPath, 1)
                    .filter(path -> !path.equals(photoPath))
                    .map(photoPath::relativize);
            Stream<Path> videos= Files.walk(videoPath, 1)
                    .filter(path -> !path.equals(videoPath))
                    .map(videoPath::relativize);
            return  Stream.concat(images,videos);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

}
