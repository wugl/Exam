package com.wgl.exam.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity

@Table(name = "rz_exam")
public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "total_score")
    private Float totalScore;
    @Column(name = "pass_score")
    private Float passScore;
    @Column(name = "total_time")
    private Integer totalTime;

    //@Temporal(TemporalType.DATE)
    @Column(name = "exam_date")
    private Date examDate;


    @Column(name = "is_delete")
    private Integer isDelete;


    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name="rz_exam_question",
            joinColumns=@JoinColumn(name="exam_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="question_id", referencedColumnName="id"))
    private List<Question> questions = new ArrayList<>();


    public List<Question> getQuestions() {
        List<Question> result = new ArrayList<>();
        for(Question q:questions){
            if(q.getIsDelete()==0){
                result.add(q);
            }
        }
        return result;
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

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    public Float getPassScore() {
        return passScore;
    }

    public void setPassScore(Float passScore) {
        this.passScore = passScore;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Exam() {
    }

    public Exam(String name, Float totalScore, Float passScore, Integer totalTime, Date examDate) {
        this(name,totalScore,passScore,totalTime,examDate,0);
    }

    public Exam(String name, Float totalScore, Float passScore, Integer totalTime, Date examDate, Integer isDelete) {
        this.name = name;
        this.totalScore = totalScore;
        this.passScore = passScore;
        this.totalTime = totalTime;
        this.examDate = examDate;
        this.isDelete = isDelete;
    }
}
