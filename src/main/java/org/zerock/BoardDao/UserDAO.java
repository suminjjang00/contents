package org.zerock.BoardDao;

import java.util.Date;

import org.zerock.vo.LoginDTO;
import org.zerock.vo.UserVO;

public interface UserDAO {
	
	public UserVO login(LoginDTO dto)throws Exception;
	
	public void keepLogin(String uid, String sessionId, Date next);
	
	public UserVO checkUserSessionKey(String value);
	
	public void loginjoin(UserVO vo) throws Exception;
	
	public UserVO logincheck(String uid)throws Exception;
}
