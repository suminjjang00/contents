package org.zerock.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;
import org.zerock.BoardService.UserService;
import org.zerock.vo.LoginDTO;
import org.zerock.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@Inject
	private UserService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGET(@ModelAttribute("dto") LoginDTO dto){
		System.out.println(dto.toString()+"get : Controller");
	}
	@RequestMapping(value="/loginPost",method=RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession session, Model model)throws Exception{
		
		System.out.println(dto.toString()+"post : Controller");
		
		UserVO vo =service.login(dto);
		
		if(vo==null){
			return;			
		};
//		vo를 모델 객체인 userVO에 담는다. 스트링형임.		
		model.addAttribute("userVO",vo);
		
		if(dto.isUseCookie()){
			int amount = 60*20;
			
			Date sessionLimit = new Date(System.currentTimeMillis()+(1000*amount));
			
			service.keepLogin(vo.getUid(), session.getId(), sessionLimit);
		}
		
			
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws Exception{
		Object obj=session.getAttribute("login");
		if(obj !=null){
			UserVO vo = (UserVO)obj;
			session.removeAttribute("login");
			session.invalidate();
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie !=null){
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
				service.keepLogin(vo.getUid(), session.getId(), new Date());
			}
		}
	}
	
	@RequestMapping(value="/loginjoin", method=RequestMethod.GET)
	public void loginjoin()throws Exception{
		logger.info("loginjoin ====================");
	}
	@RequestMapping(value="/loginjoin", method=RequestMethod.POST)
	public void loginjoinPOST(@ModelAttribute("dto") UserVO vo)throws Exception{
		logger.info("loginjoin POST ================");
		service.loginjoin(vo);
		
	}
	
	@RequestMapping(value="/logincheck", method=RequestMethod.POST)
	public ResponseEntity<String> idcheck(@RequestBody String uid)throws Exception{
		ResponseEntity<String> entity=null;
		System.out.println(uid+"나오나?");	
		System.out.println(service.logincheck(uid));
		UserVO vo = service.logincheck(uid);		
		System.out.println(vo.toString());
		logger.info("check id ==================");
		try {
//			문자열로 리턴할 때 값이 success인지 fail인지 확인을 해야한다. 
//			if문으로 해당 서비스.메소드가 성공할 경우 success를 실패할 경우 fial을 리턴하도록 하는게 옳다고봄.
			service.logincheck(uid);
//			UserVO vo = service.logincheck(uid);			
			Integer checkid= vo.getCheckid();
			if(checkid==1){				
				entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
			}else{
				entity = new ResponseEntity<String>("FAIL",HttpStatus.OK);
			};			
			
		} catch (Exception e) {
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
