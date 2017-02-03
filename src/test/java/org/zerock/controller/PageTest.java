package org.zerock.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.BoardDao.BoardDao;
import org.zerock.vo.BoardVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class PageTest {

	@Inject
	private BoardDao boardDao;

	private static Logger logger = LoggerFactory.getLogger(PageTest.class);

	@Test
	public void testPage() throws Exception {

		int page = 4;

		List<BoardVo> list = boardDao.listPage(page);

		for (BoardVo boardVo : list) {
			logger.info(boardVo.getBno() + ":" + boardVo.getTitle());
		}
	}
}
