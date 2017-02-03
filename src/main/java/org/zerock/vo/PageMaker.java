package org.zerock.vo;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {

	private int totalCount;
	private int startPage;
	private int endPage;
	private int displayPageNum=10;
	private Criteria cri;
	private boolean prev;
	private boolean next;
	
	public String makeSearch(int page){
//		((SearchCriteria)cri).getSearchType(); 이타입은 첨 본다. 뭔가 연구가 필요함.
		UriComponents uriComponents=UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
				.queryParam("keyword", ((SearchCriteria)cri).getKeyword())
				.build();
		return uriComponents.toString();				
	}
	public String makeQuery(int page){
		UriComponents uriComponents=UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.build();
		
		return uriComponents.toString(); 
	}
	
	private void calcData(){
		
//		endpage는 현재 페이지의 마지막 페이지를 의미하는 것이고, tempEndPage는 페이지 갯수가 10개 이하일 경우에
//		사용하게 된다. 흠...페이지 갯수가 10개 이하가 되었을 때, 아래에 있는 endpage의 계산식으로는마지막 페이지가
//		10으로 되기때문에 10 이하의 페이지 넘버를 매기기 위해서 템프엔드 페이지라는 값을 넣어서 변수를 조정해야한다.
	
		
//		endPage에 대한 해석 :: cri.getPage 를 가져온다 즉 현재 페이지 번호를 가져옴 예를 들어 현재 페이지 번호가 
//		3이라면 / 10 을 한 뒤 나온 값의 나머지는 버리고 x 10을 하게 된다. 그리하면 1~10 사이의 값들을 계산 할 경우
//		endPage는 무조건적으로 10의 값을 가진다. 만약 getPage의 값이 2일 경우 21~30사이의 모든 페이지 값들은
//		30이라는 값을 가지게 된다.이렇게 하여 sql문의 select * form table where ?? limit 1,10 또는
//		11,10 21,10 이런식으로 값을 가져오게 된다. 여기서 말하는 앞의 2자리숫자는 1페이지,2페이지,3페이지, 이러한 형태로 증가한다.
//		선택한 페이지의 보여줄 게시물에 해당하는 값들을 10개씩 가져오도록 설정한 것이다.
		endPage = (int)(Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum);
//		double ss=3;
//		int no=10;
//		
		startPage = (endPage-displayPageNum)+1;
//		tempEndPage의 해석 :: 페이징처리할 때 페이징의 갯수가 10 개 이하일 경우에 해당 변수를 사용한다.
//		게시물의 값이 100 이하일 경우 end페이지의 값은 무조건적으로 10을 가지게 되며. tempend의 값은 1~9의 값을 가지게된다.
//		그러니 endPage 의 값보다 > tempEnd가 작을경우 temp엔드를 사용하도록 설정한 것임.
		int tempEndPage=(int)(Math.ceil(totalCount/(double)cri.getPerPageNum()));
		if(tempEndPage<endPage){
			endPage=tempEndPage;
		}
		prev= startPage==1?false:true;
		next=endPage*cri.getPerPageNum() <= totalCount ? true:false;
	};
	
	public int getTotalCount() {
		return totalCount;
	};
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
		
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	public Criteria getCri() {
		return cri;
	}
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", displayPageNum=" + displayPageNum + ", cri=" + cri + ", prev=" + prev + ", next=" + next + "]";
	}
	
	
}
