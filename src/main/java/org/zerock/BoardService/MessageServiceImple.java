package org.zerock.BoardService;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.BoardDao.MessageDao;
import org.zerock.BoardDao.PointDao;
import org.zerock.vo.MessageVO;

@Service
public class MessageServiceImple implements MessageService{
	
	@Inject
	private MessageDao msdao;
	
	@Inject
	private PointDao pdao;
	
//	add 메세지에서 보낼 메세지를 작성하고 작성된 메세지를 보낼 경우 포인트값이 10점 증가한다
//	대상은 vo.getSender()로 가져옴.
	@Override
	public void addMessage(MessageVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		msdao.create(vo);
		
		pdao.updatePoint(vo.getSender(), 10);
	}
//	이쪽은 리드 메세지임 읽으면 포인트가 올라간다. 그리고 읽었을때 읽은 시간을 표시하기 위해 업데이트를 사용하는듯?
//	읽은 사람 포인트를 올린후 클라이언트 쪽으로 메세지를 전송.
	@Override
	public MessageVO readMessage(String uid, Integer mid) throws Exception {
		
		msdao.updateState(mid);
		pdao.updatePoint(uid, 5);
		
		return msdao.readMessage(mid);
	}	
}
