package com.klutzer.wineshop.resources;

public class BeanResponse {
	
	private Boolean success;
	private String msg;
	private String msgDetail;
	
	public Boolean isSuccess() {
		return success;
	}
	public BeanResponse setSuccess(Boolean success) {
		this.success = success;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public BeanResponse setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public String getMsgDetail() {
		return msgDetail;
	}
	public BeanResponse setMsgDetail(String msgDetail) {
		this.msgDetail = msgDetail;
		return this;
	}
}