package org.zerock.vo;

public class UserVO {
	
	private String uid;
	private String upw;
	private String uname;
	private Integer checkid;
//	session값에 저장한 쿠키값이 변수가 없어서 설정해야 함.
//	private String useCookie;
	

	//	public String getUseCookie() {
//		return useCookie;
//	}
//	public void setUseCookie(String useCookie) {
//		this.useCookie = useCookie;
//	}
	private int upoint;
	public Integer getCheckid() {
		return checkid;
	}
	public void setCheckid(Integer checkid) {
		this.checkid = checkid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpw() {
		return upw;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getUpoint() {
		return upoint;
	}
	public void setUpoint(int upoint) {
		this.upoint = upoint;
	}
	@Override
	public String toString() {
		return "UserVO [uid=" + uid + ", upw=" + upw + ", uname=" + uname + ", upoint="
				+ upoint + "]";
	}
	
	
}
