package com.lenovohit.administrator.tyut.domain;

/**
 * Created by Administrator on 2017-04-26.
 * 蹭课时需要的实体类
 */

public class Courses {
    //课程号
    private String courseCode;
    //学分
    private String xueFen;
    //考试类型，是否需要考试
    private String kaoshiType;
    //系所
    private String xiSuo;
    //教师姓名
    private String teacherName;
    //课程名称
    private String courseName;
    //id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getXueFen() {
        return xueFen;
    }

    public void setXueFen(String xueFen) {
        this.xueFen = xueFen;
    }

    public String getKaoshiType() {
        return kaoshiType;
    }

    public void setKaoshiType(String kaoshiType) {
        this.kaoshiType = kaoshiType;
    }

    public String getXiSuo() {
        return xiSuo;
    }

    public void setXiSuo(String xiSuo) {
        this.xiSuo = xiSuo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseCode='" + courseCode + '\'' +
                ", xueFen='" + xueFen + '\'' +
                ", kaoshiType='" + kaoshiType + '\'' +
                ", xiSuo='" + xiSuo + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
