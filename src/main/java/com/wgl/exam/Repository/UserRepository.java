package com.wgl.exam.Repository;

import com.wgl.exam.domain.User;
import com.wgl.exam.uti.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNameAndPasswordAndTypeAndIsDelete(String name, String password, UserType type, int isDelete);


    User findUserByNameAndTypeAndIsDelete(String name, UserType type, int isDelete);

    User findUserByIdAndIsDelete(Long id, Integer isDelete);

    User findUserByNameAndIsDelete(Long id, Integer isDelete);
}
