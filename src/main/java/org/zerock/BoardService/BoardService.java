package org.zerock.BoardService;

import java.util.List;

import org.zerock.vo.BoardVo;
import org.zerock.vo.Criteria;
import org.zerock.vo.SearchCriteria;

public interface BoardService {
	
	public void regist(BoardVo board)throws Exception;
	
	public BoardVo read(Integer bno)throws Exception;
	
	public void modify(BoardVo board)throws Exception;
	
	public void remove(Integer bno) throws Exception;
	
	public List<BoardVo> listAll()throws Exception;
	
	public List<BoardVo> listCriteria(Criteria cri)throws Exception;
	
	public int listCountCriteria(Criteria cri)throws Exception;
	
	public List<BoardVo> listSearch(SearchCriteria cri)throws Exception;
	
	public int listSearchCount(SearchCriteria cri) throws Exception;
	
	public List<String> getAttach(Integer bno) throws Exception;
	
}
