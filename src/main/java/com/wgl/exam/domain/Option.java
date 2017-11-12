package com.wgl.exam.domain;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "rz_option")
public class Option implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "question_id")
    private long questionId;

    @Column(name = "is_delete")
    private Integer isDelete;

    public Option() {
    }


    public Option(String title, long questionId, Integer isDelete) {
        this.content = title;
        this.questionId = questionId;
        this.isDelete = isDelete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Option(String title, long questionId) {
        this(title, questionId, 0);
    }


    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
