package com.wgl.exam.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rz_exam_question")
public class ExamQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name="exam_id")
    private Long exmaId;
    @Column(name="question_id")
    private Long questionId;

    @Column(name="is_delete")
    private Integer isDelete;

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

    public Long getExmaId() {
        return exmaId;
    }

    public void setExmaId(Long exmaId) {
        this.exmaId = exmaId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public ExamQuestion() {
    }

    public ExamQuestion(String name, Long exmaId, Long questionId) {
        this.name = name;
        this.exmaId = exmaId;
        this.questionId = questionId;
    }

    public ExamQuestion(String name, Long exmaId, Long questionId, Integer isDelete) {
        this.name = name;
        this.exmaId = exmaId;
        this.questionId = questionId;
        this.isDelete = isDelete;
    }
}
