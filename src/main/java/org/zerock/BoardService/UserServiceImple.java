package org.zerock.BoardService;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.BoardDao.UserDAO;
import org.zerock.vo.LoginDTO;
import org.zerock.vo.UserVO;

@Service
public class UserServiceImple implements UserService{
	
	@Inject
	private UserDAO dao;
	
	
	@Override
	public UserVO logincheck(String uid) throws Exception {
	
		return dao.logincheck(uid);
	}
	
	@Override
	public void loginjoin(UserVO vo) throws Exception {
	
		dao.loginjoin(vo);
	}
	@Override
	public UserVO checkLoginBefore(String value) {
		
		return dao.checkUserSessionKey(value);
	}
	@Override
	public void keepLogin(String id, String sessionId, Date next) throws Exception {
		dao.keepLogin(id, sessionId, next);
		
	}
	
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
	
		return dao.login(dto);
	}
}
