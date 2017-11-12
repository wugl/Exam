package com.wgl.exam.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rz_question_type")
public class QuestionType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;



    @Column(name="is_delete")
    private Integer isDelete;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="type_id")
    private List<Question> questions = new ArrayList<Question>();

    public List<Question> getQuestions() {
//        for (Question add :questions)
//            add.setTypeId(id);

        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public QuestionType() {
    }

    public QuestionType(String name, Integer isDelete) {
        this.name = name;
        this.isDelete = isDelete;
    }

    public QuestionType(String name) {
        this(name,0);
    }
}
