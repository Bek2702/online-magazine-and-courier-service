package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.File;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    //local uchun
    private static final Path root = Paths.get("src/main/resources");
    //server uchun
//    private static final Path root= Paths.get("/root");

    public File saveFiles(MultipartFile file) {
        String director = checkingAttachmentType(file);

        if (director == null) {
            throw RestException.restThrow("Faylni yukalsh uchun papka topilmadi");
        }

        long currentTimeMillis = System.currentTimeMillis();

        Path resolve = root.resolve(director + "/" + currentTimeMillis + "-" + file.getOriginalFilename());
        File files;

        try {
            Files.copy(file.getInputStream(), resolve, StandardCopyOption.REPLACE_EXISTING);
            File v = new File();
            v.setFileName(file.getOriginalFilename());
            v.setFileName(director + "/" + currentTimeMillis + "-" + file.getOriginalFilename().toString());
            v.setContentType(file.getContentType());
            v.setSize(file.getSize());
            files =  fileRepository.save(v);
        } catch (IOException e) {
            throw RestException.restThrow(e.getMessage());
        }

        return files;
    }


    private String checkingAttachmentType(MultipartFile file) {
        String filename = file.getOriginalFilename();

        assert filename != null;
        if (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".webp")
                || filename.endsWith(".PNG") || filename.endsWith(".JPG") || filename.endsWith(".JPEG") || filename.endsWith(".WEBP")) {
            return "img";
        } else if (checkFile(filename)) {
            return "files";
        }
        return null;
    }

    private boolean checkFile(String filename) {
        return filename.endsWith(".pdf") || filename.endsWith(".docx") ||
                filename.endsWith(".pptx") || filename.endsWith(".zip") ||
                filename.endsWith(".PDF") || filename.endsWith(".DOCX") ||
                filename.endsWith(".PPTX") || filename.endsWith(".ZIP");
    }
}
