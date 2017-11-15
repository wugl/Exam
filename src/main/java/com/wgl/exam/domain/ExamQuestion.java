package com.wgl.exam.domain;


import javax.persistence.*;

@Entity
@Table(name = "rz_exam_question")
public class ExamQuestion {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "question_id")
    private Long QuestionId;

    @Column(name = "exam_id")
    private Long ExamId;

//    @Column(name = "is_delete")
//    private Integer isDelete;

    public ExamQuestion(Long examId,Long questionId) {
        QuestionId = questionId;
        ExamId = examId;
      //  isDelete=0;
    }

    public ExamQuestion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(Long questionId) {
        QuestionId = questionId;
    }

    public Long getExamId() {
        return ExamId;
    }

    public void setExamId(Long examId) {
        ExamId = examId;
    }


}
