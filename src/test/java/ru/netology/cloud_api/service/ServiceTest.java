package ru.netology.cloud_api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cloud_api.dao.FilesRepository;
import ru.netology.cloud_api.dao.UserRepository;
import ru.netology.cloud_api.dto.FileResponse;
import ru.netology.cloud_api.entity.File;
import ru.netology.cloud_api.entity.UserDB;
import ru.netology.cloud_api.exception.IncorrectDataException;
import ru.netology.cloud_api.service.fileservice.FileServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ServiceTest {
    @Mock
    FilesRepository filesRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    FileServiceImpl fileService;

    private final File file = new File();
    private final List<File> fileList = new ArrayList<>();
    private final UserDB user = UserDB.builder().login("user").password("user").build();
    private final String FILENAME = "file";



    @Test
    void getFilesTest() {
        int limit = 1;
        file.setName(FILENAME);
        fileList.add(file);
        given(filesRepository.findAllFilesByUser(user)).willReturn(Optional.of(fileList));
        given(userRepository.findByLogin(user.getLogin())).willReturn(user);
        List<FileResponse> responseList = fileService.show(user.getLogin(), limit);
        Assertions.assertEquals(responseList.get(0).getFilename(), file.getName());
    }

    @Test
    void deleteFileTest() {
        fileService.delete( FILENAME);
        verify(filesRepository, times(1)).removeFileByName(FILENAME);
    }
    @Test
    void getFileTest() {
        file.setName(FILENAME);
        given(filesRepository.findByName(FILENAME)).willReturn(Optional.of(file));
        File newFile = fileService.getFile( FILENAME);
        Assertions.assertEquals(file.getName(), newFile.getName());
    }

    @Test
    void updateFileTest() {
     final String NEWFILENAME = "newFileName";
        given(filesRepository.findByName(FILENAME)).willReturn(Optional.of(file));
        fileService.updateFileName(FILENAME, NEWFILENAME);
        verify(filesRepository, times(1)).save(file);
    }
    @Test
    void updateFileTestAssertThrows() throws IncorrectDataException {
        Exception thrown = Assertions.assertThrows(IncorrectDataException.class, () -> fileService.updateFileName(FILENAME, FILENAME));
        Assertions.assertEquals("Файла с таким именем не существует", thrown.getMessage());

    }

}
