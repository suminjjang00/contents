package org.zerock.BoardDao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.vo.MessageVO;

@Repository
public class MessageDaoImple implements MessageDao{
	
	@Inject
	private SqlSession sqlsession;
	
	private static String namespace = "org.zerock.mappers.messageMapper";
	
	@Override
	public void create(MessageVO vo) throws Exception {
		
		sqlsession.insert(namespace+".create",vo);
	}
	@Override
	public MessageVO readMessage(Integer mid) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne(namespace+".readMessage",mid);
	}
	@Override
	public void updateState(Integer mid) throws Exception {
		// TODO Auto-generated method stub
		sqlsession.update(namespace+".updateState",mid);
	}

}
