package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017-04-16.
 */
@Entity
public class Course {
    //课程名称、上课教室，教师，程课编号
    @Id
    private Long id;
    private String name;
    private String room;
    private String teach;
    //开始上课节次， 一共几节课
    private int start;
    private int step;
    private String time;
    @Generated(hash = 1024503258)
    public Course(Long id, String name, String room, String teach, int start,
            int step, String time) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.teach = teach;
        this.start = start;
        this.step = step;
        this.time = time;
    }
    @Generated(hash = 1355838961)
    public Course() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRoom() {
        return this.room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public String getTeach() {
        return this.teach;
    }
    public void setTeach(String teach) {
        this.teach = teach;
    }
    public int getStart() {
        return this.start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getStep() {
        return this.step;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}