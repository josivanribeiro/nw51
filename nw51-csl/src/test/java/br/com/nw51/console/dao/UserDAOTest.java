package br.com.nw51.console.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.nw51.common.vo.UserVO;

/*import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.nw51.vo.UserVO;*/

public class UserDAOTest {

	private UserDAO userDAO;
	
	@Before 
	public void setUp() {	    
		userDAO = new UserDAO();
    }
	
	@Test
	public void testInsert () {
		int affectedRows = 0;
		UserVO userVO = new UserVO ();
		userVO.setEmail ("josivan.silva@outlook.com");
		userVO.setPwd ("12345");
		userVO.setStatus (true);
		try {
			affectedRows = userDAO.insert (userVO);
			assertEquals (1, affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*@Test
	public void testInsertWithSQLInjection () {
		int affectedRows = 0;
		UserVO userVO = new UserVO ();
		userVO.setEmail ("or updatexml (1,concat(0x7e,(version())),0) or");
		userVO.setPwd ("12345");
		userVO.setStatus (true);
		try {
			affectedRows = userDAO.insert (userVO);
			assertEquals (1, affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testInsertWithSQLInjection2 () {
		int affectedRows = 0;
		UserVO userVO = new UserVO ();
		userVO.setEmail ("or DROP TABLE USER or");
		userVO.setPwd ("12345");
		userVO.setStatus (true);
		try {
			affectedRows = userDAO.insert (userVO);
			assertEquals (1, affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testUpdate () {
		int affectedRows = 0;
		UserVO userVO = new UserVO ();
		userVO.setUserId (13);
		userVO.setEmail ("josivan@gmail.com");
		userVO.setPwd ("12345");
		userVO.setStatus (false);
		try {
			affectedRows = userDAO.update (userVO);
			assertEquals (1, affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testUpdatePassword () {
		int affectedRows = 0;
		UserVO userVO = new UserVO ();
		userVO.setUserId (2);
		userVO.setPwd ("aaaaaa");
		try {
			affectedRows = userDAO.updatePassword (userVO);
			assertEquals (1, affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testDelete () {
		int affectedRows = 0;
		UserVO userVO = new UserVO ();
		userVO.setUserId (2);
		try {
			affectedRows = userDAO.delete (userVO);
			assertEquals (1, affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testFindById () {
		UserVO userVO = new UserVO ();
		userVO.setUserId (4);
		try {
			UserVO foundUser = userDAO.findById (userVO);
			assertNotNull (foundUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testFindAll () {
		List<UserVO> userVOList = new ArrayList<UserVO> ();
		try {
			userVOList = userDAO.findAll ();
			assertNotNull (userVOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testDoLogin () {
		boolean isLogged = false;
		try {
			UserVO userVO = new UserVO();
			userVO.setEmail("josivan@gmail.com");
			userVO.setPwd ("12345");
			isLogged = userDAO.doLogin (userVO);
			assertTrue (isLogged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
