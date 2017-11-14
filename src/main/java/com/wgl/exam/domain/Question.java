package com.wgl.exam.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rz_question")
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String name;

    private Integer score;

    @Column(length = 10000)
    private String comment;

    @Column(name = "type_id")
    private Long typeId;


    @Column(name = "answer")
    private String answer;
    //private long answer;

    @Column(name = "is_delete")
    private Integer isDelete;


//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Option> answers = new ArrayList<Option>();
//
//    public List<Option> getAnswers() {
////        for (QuestionAnswer add : questionAnswers)
////            add.setQuestionId(id);
//        return answers;
//    }
//
//    public void setAnswers(List<Option> answers) {
//        this.answers = answers;
//
//    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Question(String title, String name, Integer score, String comment, Long typeId, String answer) {
        this.title = title;
        this.name = name;
        this.score = score;
        this.comment = comment;
        this.typeId = typeId;
        this.answer = answer;
        this.isDelete = 0;
    }

    public Question(String title, String name, String comment, Long typeId, String answer) {
        this.title = title;
        this.name = name;
        this.comment = comment;
        this.typeId = typeId;
        this.answer = answer;
        this.isDelete = 0;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Option> options = new ArrayList<Option>();

    public List<Option> getOptions() {
        List<Option> newOptions = new ArrayList<Option>();

        for (Option add : options) {
            if (add.getIsDelete() == 0)
                newOptions.add(add);
        }

        return newOptions;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Question() {
    }


    public Question(String name, String answer, Long typeId, Integer isDelete) {
        this.name = name;
        this.answer = answer;
        this.typeId = typeId;

        this.isDelete = isDelete;
    }

    public Question(String name, String answer, Long typeId) {
        this(name, answer, typeId, 0);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
