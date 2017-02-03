package org.zerock.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.BoardDao.BoardDao;
import org.zerock.vo.BoardVo;
import org.zerock.vo.Criteria;
import org.zerock.vo.SearchCriteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class DaoTest {
	@Inject
	private BoardDao dao;
	
	private static Logger logger = LoggerFactory.getLogger(DaoTest.class);

	@Test
	public void testCreate() throws Exception {

		BoardVo board = new BoardVo();
		board.setTitle("새글");
		board.setContent("새글임");
		board.setWriter("글쓴이1");
		dao.create(board);
	}

	@Test
	public void testUpdate() throws Exception {
		BoardVo boardVo = new BoardVo();
		boardVo.setBno(1);
		boardVo.setTitle("수정테스트");
		boardVo.setContent("수정된 내용");
		dao.update(boardVo);
	}

	@Test
	public void testDelete() throws Exception {
		dao.delete(1);
	}

	@Test
	public void testPage() throws Exception {
		int page = 3;
		List<BoardVo> list = dao.listPage(page);
//		for (BoardVo boardVo : list) {
//			System.out.println(boardVo.getBno() + ":" + boardVo.getTitle());
//		};
	};
	@Test
	public void testListCriteria()throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(4);
		cri.setPerPageNum(10);
		List<BoardVo> list= dao.listCriteria(cri);
//		for(BoardVo test:list){
//			System.out.println(test.getBno()+":"+test.getTitle()+"listTest");
//			
//		};
		
	};
	@Test
	public void testURI()throws Exception{
		UriComponents uriComponents= UriComponentsBuilder.newInstance()
				.path("/board/read")
				.queryParam("bno", 12)
				.queryParam("perPageNum", 20)
				.build();
		logger.info("/board/read?bno=12&perPageNum=20");
		logger.info(uriComponents.toString());
	};
	@Test
	public void testSearchSQL()throws Exception{
		SearchCriteria cri=new SearchCriteria();
		cri.setPage(1);
		cri.setPerPageNum(10);
		cri.setKeyword("수");
		cri.setSearchType("w");
		logger.info("=========================");
		
		List<BoardVo> boardVos = dao.listSearch(cri);
		for(BoardVo list:boardVos){
			logger.info(list.getBno()+": "+ list.getTitle());
		}
		logger.info("========================");
		logger.info("COUNT:"+dao.listSearchCount(cri));
	};

};
