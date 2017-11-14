package com.wgl.exam.Repository;

import com.wgl.exam.domain.Exam;

import com.wgl.exam.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Override
    @Query("select e from Exam e where e.isDelete=0")
    List<Exam> findAll();

    @Modifying(clearAutomatically = true)
    @Query("update Exam e set e.isDelete = 1 where e.id = :id")
    int del(@Param("id") Long id);

    Exam findExamByIdAndIsDelete(Long id, int isDelete);
}
