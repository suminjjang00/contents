package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.BoardService.ReplyService;
import org.zerock.vo.Criteria;
import org.zerock.vo.PageMaker;
import org.zerock.vo.ReplyVO;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	
	@Inject
	private ReplyService service;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ReplyController.class);
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity=null;
		try {
			service.addReply(vo);
//		etity객체에 담아서 보내는 text내용인 SUCCESS는 test.jsp 에 있는 
//			success : function(result){
//			if(result=="SUCCESS")} 인 인자값 result를 의미한다.
			entity = new ResponseEntity<String>("SUCCESSA",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}		
		return entity;
	}
	@RequestMapping(value="/all/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno){
		ResponseEntity<List<ReplyVO>> entity=null;		
		try {			
			
			entity = new ResponseEntity<List<ReplyVO>> (service.listReply(bno),HttpStatus.OK);			
		} catch (Exception e) {
			entity = new ResponseEntity<List<ReplyVO>> (HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	@RequestMapping(value="/{rno}",method={RequestMethod.PUT,RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable ("rno") Integer rno, @RequestBody ReplyVO vo){
		
		ResponseEntity<String> entity= null;
		try {
			vo.setRno(rno);
			service.modifyReply(vo);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}				
		return entity;
	}
	@RequestMapping(value="/{rno}",method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno")Integer rno){
		ResponseEntity<String> entity= null;
		try {
			service.removeReply(rno);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}		
		return entity;			
	}
	@RequestMapping(value="/{bno}/{page}",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(
			@PathVariable("bno")Integer bno,
			@PathVariable("page")Integer page){
		
		ResponseEntity<Map<String, Object>> entity=null;		
		try {			
			Criteria cri = new Criteria();
			cri.setPage(page);					
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);						
			Map<String, Object> map = new HashMap<String,Object>();			
			List<ReplyVO> list = service.listReplyPage(bno, cri);			
			map.put("list", list);						
			int replyCount =service.count(bno);			
			pageMaker.setTotalCount(replyCount);						
			map.put("pageMaker", pageMaker);									
			entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);				
		} catch (Exception e) {
			entity = new ResponseEntity<Map<String,Object>> (HttpStatus.BAD_REQUEST);			
		}						
		return entity;
	}
}
