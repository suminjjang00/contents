package org.zerock.BoardService;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.BoardDao.BoardDao;
import org.zerock.vo.BoardVo;
import org.zerock.vo.Criteria;
import org.zerock.vo.SearchCriteria;

@Service
public class BoardServiceimple implements BoardService{
	
	@Inject
	private BoardDao boardDao;
	
	@Override
	public List<String> getAttach(Integer bno) throws Exception {
	
		return boardDao.getAttach(bno);
	}
	
	@Transactional
	@Override
	public void regist(BoardVo board) throws Exception {
				
		boardDao.create(board);
		
		String[] files=board.getFiles();
		
		if(files == null){
			return;		
		}
		for(String fileName:files){
			boardDao.addAttach(fileName);
		}
		
	}
	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public BoardVo read(Integer bno) throws Exception {

		boardDao.updateViewCnt(bno);
		
		return boardDao.read(bno);
	}
	@Override
	public void modify(BoardVo board) throws Exception {
		// TODO Auto-generated method stub
		boardDao.update(board);
	}
	@Override
	public void remove(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		boardDao.delete(bno);
	}
	@Override
	public List<BoardVo> listAll() throws Exception {
		// TODO Auto-generated method stub
		return boardDao.listAll();
	}
	@Override
	public List<BoardVo> listCriteria(Criteria cri) throws Exception {
	
		return boardDao.listCriteria(cri);
	}
	@Override
	public int listCountCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.countPaging(cri);
	}
	@Override
	public List<BoardVo> listSearch(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.listSearch(cri);
	}
	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.listSearchCount(cri);
	}
	
}
