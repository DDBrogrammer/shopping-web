package edu.daidp.shoppingwebapp.controller;

import edu.daidp.shoppingwebapp.dto.FileInfoDto;
import edu.daidp.shoppingwebapp.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/files")
public class FilesController {

  @Autowired
  FilesStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("productId") long productId ) {
    String message = "";
    try {
      storageService.save(file,productId);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(message);
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    }
  }

  @GetMapping("/file")
  public ResponseEntity<List<FileInfoDto>> getListFiles(@RequestParam Long  productId) {
    List<FileInfoDto> fileInfoDtos = storageService.loadAll(productId).map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile",String.valueOf(productId), path.getFileName().toString()).build().toString();
      return new FileInfoDto(filename, url);
    }).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(fileInfoDtos);
  }

  @GetMapping("/file/{productId:.+}/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable Long productId,@PathVariable String filename) {
    Resource file = storageService.load(productId,filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @DeleteMapping("/file/{productId:.+}/{filename:.+}")
  public ResponseEntity<String> deleteFile(@PathVariable Long productId,@PathVariable String filename) {
    String message = "";
    try {
      boolean existed = storageService.delete(productId,filename);
      if (existed) {
        message = "Delete the file successfully: " + filename;
        return ResponseEntity.status(HttpStatus.OK).body(message);
      }
      message = "The file does not exist!";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    } catch (Exception e) {
      message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
  }
}
