package ru.netology.cloud_api.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.cloud_api.entity.UserDB;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserDB,Integer> {
    @NotNull UserDB save(@NotNull UserDB userDB);
    @Query("update UserDB  set token=null where token = :token")
    void removeToken(String token);
    UserDB findByLogin(String login);
    @Modifying
    @Query("update UserDB u set u.token = :token where u.login = :login")
    void addTokenToUser(String login, String token);
}
