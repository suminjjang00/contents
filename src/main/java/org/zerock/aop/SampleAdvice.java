package org.zerock.aop;


import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {
	private static final Logger logger =org.slf4j.LoggerFactory.getLogger(SampleAdvice.class);
	
//	비포어 어노테이션은 해당 메소드를 먼저 실행한 후 target메소드가 실행되게 하는 것임.
// 	띄어쓰기 하나하나 조심해야 함.
//	before구문으로 메소드가 시작하기전에 aop를 적용시켰다. 주목할 것은 execution으로 지정한 pointcut 부분임.
//	org.zerock.BoardService.MessageService클래스의 *의 모든 메소드를 지정하는 것. 
//	지정하는 방법이 아마 키포인트가 도리듯하다.
	
	@Before("execution(* org.zerock.BoardService.MessageService*.*(..))")
	public void startLog(JoinPoint jp){
		logger.info("================================");
		logger.info("================================");		
		logger.info(Arrays.toString(jp.getArgs()));
	}
//	aroung aop어노테이션 적용.
//	proceedingJoinpoint는 joinpoin의 모든 기능을 가지고 있다고 함. joinpoint의 하위 메소드
//	해당 메소드를 이용하여 실행시간을 체크하는것임. 특이한것은 object타입으로 만들어서 리턴값을 넣어야 한다는것.
//	시작시간과 끝나는 시간을 정해놓은 뒤 proceedingJoinpoint의 오브젝트 객체를 이용 시간을 출력. 
	
	@Around("execution(* org.zerock.BoardService.MessageService*.*(..))")
	public Object timelog(ProceedingJoinPoint pjp)throws Throwable{
		long startTime = System.currentTimeMillis();
		
		logger.info(Arrays.toString(pjp.getArgs()));		
		Object result=pjp.proceed();
		long endTime = System.currentTimeMillis();
		logger.info(pjp.getSignature().getName()+":" + (endTime-startTime));
		logger.info("================================");
		
		return result;
	}
}
