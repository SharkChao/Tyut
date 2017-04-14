package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Score {
	@Id
	public Long id;
	@Property(nameInDb = "username")
	public String username;
	@Property(nameInDb = "title")
	public String title;
	@Property(nameInDb = "xuefen")
	public String xuefen;
	@Property(nameInDb = "chengji")
	public String chengji;
	//用来表示是本学期成绩ben，上学期成绩shang，不及格成绩bu，还是所有成绩suo。
	@Property(nameInDb = "flag")
	public String flag;
	@Property(nameInDb = "isHead")
	public boolean isHead;
	@Generated(hash = 103937926)
	public Score(Long id, String username, String title, String xuefen,
									String chengji, String flag, boolean isHead) {
					this.id = id;
					this.username = username;
					this.title = title;
					this.xuefen = xuefen;
					this.chengji = chengji;
					this.flag = flag;
					this.isHead = isHead;
	}
	@Generated(hash = 226049941)
	public Score() {
	}
	public Long getId() {
					return this.id;
	}
	public void setId(Long id) {
					this.id = id;
	}
	public String getUsername() {
					return this.username;
	}
	public void setUsername(String username) {
					this.username = username;
	}
	public String getTitle() {
					return this.title;
	}
	public void setTitle(String title) {
					this.title = title;
	}
	public String getXuefen() {
					return this.xuefen;
	}
	public void setXuefen(String xuefen) {
					this.xuefen = xuefen;
	}
	public String getChengji() {
					return this.chengji;
	}
	public void setChengji(String chengji) {
					this.chengji = chengji;
	}
	public String getFlag() {
					return this.flag;
	}
	public void setFlag(String flag) {
					this.flag = flag;
	}
	public boolean getIsHead() {
					return this.isHead;
	}
	public void setIsHead(boolean isHead) {
					this.isHead = isHead;
	}

}
