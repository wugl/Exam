package com.wgl.exam.Repository;

import com.wgl.exam.domain.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByStudentIdAndIsDelete(Long studentId, Integer isDelete);

    List<StudentAnswer> findByExamIdAndIsDelete(Long examId, Integer isDelete);

    List<StudentAnswer> findByStudentIdAndExamIdAndIsDelete(Long studentId, Long examId, Integer isDelete);

}
