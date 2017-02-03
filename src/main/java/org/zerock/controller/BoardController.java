package org.zerock.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextLiveBeansView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.BoardService.BoardService;
import org.zerock.vo.BoardVo;
import org.zerock.vo.Criteria;
import org.zerock.vo.PageMaker;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {

	public static final Logger logger=LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService boardService;

//	@RequestMapping("/getAttach/{bno}")
//	@ResponseBody
//	public List<String> getAttach(@PathVariable("bno")Integer bno)throws Exception{
//
//		return boardService.getAttach(bno);
//	}


	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void registerGET(BoardVo board, Model model)throws Exception{
		logger.info("register げt。。。");
	}
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registPOST(BoardVo board, RedirectAttributes rttr)throws Exception{
		logger.info("regist post!!..");
		logger.info(board.toString());
		boardService.regist(board);

		rttr.addFlashAttribute("result","success");

		return "redirect:/board/listPage";
	}
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public void listAll(Model model)throws Exception{
		logger.info("show all list..");
		model.addAttribute("list", boardService.listAll());
	}
	@RequestMapping(value="/read", method=RequestMethod.GET )
	public void read(@RequestParam("bno") int bno, Model model)throws Exception{
//		boardService.read(bno)를 담아가는 변수명이 없음.. 이럴 경우 boardSerice.read(bno)로 읽어온 값의 데이터가 BoardV타 이므
//		보이드로 출력되는 값 또한 BoardVo 다만 소문조 boardVo 로 출력이 된다(앞의 첫글자만 소문자로 변경됨.)
		model.addAttribute(boardService.read(bno));
	};
	@RequestMapping(value="/remove")
	public String remove(@RequestParam("bno")int bno,RedirectAttributes rttr)throws Exception{
		logger.info("remove method.....");
		boardService.remove(bno);
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listAll";
	};
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public void modifyGET(Model model,int bno)throws Exception{
//		read.jsp 에서 수정 버튼을 클릭시 이쪽으로 호출이 됨.
//		호출 된 상태에서 파라미터로 bno를 받아오고 받아온 것으로 서비스.리드를 호출함.
//		그리고 모델객체에 담아서 다시 뷰 modify로 전송한다.
		logger.info("modify....get >> post go");
//		여기 또한 마찬가지로 변수명을 입력하지 않았다. 입력하지 않았을 경우 boadVo로 인식됨.
//		호출된 메소드의 타입으로 인식 된다고 보면 된다.		
		model.addAttribute(boardService.read(bno));
	};
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyPOST(BoardVo boardVo, RedirectAttributes rttr)throws Exception{
		logger.info("board modify.........");
		boardService.modify(boardVo);
//		알티티알. 플래쉬 속성으로 전송한다 ( 히든타입으로 알고있음. )
//		석세스 값을 엠에스쥐에 담아서 전송하고 엠에스쥐 값을 받아서 특정 문자열과 일치하면 처리완료 메세지가 뜸.
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listAll";
	};
//	@RequestMapping(value="/listCri", method=RequestMethod.GET)
//	public void listCriteriGET(Criteria cri,Model model)throws Exception{
//		logger.info("listCri request.....");
//		model.addAttribute("list",boardService.listCriteria(cri));
//	};
// 클라이언트 측에서 criteria를 인자값으로 받아오기 때문에 크리테리아를 넣어줌.
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(Model model, @ModelAttribute("cri") Criteria cri)throws Exception{
		logger.info("page.........");
//		list에 boardService.listcriteri(cri)로 나온 결과물을 담아서 전송함.
		model.addAttribute("list",boardService.listCriteria(cri));

		PageMaker pageMaker = new PageMaker();
//		totlaCount가 실행되면 calcData()메소드를 호출하기 때문에 setcri를 먼저 인자값으로 저장함.
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.listCountCriteria(cri));

		model.addAttribute("pageMaker",pageMaker);
	};
//	인자값으로 들어온 cri는 사용하지 않고 그대로 방출한다. readPage쪽으로.
//	그리고 jsp페이지 쪽에서 cri.page 이러한 형태로 사용하게 된다.
//	인자값으로 받아온 cri를 모델 객체에 담아서 보내거나 @ModelAttribute를 이용해서 담아야 한다.
//	ModelAttribute를 이용할 경우 Model객체를 이용하지 않아도 자동을 반환이 된다.
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public void read(@RequestParam("bno") int bno,@ModelAttribute("cri")Criteria cri, Model model)throws Exception{

//		model.addAttribute("cri",cri);
		model.addAttribute(boardService.read(bno));
	}
	@RequestMapping(value="/removePage", method=RequestMethod.POST)
	public String remove(@RequestParam("bno")int bno,@ModelAttribute("cri")Criteria cri, RedirectAttributes rttr)throws Exception{
		boardService.remove(bno);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("msg","success");
		return "redirect:/board/listPage";
	};

	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modify(@RequestParam("bno") int bno,@ModelAttribute("cri")Criteria cri, Model model)throws Exception{
//		model.addAttribute("cri",cri);
		model.addAttribute(boardService.read(bno));
	};
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modifyPagingPOST(BoardVo vo,Criteria cri, RedirectAttributes rttr)throws Exception{
		boardService.modify(vo);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("msg","success");
		return "redirect:/board/listPage";
	}


};
