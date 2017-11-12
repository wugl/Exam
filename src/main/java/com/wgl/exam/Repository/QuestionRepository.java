package com.wgl.exam.Repository;

import com.wgl.exam.domain.Question;
import com.wgl.exam.domain.QuestionType;
import com.wgl.exam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository  extends JpaRepository<Question, Long> {

    @Override
    @Query("select q from Question q where q.isDelete=0")
    List<Question> findAll();
}
