package org.zerock.LoginInterceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.UriComponentsBuilderMethodArgumentResolver;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;
import org.zerock.BoardService.UserService;
import org.zerock.vo.UserVO;

// 해당 클래스는 현재 사용자가 로그인한 상태의 사용자인지를 체크하는 역활임.
public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	@Inject
	private UserService service;
	
	private static final Logger logger= LoggerFactory.getLogger(AuthInterceptor.class);
//	사용자가 원하는 화면으로 이동하도록 메소드 작성하기.. 게시물에 글을 쓰려고 헀다가 로그인이 안되어있을 시 로그인 화면으로 이동함
//	그 후 로그인을 하면 게시물의 글을 쓰던 곳으로 페이징처리가 되어야 하는데 그게아니라 메인의 홈 쪽으로 이동하게 된다.
//	사용자가 원하는 위치로 이동하도록 하는 메소드 작성하기.
	private void saveDest(HttpServletRequest req){
//		요청경로의 uri를 받아옴. query를 받아옴.
		String uri=req.getRequestURI();
		String query=req.getQueryString();
//		if 쿼리가 널 혹은 query.equals객체가 널일 경우
//		query자체가 ""<<로 들어감 없다는거지. 아닐경우 ?+query명이 들어감.
		
		if(query==null || query.equals("null")){
			query="";
		}else{
			query ="?" + query;
		}
//		요청경로의 메소드가 GET 타입일 경우 req.getSession요청 세션을 가져오고 거기에 
//		setAttribute로 속성명dest를 uri+query 두개를 합친 것으로 저장함 .
//		uri+query를 저장한다.
//		이곳에서 저장된 session값은 loginInterceptor 클래스로 이동함.
		if(req.getMethod().equals("GET")){
			logger.info("dest: "+(uri+query));
			req.getSession().setAttribute("dest", uri+query);
		}
	}
	
//	if문의 return 값에 true를 주게되면 ... 프리핸들값이 false로 들어가는데 어떻게 되는거지..?
//	어뜨케 되긴요 false값이 들어가게 되면 preHandle 메소드가 종료 된답니다.
//	servlet 쪽에서 등록한 interceptor 매핑에 보내기 전에 prehandle 쪽에서 낚아챈다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
			HttpSession session=request.getSession();
//		쿠키를 사용하는 곳, 이곳에서 쿠키를 저장하고 내보낸다.	
			if(session.getAttribute("login")==null){
				logger.info("currnet user is not logined");
//				preHandle interceptor로 잡아낸 request요청을 saveDest에 인자로 넘김 그리고 위쪽 메소드에서
//				Query를 확인한다. 
				saveDest(request);
				
				Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
				
				if(loginCookie !=null){
					
					UserVO userVO = service.checkLoginBefore(loginCookie.getValue());					
					logger.info("USERVO:"+userVO);
					if(userVO !=null){
						session.setAttribute("login", userVO);
						return true;
					}
				}
				
				response.sendRedirect("/user/login");
				return false;
			}		
			return true;
	}
	

}
