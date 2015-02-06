package fr.univ.lille1.ftp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ.lille1.ftp.client.FtpClient;

public class TestUserPass {

	private FtpClient client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//this.client = new FtpClient(socket, username, password)
		
		
	}

	@Test
	public void testUserCommandOk() {
		assertTrue(false);
	}
	
	@Test
	public void testUserCommandKo() {
		assertTrue(true);
	}

	
	@Test
	public void testPassCommandOk() {
		assertTrue(true);
	}


}
