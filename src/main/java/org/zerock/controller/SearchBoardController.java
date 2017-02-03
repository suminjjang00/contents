package org.zerock.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.BoardService.BoardService;
import org.zerock.vo.BoardVo;
import org.zerock.vo.PageMaker;
import org.zerock.vo.SearchCriteria;

@Controller
@RequestMapping(value = "/sboard/**")
public class SearchBoardController {
	private Logger logger = LoggerFactory.getLogger(SearchBoardController.class);

	@Inject
	private BoardService boardService;

//	responseBody를 이용하여 http객체로 응답한다. 
//	인자값으로 들어온 pathvariable은 request mapping 의 {bno} 와 매칭이 됨.
//	특정 getAttach/게시물 번호로 들어갈 경우 bno 값으로 service단을 실행하게 
	@RequestMapping("/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno")Integer bno)throws Exception{
		
		return boardService.getAttach(bno);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage( @ModelAttribute("cri")SearchCriteria cri, Model model) throws Exception {
		logger.info(cri.toString());
		// model.addAttribute("list",boardService.listCriteria(cri));
		model.addAttribute("list", boardService.listSearch(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		// pageMaker.setTotalCount(boardService.listCountCriteria(cri));
		pageMaker.setTotalCount(boardService.listSearchCount(cri));

		model.addAttribute("pageMaker", pageMaker);
	};

	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno,
			
			@ModelAttribute("cri")SearchCriteria cri, 
			Model model)throws Exception {
		
		
		model.addAttribute(boardService.read(bno));
	};
//	readyPage에서 모덽에트리뷰트를 지우니 리무브 페이지로 넘어갈때 에러가 난다.
//	searchType과 keyword값이 널로 떠서 뷰 페이지로 넘어간 후 값이 없다고 에러가 나옴.
//	아래쪽 리무브 페이지에선 @ModelAttribute를 쓰지 않아도 된다. 이유인 즉, 
//	위쪽에 있는 리드페이지 쪽에서 받아온 ModelAttribute를 뷰페이진 readPage에서 히든값으로 
//	리무브 페이지로 넘기기 때문. 그리고 리무브쪽에선 RequestAttribute로 cri.get으로 가져온 값을 page에 담아서
//	sboard/list쪽으로 리턴해준다. 
	@RequestMapping(value="/removePage",method=RequestMethod.POST)
	public String remove(@RequestParam("bno")int bno,
			SearchCriteria cri,
			RedirectAttributes rttr) throws Exception{
//		model객체에 담아서 가는게 아니다. 이유는 삭제이기 때문에 model객체에 담아서 굳이 재 전송할 필요가 없기 때문이다.
		boardService.remove(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		rttr.addAttribute("msg","success");
		return "redirect:/sboard/list";
	};
//	추측해본다. modifyPage.jsp를 가보면 form hidden타입으로 전송하는걸 주석처리한것을 확인할 수 있다.
//	그 이유는 hiddentype으로 전송을 하지 않아도 아래에있는 post형태로 전송한 뒤 list쪽에서
//	히든타입으로 전송하지 않은 것들이 확인이 되기 때문.
//	이걸 추측해본다면 get타입인 @ModelAttribute로 cri를 받아온 후 다시 자기 자신을 호출하기 때문에
//	히든 타입으로 list쪽으로 보내지 않아도 된다는 추측이 든다. 위와는 다른것이
//	페이지 이동이 없기 때문에. 즉 자기 자신을 호출하기 때문이라는 생각이 듬.(위에는 다른 페이지로 (read->remove)이동하기 때문에 
//	ModelAttribute를 사용해야 한다고 생각이 든다.
//	실험 결과 리드페이지에 있는 히든값을 지우고 전송하니 에러가 나온다. 리드 페이지에서 히든값으로 전송할 값들이 없기 때문에
//	즉 cri로 반환된 값들을 담아서 보내야하는데 hidden으로 전송을 하지 않았기 때문에 에러가 난다.
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modify(@RequestParam("bno")int bno,
			Model model,
			@ModelAttribute("cri")SearchCriteria cri)throws Exception{
		
		model.addAttribute(boardService.read(bno));		
	};
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modifyPage(BoardVo boardVo, 
			SearchCriteria cri, 
			RedirectAttributes rttr)throws Exception{
		
		boardService.modify(boardVo);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		rttr.addAttribute("msg","successmodify");
		
		return "redirect:/sboard/list";
	};
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void register()throws Exception{
		
		logger.info("register.get============");
		
	};
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String registerPOST(BoardVo boardVo,RedirectAttributes rttr)throws Exception{
		
		boardService.regist(boardVo);
		rttr.addAttribute("msg","successregister");
		
		return "redirect:/sboard/list";
	}
};
