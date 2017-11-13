package com.wgl.exam.Repository;

import com.wgl.exam.domain.Question;
import com.wgl.exam.domain.QuestionType;
import com.wgl.exam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository  extends JpaRepository<Question, Long> {

    @Override
    @Query("select q from Question q where q.isDelete=0")
    List<Question> findAll();


    Question findQuestionByIdAndIsDelete(Long id, Integer isDelete);


    @Modifying(clearAutomatically = true)
    @Query("update Question q set q.name = :name,q.typeId=:typeId,q.title=:title,q.comment=:comment where q.id = :id")
    int update(@Param("id") Long id, @Param("typeId") Long typeId, @Param("name") String name, @Param("title") String title, @Param("comment") String comment);

    @Modifying(clearAutomatically = true)
    @Query("update Question q set q.isDelete = 1 where q.id = :id")
    int del(@Param("id") Long id);
}
