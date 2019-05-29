package com.cafe24.mysite.dto;

public class JSONResult {
	private String result;
	private String message;
	private Object data;
	
	//생성자와 setter를 막고  메소드로 접근
	public static JSONResult fail(String msg) {
		return new JSONResult("fail",msg,null);
	}
	
	public static JSONResult success(Object data) {
		return new JSONResult("success",null,data);
	}
	
	private JSONResult(String result,String msg,Object data) {
		this.result=result;
		this.message=msg;
		this.data=data;
	}
	
	public String getResult() {
		return result;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object getData() {
		return data;
	}
	
	
	
}
