package com.cafe24.mysite.repository.vo;

public class BoardVo {
	private long no;
	private String title;
	private String contents;
	private long hit;
	private String reg_date;
	private long user_no;
	private long group_no;
	private long order_no;
	private long depth;
	private String name;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public long getHit() {
		return hit;
	}
	public void setHit(long hit) {
		this.hit = hit;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public long getUser_no() {
		return user_no;
	}
	public void setUser_no(long user_no) {
		this.user_no = user_no;
	}
	public long getGroup_no() {
		return group_no;
	}
	public void setGroup_no(long group_no) {
		this.group_no = group_no;
	}
	public long getOrder_no() {
		return order_no;
	}
	public void setOrder_no(long order_no) {
		this.order_no = order_no;
	}
	public long getDepth() {
		return depth;
	}
	public void setDepth(long depth) {
		this.depth = depth;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", hit=" + hit + ", reg_date="
				+ reg_date + ", user_no=" + user_no + ", group_no=" + group_no + ", order_no=" + order_no + ", depth="
				+ depth + "]";
	}
	
	
}
