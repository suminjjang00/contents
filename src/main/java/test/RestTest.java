package test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class RestTest {

	@RequestMapping("/hello")
	public String test(){
		return "Rest hi";
	}
	
}
