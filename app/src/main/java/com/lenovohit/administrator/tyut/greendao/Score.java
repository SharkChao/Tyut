package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Score {
	@Property(nameInDb = "title")
	public String title;
	@Property(nameInDb = "xuefen")
	public String xuefen;
	@Property(nameInDb = "chengji")
	public String chengji;
	//用来表示是本学期成绩ben，上学期成绩shang，不及格成绩bu，还是所有成绩suo。
	@Property(nameInDb = "flag")
	public String flag;
	@Generated(hash = 2014153687)
	public Score(String title, String xuefen, String chengji, String flag) {
					this.title = title;
					this.xuefen = xuefen;
					this.chengji = chengji;
					this.flag = flag;
	}
	@Generated(hash = 226049941)
	public Score() {
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getXuefen() {
		return xuefen;
	}
	public void setXuefen(String xuefen) {
		this.xuefen = xuefen;
	}
	public String getChengji() {
		return chengji;
	}
	public void setChengji(String chengji) {
		this.chengji = chengji;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
