package com.wgl.exam.Repository;

import com.wgl.exam.domain.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByStudentIdAndAndIsDelete(Long studentId, Integer isDelete);

    List<StudentAnswer> findByExamIdAndAndIsDelete(Long examId, Integer isDelete);

}
