package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/17.
 * 学分和绩点，还有各种排名。
 */
@Entity
public class XueFen {

    /**
     * xh : 2013005577
     * xm : 刘超
     * bjh : 软件1319
     * bm : 软件1319
     * zyh : 160101
     * zym : 软件工程
     * xsh : 16
     * xsm : 软件学院
     * njdm : 2013
     * yqzxf : 186
     * yxzzsjxf : 3.50
     * zxf : 175
     * yxzxf : 175
     * cbjgxf : 9.50
     * sbjgxf : 0
     * pjxfjd : 2.97
     * gpabjpm : 12
     * gpazypm : 478
     * pjcj : 77.30
     * pjcjbjpm : 12
     * pjcjzypm : 502
     * jqxfcj : 76.83
     * jqbjpm : 12
     * jqzypm : 491
     * tsjqxfcj : 76.83
     * tjsj : 2017-03-17 01:00:10
     * bjrs : 29
   * zyrs : 1015
     */

    @Id
    @Unique
    private String xh;
    @Property(nameInDb = "xm")
    private String xm;
    @Property(nameInDb = "bjh")
    private String bjh;
    @Property(nameInDb = "bm")
    private String bm;
    @Property(nameInDb = "zyh")
    private String zyh;
    @Property(nameInDb = "zym")
    private String zym;
    @Property(nameInDb = "xsh")
    private String xsh;
    @Property(nameInDb = "xsm")
    private String xsm;
    @Property(nameInDb = "njdm")
    private String njdm;
    @Property(nameInDb = "yqzxf")
    private String yqzxf;
    @Property(nameInDb = "yxzzsjxf")
    private String yxzzsjxf;
    @Property(nameInDb = "zxf")
    private String zxf;
    @Property(nameInDb = "yxzxf")
    private String yxzxf;
    @Property(nameInDb = "cbjgxf")
    private String cbjgxf;
    @Property(nameInDb = "sbjgxf")
    private String sbjgxf;
    @Property(nameInDb = "pjxfjd")
    private String pjxfjd;
    @Property(nameInDb = "gpabjpm")
    private String gpabjpm;
    @Property(nameInDb = "gpazypm")
    private String gpazypm;
    @Property(nameInDb = "pjcj")
    private String pjcj;
    @Property(nameInDb = "pjcjbjpm")
    private String pjcjbjpm;
    @Property(nameInDb = "pjcjzypm")
    private String pjcjzypm;
    @Property(nameInDb = "jqxfcj")
    private String jqxfcj;
    @Property(nameInDb = "jqbjpm")
    private String jqbjpm;
    @Property(nameInDb = "jqzypm")
    private String jqzypm;
    @Property(nameInDb = "tsjqxfcj")
    private String tsjqxfcj;
    @Property(nameInDb = "tjsj")
    private String tjsj;
    @Property(nameInDb = "bjrs")
    private String bjrs;
    @Property(nameInDb = "zyrs")
    private String zyrs;
    @Generated(hash = 1259555993)
    public XueFen(String xh, String xm, String bjh, String bm, String zyh,
            String zym, String xsh, String xsm, String njdm, String yqzxf,
            String yxzzsjxf, String zxf, String yxzxf, String cbjgxf, String sbjgxf,
            String pjxfjd, String gpabjpm, String gpazypm, String pjcj,
            String pjcjbjpm, String pjcjzypm, String jqxfcj, String jqbjpm,
            String jqzypm, String tsjqxfcj, String tjsj, String bjrs, String zyrs) {
        this.xh = xh;
        this.xm = xm;
        this.bjh = bjh;
        this.bm = bm;
        this.zyh = zyh;
        this.zym = zym;
        this.xsh = xsh;
        this.xsm = xsm;
        this.njdm = njdm;
        this.yqzxf = yqzxf;
        this.yxzzsjxf = yxzzsjxf;
        this.zxf = zxf;
        this.yxzxf = yxzxf;
        this.cbjgxf = cbjgxf;
        this.sbjgxf = sbjgxf;
        this.pjxfjd = pjxfjd;
        this.gpabjpm = gpabjpm;
        this.gpazypm = gpazypm;
        this.pjcj = pjcj;
        this.pjcjbjpm = pjcjbjpm;
        this.pjcjzypm = pjcjzypm;
        this.jqxfcj = jqxfcj;
        this.jqbjpm = jqbjpm;
        this.jqzypm = jqzypm;
        this.tsjqxfcj = tsjqxfcj;
        this.tjsj = tjsj;
        this.bjrs = bjrs;
        this.zyrs = zyrs;
    }
    @Generated(hash = 921174134)
    public XueFen() {
    }
    public String getXh() {
        return this.xh;
    }
    public void setXh(String xh) {
        this.xh = xh;
    }
    public String getXm() {
        return this.xm;
    }
    public void setXm(String xm) {
        this.xm = xm;
    }
    public String getBjh() {
        return this.bjh;
    }
    public void setBjh(String bjh) {
        this.bjh = bjh;
    }
    public String getBm() {
        return this.bm;
    }
    public void setBm(String bm) {
        this.bm = bm;
    }
    public String getZyh() {
        return this.zyh;
    }
    public void setZyh(String zyh) {
        this.zyh = zyh;
    }
    public String getZym() {
        return this.zym;
    }
    public void setZym(String zym) {
        this.zym = zym;
    }
    public String getXsh() {
        return this.xsh;
    }
    public void setXsh(String xsh) {
        this.xsh = xsh;
    }
    public String getXsm() {
        return this.xsm;
    }
    public void setXsm(String xsm) {
        this.xsm = xsm;
    }
    public String getNjdm() {
        return this.njdm;
    }
    public void setNjdm(String njdm) {
        this.njdm = njdm;
    }
    public String getYqzxf() {
        return this.yqzxf;
    }
    public void setYqzxf(String yqzxf) {
        this.yqzxf = yqzxf;
    }
    public String getYxzzsjxf() {
        return this.yxzzsjxf;
    }
    public void setYxzzsjxf(String yxzzsjxf) {
        this.yxzzsjxf = yxzzsjxf;
    }
    public String getZxf() {
        return this.zxf;
    }
    public void setZxf(String zxf) {
        this.zxf = zxf;
    }
    public String getYxzxf() {
        return this.yxzxf;
    }
    public void setYxzxf(String yxzxf) {
        this.yxzxf = yxzxf;
    }
    public String getCbjgxf() {
        return this.cbjgxf;
    }
    public void setCbjgxf(String cbjgxf) {
        this.cbjgxf = cbjgxf;
    }
    public String getSbjgxf() {
        return this.sbjgxf;
    }
    public void setSbjgxf(String sbjgxf) {
        this.sbjgxf = sbjgxf;
    }
    public String getPjxfjd() {
        return this.pjxfjd;
    }
    public void setPjxfjd(String pjxfjd) {
        this.pjxfjd = pjxfjd;
    }
    public String getGpabjpm() {
        return this.gpabjpm;
    }
    public void setGpabjpm(String gpabjpm) {
        this.gpabjpm = gpabjpm;
    }
    public String getGpazypm() {
        return this.gpazypm;
    }
    public void setGpazypm(String gpazypm) {
        this.gpazypm = gpazypm;
    }
    public String getPjcj() {
        return this.pjcj;
    }
    public void setPjcj(String pjcj) {
        this.pjcj = pjcj;
    }
    public String getPjcjbjpm() {
        return this.pjcjbjpm;
    }
    public void setPjcjbjpm(String pjcjbjpm) {
        this.pjcjbjpm = pjcjbjpm;
    }
    public String getPjcjzypm() {
        return this.pjcjzypm;
    }
    public void setPjcjzypm(String pjcjzypm) {
        this.pjcjzypm = pjcjzypm;
    }
    public String getJqxfcj() {
        return this.jqxfcj;
    }
    public void setJqxfcj(String jqxfcj) {
        this.jqxfcj = jqxfcj;
    }
    public String getJqbjpm() {
        return this.jqbjpm;
    }
    public void setJqbjpm(String jqbjpm) {
        this.jqbjpm = jqbjpm;
    }
    public String getJqzypm() {
        return this.jqzypm;
    }
    public void setJqzypm(String jqzypm) {
        this.jqzypm = jqzypm;
    }
    public String getTsjqxfcj() {
        return this.tsjqxfcj;
    }
    public void setTsjqxfcj(String tsjqxfcj) {
        this.tsjqxfcj = tsjqxfcj;
    }
    public String getTjsj() {
        return this.tjsj;
    }
    public void setTjsj(String tjsj) {
        this.tjsj = tjsj;
    }
    public String getBjrs() {
        return this.bjrs;
    }
    public void setBjrs(String bjrs) {
        this.bjrs = bjrs;
    }
    public String getZyrs() {
        return this.zyrs;
    }
    public void setZyrs(String zyrs) {
        this.zyrs = zyrs;
    }

}
