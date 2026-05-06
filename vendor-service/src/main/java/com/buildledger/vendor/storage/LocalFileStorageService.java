package com.buildledger.vendor.storage;

import com.buildledger.vendor.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
@Slf4j
public class LocalFileStorageService {

    @Value("${app.file-storage.base-path:uploads}")
    private String basePath;

    public String store(MultipartFile file, Long vendorId) {
        try {
            Path dir = Paths.get(basePath, "vendor_" + vendorId);
            Files.createDirectories(dir);

            String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_")
                : "document.pdf";
            String filename = System.currentTimeMillis() + "_" + originalName;
            Path target = dir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            log.info("File stored: {}", target.toAbsolutePath());
            return target.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new BadRequestException("Failed to store file: " + e.getMessage());
        }
    }

    public Resource load(String fileUri) {
        try {
            Path path = Paths.get(fileUri);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            throw new BadRequestException("File not found or not readable: " + fileUri);
        } catch (MalformedURLException e) {
            throw new BadRequestException("Invalid file path: " + fileUri);
        }
    }

    public void delete(String fileUri) {
        try {
            if (fileUri != null) {
                Files.deleteIfExists(Paths.get(fileUri));
                log.info("File deleted: {}", fileUri);
            }
        } catch (IOException e) {
            log.warn("Could not delete file: {} – {}", fileUri, e.getMessage());
        }
    }
}

