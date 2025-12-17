package com.op.chatopback.service;

import com.op.chatopback.config.FileStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final String uploadDir;

    @Autowired
    public FileStorageService(FileStorageConfig config) {
        this.uploadDir = config.getUploadDir();
    }

    public String saveRentalImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            String filename =
                    UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path directory = Paths.get(uploadDir, "rentals");
            Path filePath = directory.resolve(filename);

            Files.createDirectories(directory);
            Files.copy(file.getInputStream(), filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            return "http://localhost:8080/uploads/rentals/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }

    }
}
