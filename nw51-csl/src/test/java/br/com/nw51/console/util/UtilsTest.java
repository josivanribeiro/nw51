package br.com.nw51.console.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;

public class UtilsTest {
	
	@Before 
	public void setUp() {
				
    }
	
	//@Test
	public void testGetSHA512Password () {
		/*String pwd = "12345";
		String salt = "josivan@nw51.com.br";		
		String generatedPwd = SecurityUtils.getSHA512Password (pwd, salt);
		System.out.println ("generatedPwd: " + generatedPwd);
		System.out.println ("generatedPwd.length: " + generatedPwd.length());
		assertNotNull (generatedPwd);*/		
	}
	
	//@Test
	public void testGetSHA512PasswordWithComparison () {
		/*String pwd1 = "12345";
		String salt1 = "josivan@gmail.com";
		
		String pwd2 = "12345";
		String salt2 = "josivan@gmail.com";
		
		String generatedPwd1 = SecurityUtils.getSHA512Password (pwd1, salt1);
		String generatedPwd2 = SecurityUtils.getSHA512Password (pwd2, salt2);
		
		System.out.println ("generatedPwd1: " + generatedPwd1);
		System.out.println ("generatedPwd2: " + generatedPwd2);
		System.out.println ("generatedPwd1.length: " + generatedPwd1.length());
		System.out.println ("generatedPwd2.length: " + generatedPwd2.length());
		
		assertEquals (generatedPwd1, generatedPwd2);*/
	}

}
