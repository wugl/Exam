package com.wgl.exam.Repository;

import com.wgl.exam.domain.User;
import com.wgl.exam.uti.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNameAndPasswordAndTypeAndIsDelete(String name, String password, UserType type, int isDelete);

    @Override
    @Query("select u from User u where u.isDelete=0")
    List<User> findAll();

    User findUserByNameAndTypeAndIsDelete(String name, UserType type, int isDelete);


    User findUserByIdAndIsDelete(Long id, Integer isDelete);

    User findUserByIdAndNameAndIsDelete(Long id, String name, Integer isDelete);

    User findUserByNameAndIsDelete(String name, Integer isDelete);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.isDelete = 1 where u.id = :id")
    int del(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.name = :name,u.password=:password,u.type=:type,u.email=:email ,u.phone=:phone where u.id = :id")
    int update(@Param("id") Long id, @Param("name") String name, @Param("password") String password, @Param("type") UserType type, @Param("email") String email, @Param("phone") String phone);
}
