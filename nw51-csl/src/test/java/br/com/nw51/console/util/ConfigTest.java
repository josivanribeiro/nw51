package br.com.nw51.console.util;

import org.junit.Before;

public class ConfigTest {

	private Config config;
	
	@Before 
	public void setUp() {
		config = new Config();		
    }
	
	/*@Test
	public void testLoadProperties () {		
		assertEquals ("com.mysql.jdbc.Driver", config.getProperty ("db.driver.name"));		
	}*/	
	
}
