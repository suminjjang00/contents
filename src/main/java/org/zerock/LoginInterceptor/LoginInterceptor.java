package org.zerock.LoginInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mysql.jdbc.log.Log;

// 로그인 부분을 인터셉터를 활용하여 중간에 낚아채서 로그인 함.

public class LoginInterceptor extends HandlerInterceptorAdapter{
	private static final String LOGIN = "login";
	private static final Logger logger=LoggerFactory.getLogger(LoginInterceptor.class);
	
//	postHandle임... 일종의 핸들러 이곳에서 login이 되는 과정을 낚아챈다.
//	눈에 보이는 대로 설명을 하자면 session을 request.getsession으로 가져온다. 
//	Object 타입의 userVO를 modelmap.get UserVO 라는 이름으로 만든다.
// 	if문을 이용해 값이 없을경우 session set을 이용해 userVO를 저장함 위에있는 login이라는 것에 session형태로 저장하는 듯.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
			HttpSession session = request.getSession();
		
//		아마 controller쪽에 작성한 userVO라는 스트링형의 키에 담은 값을 아래에서 뽑아오는것 같다.
		
		Object useVO = modelAndView.getModelMap().get("userVO"); 
				
		System.out.println(useVO.toString()+"Interceptor : infoVo");
		if(useVO!=null){
			logger.info("new login success");
			
			session.setAttribute(LOGIN, useVO);
			
//			인터셉터에 의해서 로그인 성공시 아래쪽에 작성한 sboard / list로 이동한다.
//			세선으로 dest객체를 가져온 후 sendRedirect로 dest 널값이 아닐 경우
//			String형으로 dest를 변환한다 널값일 경우 / <<로 경로이동.

			Object dest = session.getAttribute("dest");
			
		System.out.println(request.getParameter("useCookie")+": cookie test");
			
			
			if(request.getParameter("useCookie") !=null){
				logger.info("Remeber me ============== auto login setting");
				Cookie loginCookie = new Cookie("loginCookie", session.getId());
				System.out.println(session.getId());
				System.out.println(loginCookie.getValue());				
				loginCookie.setPath("/");
				loginCookie.setMaxAge(60*2);
				response.addCookie(loginCookie);
			}									
			
			response.sendRedirect(dest!=null ? (String)dest : "/sboard/list");
			System.out.println((String)dest+": request url =========");
//			System.out.println(session.getAttribute(s)+=======login id======");
		}
		
	}
//	이쪽은 로그인 되어있는 정보를 지운다고만 알아두어야 할듯 일단은.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session=request.getSession();
		
		if(session.getAttribute(LOGIN) !=null){
			logger.info("cleaer login data before");
			session.removeAttribute(LOGIN);
		}
		return true;
	}
}
