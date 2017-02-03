package org.zerock.BoardService;

import java.util.Date;

import org.zerock.vo.LoginDTO;
import org.zerock.vo.UserVO;

public interface UserService {

	public UserVO login(LoginDTO dto) throws Exception;
	
	public void keepLogin(String id, String sessionId, Date next)throws Exception;
	
	public UserVO checkLoginBefore(String value);
	
	public void loginjoin(UserVO vo) throws Exception;
	
	public UserVO logincheck(String uid) throws Exception;
}
