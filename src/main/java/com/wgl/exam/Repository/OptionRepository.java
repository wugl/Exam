package com.wgl.exam.Repository;

import com.wgl.exam.domain.Option;
import com.wgl.exam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Modifying
    @Query("update Option o set o.isDelete=1 where o.questionId=:id")
    int del(@Param("id") Long id);

}
