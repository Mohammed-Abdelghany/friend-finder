package com.example.friendfinder.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UploadHelper {



    public static Map<String, String> uploadFile(MultipartFile file,FileType fileType
    ) throws IOException {

        String uploadDirectory = "src/main/resources/static/assets/uploads/";

        // Validate file presence
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Validate content type


        // Validate file size
        if (file.getSize() >fileType.getMaxSize()) {
            throw new RuntimeException("Max Size for your file is "+fileType.getMaxSize());
        }

        // Validate filename
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new RuntimeException("Invalid filename");
        }

        // Validate extension
        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!file.getContentType().startsWith(fileType.getContentTypePrefix())) {
            throw new RuntimeException("Invalid file type");
        }

        if (file.getSize() > fileType.getMaxSize()) {
            throw new RuntimeException("File too large");
        }
        if (!fileType.getAllowedExtensions().contains(extension)) {
            throw new RuntimeException("Invalid extension");
        }


        // Create directory
        Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String uniqueFilename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(uniqueFilename).normalize();

        // Security check
        if (!filePath.startsWith(uploadPath)) {
            throw new RuntimeException("Invalid file path");
        }

        // Save file
    Files.copy(file.getInputStream(), filePath);

        // Success response
        Map<String, String> response = new HashMap<>();
        response.put("url", "/uploads/" + uniqueFilename);
        response.put("filename", uniqueFilename);

        return response;
    }



    private static String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1);
    }
}





//    @PostMapping("/delete")
//    public ResponseEntity<Map<String, String>> deleteImage(@RequestParam String filename) {
//        Path uploadDirectory = Paths.get("src/main/resources/static/assets/uploads/").toAbsolutePath().normalize();
//        Path filePath = uploadDirectory.resolve(filename).normalize();
//        try {
//            Files.deleteIfExists(filePath);
//            return ResponseEntity.ok(Map.of("message", "File deleted successfully"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to delete file"));
//        }
//    }


