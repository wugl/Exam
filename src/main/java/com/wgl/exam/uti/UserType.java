package com.wgl.exam.uti;
public enum UserType {
    STUDENT("学生", 1), TEACHER("老师", 2), MANAGER("管理员", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private UserType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (UserType c : UserType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static UserType getByIndex(int index) {
        for (UserType c : UserType.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
