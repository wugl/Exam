package com.wgl.exam.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rz_student_answer")
public class StudentAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @Column(name = "exam_id")
    private Long examId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "question_id")
    private Long questionId;

    private String answer;

    @Column(name="is_delete")
    private Integer isDelete;

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    @Column(name="answer_date")
    private Date answerDate = new Date();

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="question_id",insertable = false,updatable = false)
    private Question question;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="exam_id",insertable = false,updatable = false)
    private Exam exam;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    public StudentAnswer( Long examId, Long studentId, Long questionId, String answer) {
        this("",examId,studentId,questionId,answer,0);
    }
    public StudentAnswer(String name, Long examId, Long studentId, Long questionId, String answer) {
        this(name,examId,studentId,questionId,answer,0);
    }

    public StudentAnswer(String name, Long examId, Long studentId, Long questionId, String answer, Integer isDelete) {
        this.name = name;
        this.examId = examId;
        this.studentId = studentId;
        this.questionId = questionId;
        this.answer = answer;
        this.isDelete = isDelete;
    }

    public StudentAnswer() {
    }
}
