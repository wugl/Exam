package com.wgl.exam.domain;


import com.wgl.exam.uti.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rz_user")
public class User  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private UserType type;
    @Column(unique=true)
    private String name;
    private String password;
    private String email;
    private String phone;
    @Column(name="reg_time")
    private Date regTime = new Date();
    @Column(name="is_delete")
    private Integer isDelete;

    public User() {
    }

    public User(UserType type, String name, String password, String email, String phone) {
        this.type = type;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isDelete = 0;
    }

    public User(UserType type, String name, String password, String email, String phone, Date regTime, Integer isDelete) {
        this.type = type;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.regTime = regTime;
        this.isDelete = isDelete;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public User(UserType type, String name, String password, String email, String phone,Date regTime  ) {
        this(type,name,password,email,phone,regTime,0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
