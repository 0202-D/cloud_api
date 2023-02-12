package ru.netology.cloud_api.service.fileservice;

import javassist.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloud_api.dao.FilesRepository;
import ru.netology.cloud_api.dao.UserRepository;
import ru.netology.cloud_api.dto.FileResponse;
import ru.netology.cloud_api.entity.File;
import ru.netology.cloud_api.entity.UserDB;
import ru.netology.cloud_api.exception.IncorrectDataException;
import ru.netology.cloud_api.service.authservice.AuthServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    final
    AuthServiceImpl authService;
    final
    FilesRepository filesRepository;
    final
    UserRepository userRepository;

    public FileServiceImpl(FilesRepository filesRepository, AuthServiceImpl authService, UserRepository userRepository) {
        this.filesRepository = filesRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    public void upload(String fileName, MultipartFile files) throws IOException {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        File file = File.builder().name(fileName).data(files.getBytes()).size(files.getSize())
                .user(userRepository.findByLogin(login)).createDate(LocalDate.now()).build();

        filesRepository.save(file);
    }

    @Override
    public List<FileResponse> show(String login, int limit) {
        Optional<List<File>> fileList = filesRepository.findAllFilesByUser(userRepository.findByLogin(login));
        return fileList.get().stream().map(f -> new FileResponse(f.getName(), f.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String fileName) {
        filesRepository.removeFileByName(fileName);
    }

    @Override
    public File getFile(String filename) {
        Optional<File> optionalFile = filesRepository.findByName(filename);
        if (optionalFile.isEmpty()) {
            throw new IncorrectDataException("Файлпа с таким именем не существует");
        }
        return optionalFile.get();
    }

    @Override
    public File updateFileName(String fileName, String newName) {
        Optional<File> optionalFile = filesRepository.findByName(fileName);
        if (optionalFile.isEmpty()) {
            throw new IncorrectDataException("Файлпа с таким именем не существует");
        }
        File file = optionalFile.get();
        file.setName(newName);
        filesRepository.save(file);
        return file;
    }


}
