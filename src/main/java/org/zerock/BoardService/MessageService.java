package org.zerock.BoardService;

import org.zerock.vo.MessageVO;

public interface MessageService {
	
	public void addMessage (MessageVO vo) throws Exception;
	
	public MessageVO readMessage (String uid, Integer mno) throws Exception;
}
