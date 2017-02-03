package org.zerock.vo;

public class Criteria {
	
	private int page;
	private int perPageNum;
	
	public Criteria(){
		this.page=1;
		this.perPageNum=10;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		if(page<=0){
			page=1;
			return;
		}
			this.page=page;
				
	}
//	method for Mybatis SQL Mapper
//	마이바티스 자체적으로 해당 메소드가 존재함 이것이랑 연결되어서 퍼페이지넘을 가져오게 된다.
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		if(perPageNum <=0 || perPageNum > 100){
			this.perPageNum = perPageNum;
			return;
		}else
			this.perPageNum = perPageNum;			
		
	};
//	method for Mybatis SQL Mapper
//	마이바티스 자체적으로 해당 메소드가 존재함 이것이랑 연결되어서 페이지스타트를 가져오게 된다.
	public int getPageStart(){
		return (this.page-1) * perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
	
};
