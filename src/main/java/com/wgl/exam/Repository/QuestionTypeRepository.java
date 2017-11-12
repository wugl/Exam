package com.wgl.exam.Repository;

import com.wgl.exam.domain.QuestionType;
import com.wgl.exam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
    QuestionType findByNameAndIsDelete(String name,Integer isDelete);

    QuestionType findByName(String name);

    @Override
    @Query("select qt from QuestionType qt where qt.isDelete=0")
    List<QuestionType> findAll();


    @Modifying(clearAutomatically = true)
    @Query("update QuestionType qt set qt.name = :name where qt.id = :id")
    int update(@Param("id") Long id, @Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Query("update QuestionType qt set qt.isDelete = 1 where qt.id = :id")
    int del(@Param("id") Long id);

}
