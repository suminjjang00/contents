package org.zerock.BoardService;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.BoardDao.BoardDao;
import org.zerock.BoardDao.ReplyDAO;
import org.zerock.vo.Criteria;
import org.zerock.vo.ReplyVO;

@Service
public class ReplyServiceImple implements ReplyService{
	
	@Inject
	private ReplyDAO dao;
	
//	댓글 삭제시 게시글을 얻어오는것과 같이 써야 함으로 boardDao를 인젝함.
	@Inject
	private BoardDao bdao;
	
	@Transactional
	@Override
	public void addReply(ReplyVO vo) throws Exception {
//		인자값으로 들어온 vo에서 getBno를 가져와서 게시판 번호를 알아낸다 그 후 bdao.updateReplyCnt에
//		getbno를 집어넣고 증가시킬 게시물의 댓글 조회수를 +1 시킴.
		bdao.updateReplyCnt(vo.getBno(), 1);
			dao.create(vo);
	}

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		 dao.update(vo);
	}
	
	@Transactional
	@Override
	public void removeReply(Integer rno) throws Exception {
//		replydao의 getbno메소드를 이용, 인자값으로 들어온 rno를 이용하여 게시판 bno값을 가져온다.
//		가져온 게시판의 bno값을 bdao.updateReplycnt에다가 집어넣고 게시판 자체의 댓글을 갯수를 -1시킨다.
		int bno=dao.getBno(rno);
		bdao.updateReplyCnt(bno, -1);
		dao.delete(rno);
	}
	@Override
	public int count(Integer bno) throws Exception {
	
		return dao.count(bno);
	}
	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listPage(bno, cri);
	}

}
