package org.zerock.BoardDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.vo.BoardVo;
import org.zerock.vo.Criteria;
import org.zerock.vo.SearchCriteria;

// 리포지토리 등록함.
// 리포지토리가 등록이 되었는지 확인을 해야하는 작업이 필요하다. 
@Repository
public class BoardImple implements BoardDao {
	
	// sqlsession을 인젝트해서 가져와서 빈을 자동생성해서 사용하는듯.
	@Inject
	private SqlSession session;
	
	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".getAttach",bno);
	}
	
	@Override
	public void addAttach(String fullName) throws Exception {
	
		session.insert(namespace+".addAttach",fullName);
	}
	
	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".updateViewCnt",bno);
	}
	
//	게시물의 댓글 조회수 소스.
	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("amount", amount);
		
		session.update(namespace+".updateReplyCnt",paramMap);
		
	}

	// namespace이름을 맞추어놓았다. boardMapper.xml쪽과 동일하게.
	private static String namespace = "org.zerock.mapper.BoardMapper";

	// 오버라이드 한것들은 String 형으로 mapper쪽을 찾아가서 sql문을 사용하도록 설정되어있다.
	@Override
	public void create(BoardVo vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}

	@Override
	public BoardVo read(Integer bno) throws Exception {
		return session.selectOne(namespace + ".read", bno);
	}

	@Override
	public void update(BoardVo vo) throws Exception {
		session.update(namespace + ".update", vo);
	}

	@Override
	public void delete(Integer bno) throws Exception {
		session.delete(namespace + ".delete", bno);

	}

	@Override
	public List<BoardVo> listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}

	@Override
	public List<BoardVo> listPage(int page) throws Exception {
		if (page <= 0) {
			page = 1;
		} else {
			page = (page - 1) * 10;
		}
		return session.selectList(namespace + ".listPage", page);
	}

	@Override
	public List<BoardVo> listCriteria(Criteria cri) throws Exception {
		return session.selectList(namespace + ".listCriteria", cri);
	}
	@Override
	public int countPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".countPaging",cri);
	};
	@Override
	public List<BoardVo> listSearch(SearchCriteria cri) throws Exception {
		
		return session.selectList(namespace+".listSearch",cri);
	}
	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {

		return session.selectOne(namespace+".listSearchCount",cri);
	}
}
