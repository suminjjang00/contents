package org.zerock.BoardDao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.vo.LoginDTO;
import org.zerock.vo.UserVO;

@Repository
public class UserDAOImple implements UserDAO{
	
	@Inject
	private SqlSession sqlsession;
	
	private static String namespace = "org.zerock.mappers.UserMapper";
	
	@Override
	public UserVO logincheck(String uid) throws Exception {
	
		return sqlsession.selectOne(namespace+".logincheck",uid);
	}
	
	@Override
	public void loginjoin(UserVO vo) throws Exception {
		
		sqlsession.insert(namespace+".loginjoin",vo);
	}
	
	@Override
	public UserVO checkUserSessionKey(String value) {
		
		return sqlsession.selectOne(namespace+".checkUserWithSessionKey",value);
	}
	@Override
	public void keepLogin(String uid, String sessionId, Date next) {
		
		Map<String, Object> paramMap =new HashMap<String, Object>();
		paramMap.put("uid", uid);
		paramMap.put("sessionId", sessionId);
		paramMap.put("next", next);
		
		sqlsession.update(namespace+".keepLogin",paramMap);
	}
	
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		
		
		return sqlsession.selectOne(namespace+".login",dto); 
	}
}
