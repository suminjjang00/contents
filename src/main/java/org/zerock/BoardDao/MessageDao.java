package org.zerock.BoardDao;

import org.zerock.vo.MessageVO;

public interface MessageDao {
	
	public void create(MessageVO vo)throws Exception;
	
	public MessageVO readMessage(Integer mid)throws Exception;
	
	public void updateState(Integer mid)throws Exception;
	

}
