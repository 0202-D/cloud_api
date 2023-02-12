package ru.netology.cloud_api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.cloud_api.entity.File;
import ru.netology.cloud_api.entity.UserDB;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<File, Integer> {

    Optional<File> findByName(String name);


    void removeFileByName(String fileName);

    Optional<List<File>> findAllFilesByUser(UserDB user);

}
