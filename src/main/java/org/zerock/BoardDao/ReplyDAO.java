package org.zerock.BoardDao;

import java.util.List;

import org.zerock.vo.Criteria;
import org.zerock.vo.ReplyVO;

public interface ReplyDAO {
//	댓글 관련 인터페이스 작
	public List<ReplyVO> list(Integer bno)throws Exception;
	public void create(ReplyVO vo) throws Exception;
	public void update(ReplyVO vo) throws Exception;
	public void delete(Integer rno) throws Exception;
	
//	댓 페이징 처리 인터페이스 작성
	public List<ReplyVO> listPage(Integer bno, Criteria cri)throws Exception;
	public int count(Integer bno) throws Exception;
	
//	댓글이 삭제될 때 게시물의 번호를 알아내는 로직 작성하기.
	public int getBno(Integer rno) throws Exception;
}
