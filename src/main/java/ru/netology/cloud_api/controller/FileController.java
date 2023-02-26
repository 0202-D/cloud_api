package ru.netology.cloud_api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloud_api.entity.File;
import ru.netology.cloud_api.service.fileservice.FileService;
import ru.netology.cloud_api.service.authservice.AuthService;
import ru.netology.cloud_api.service.userservice.UserService;

import java.io.IOException;
import java.util.Map;

@RestController
public class FileController {
    final
    AuthService authService;

    final
    FileService fileService;

    final UserService userService;


    public FileController(AuthService authService, UserService userService, FileService fileService) {
        this.authService = authService;
        this.userService = userService;
        this.fileService = fileService;

    }


    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("filename") String filename,
                                        @RequestBody MultipartFile file) throws IOException {
        fileService.upload(filename, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam("filename") String filename) {
        File file = fileService.getFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @GetMapping("/list")
    public Object showSavedFiles(@RequestParam("limit") int limit) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return fileService.show(login, limit);
    }

    @PutMapping("/file")
    public ResponseEntity<?> updateFileName(@RequestParam("filename") String fileName,
                                            @RequestBody Map<String, String> newFileNameRq) {
        fileService.updateFileName(fileName, newFileNameRq.get("filename"));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> delete(@RequestParam("filename") String fileName
    ) {
        fileService.delete(fileName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
