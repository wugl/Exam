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

    @Column(length = 1000)
    private String title;

    @Column(name = "type_id")
    private long typeId;


    @Column(name = "answer")
    private String answer;
    //private long answer;

    @Column(name = "is_delete")
    private Integer isDelete;


    @OneToMany(cascade = CascadeType.ALL)

       private List<Option> answers = new ArrayList<Option>();

    public List<Option> getQuestionAnswers() {
//        for (QuestionAnswer add : questionAnswers)
//            add.setQuestionId(id);
        return answers;
    }

    public void setQuestionAnswers(List<Option> questionAnswers) {
        this.answers = questionAnswers;

    }



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Option> options = new ArrayList<Option>();

    public List<Option> getOptions() {
//        for (Option add : options)
//            add.setQuestionId(id);
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Question() {
    }


    public Question(String title, String answer, long typeId, Integer isDelete) {
        this.title = title;
        this.answer = answer;
        this.typeId = typeId;

        this.isDelete = isDelete;
    }

    public Question(String title, String answer, long typeId) {
        this(title, answer, typeId, 0);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }


    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
