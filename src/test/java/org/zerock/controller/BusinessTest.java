package org.zerock.controller;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.BoardDao.BoardDao;
import org.zerock.BoardService.BoardServiceimple;
import org.zerock.vo.BoardVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BusinessTest {

	@Inject
	private BoardServiceimple boardimple;
	
	@Test
	public void testCreatecode()throws Exception{
		BoardVo vo=new BoardVo();
		vo.setTitle("wpahrdla");
		vo.setContent("zhsxpsxmdla");
		vo.setWriter("글쓴이ㅣㅁ");
		boardimple.regist(vo);
	}
}
