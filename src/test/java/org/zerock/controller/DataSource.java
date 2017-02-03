package org.zerock.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class DataSource {
	
	@Inject
	private javax.sql.DataSource dataSource;
	
	@Test
 	public void testDataSource()throws IOException{
		try (Connection con=dataSource.getConnection()){
			System.out.println( con+"ㅌ스트");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}


}
