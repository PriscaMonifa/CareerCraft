package com.csb.utility;

import com.csb.exception.FileInvalidExtensionException;
import com.csb.exception.FileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUtility {

    public static void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileNotFoundException(
                    "Please select file to upload");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            throw new FileInvalidExtensionException(
                    "Invalid file");
        }
        String ext = filename.substring(
                filename.lastIndexOf(".") + 1
        ).toLowerCase();
        List<String> allowedExtensions = List.of(
                "png", "jpeg", "jpg",
                "pdf", "docx", "pages"
        );
        if (!allowedExtensions.contains(ext)) {
            throw new FileInvalidExtensionException(
                    ext + " not allowed");
        }
    }
}
