package com.wgl.exam.Repository;

import com.wgl.exam.domain.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {


    @Modifying(clearAutomatically = true)
    @Query(value = "delete from  rz_exam_question   where rz_exam_question.exam_id=?1",nativeQuery = true)
    int delByExamId(Long id);



}
