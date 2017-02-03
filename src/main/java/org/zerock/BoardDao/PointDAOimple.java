package org.zerock.BoardDao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOimple implements PointDao{
	
	@Inject
	private SqlSession sqlsession;
	
	private static String namespace = "org.zerock.mappers.pointMapper";
	
	@Override
	public void updatePoint(String uid, int point) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uid", uid);
		paramMap.put("point",point);
		
		sqlsession.update(namespace+".updatePoint",paramMap);
		
		
	}

}
