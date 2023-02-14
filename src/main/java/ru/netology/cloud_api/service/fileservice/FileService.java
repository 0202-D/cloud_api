package ru.netology.cloud_api.service.fileservice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloud_api.dto.FileResponse;
import ru.netology.cloud_api.entity.File;


import java.io.IOException;
import java.util.List;
@Service
public interface FileService {
      void upload(String file,MultipartFile files) throws IOException;
      List<FileResponse> show(String login,int limit);

      void delete(String fileName);

      File getFile(String filename);

      File updateFileName(String fileName,String newName);
}
