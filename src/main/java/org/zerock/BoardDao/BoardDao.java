package org.zerock.BoardDao;

import java.util.List;

import org.zerock.vo.BoardVo;
import org.zerock.vo.Criteria;
import org.zerock.vo.SearchCriteria;

public interface BoardDao {
	
	public List<String> getAttach(Integer bno)throws Exception;
	
//	파일 업로드 인터페이스 부분
	public void addAttach(String fullName)throws Exception;
	
//	드디어 조회 페이지 처리임.
	public void updateViewCnt(Integer bno)throws Exception;
	
	
//	해당 인터페이스의 메소드 이름명은 mapper쪽과 동일함.
	
	public void create(BoardVo vo)throws Exception;
	
	public BoardVo read(Integer bno)throws Exception;
	
	public void update(BoardVo vo)throws Exception;
	
	public void delete(Integer bno)throws Exception;
	
	public List<BoardVo> listAll()throws Exception;
	
	public List<BoardVo> listPage(int page)throws Exception;
	
	public List<BoardVo> listCriteria(Criteria cri)throws Exception;
	
	public int countPaging(Criteria cri)throws Exception;
	
// 검색의 결과물을 받아오는 dao생성함.
	public List<BoardVo> listSearch(SearchCriteria cri)throws Exception;
//	검색 후 페이징 결과를 받아오는 메소드를 생성함.
	public int listSearchCount(SearchCriteria cri)throws Exception;
//	댓글 숫자를 변경하는 메소드 추가하기
	public void updateReplyCnt(Integer bno, int amount)throws Exception;
}
