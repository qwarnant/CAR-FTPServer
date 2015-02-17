package fr.univ.lille1.ftp.test;

import fr.univ.lille1.ftp.util.FtpConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

/**
 * TestUser is the unit test class which test all the
 * USER and PASS use cases on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class TestUser {

    private static FTPClient client;

    /**
     * This method sets up the ftp client before each test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        try {

            client = new FTPClient();
            client.connect(FtpConstants.FTP_HOST_NAME, FtpConstants.FTP_SERVER_PORT);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method closes the ftp client user after each test
     * @throws Exception
     */
    @After
    public void setUpAfter() throws Exception {
        if (client.isConnected()) {
            client.quit();
        }
    }

    /**
     * This method tests a good username with USER command on the server
     */
    @Test
    public void testUserNormalRequestOk() {

        try {

            int resultCode = client.user(FtpConstants.FTP_TEST_USERNAME);
            assertEquals("Result code should be " + FtpConstants.FTP_REP_NEED_USER_CODE,
                    FtpConstants.FTP_REP_NEED_USER_CODE,
                    resultCode);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests the anonymous username with USER command on the server
     */
    @Test
    public void testUserAnonymousRequestOk() {

        try {
            int resultCode = client.user(FtpConstants.FTP_ANONYMOUS_NAME);
            assertEquals("Result code should be " + FtpConstants.FTP_REP_NEED_USER_CODE,
                    FtpConstants.FTP_REP_NEED_USER_CODE,
                    resultCode);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests a bad username with USER command on the server
     */
    @Test
    public void testUserRequestKo() {

        try {
            int resultCode = client.user("janedoe");
            assertEquals("Result code should be " + FtpConstants.FTP_ERR_INVALID_USER_PWD_CODE,
                    FtpConstants.FTP_ERR_INVALID_USER_PWD_CODE,
                    resultCode);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests a normal USER/PASS matching on the server
     */
    @Test
    public void testNormalPassRequestOk() {
        try {
            int resultCode = client.user(FtpConstants.FTP_TEST_USERNAME);
            assertEquals("Result code should be " + FtpConstants.FTP_REP_NEED_USER_CODE,
                    FtpConstants.FTP_REP_NEED_USER_CODE,
                    resultCode);
            resultCode = client.pass(FtpConstants.FTP_TEST_PASSWORD);
            assertEquals("Result code should be " + FtpConstants.FTP_REP_LOGIN_OK_CODE,
                    FtpConstants.FTP_REP_LOGIN_OK_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests a bad USER/PASS matching on the server
     */
    @Test
    public void testNormalPassRequestKo() {
        try {
            int resultCode = client.user(FtpConstants.FTP_TEST_USERNAME);
            assertEquals("Result code should be " + FtpConstants.FTP_REP_NEED_USER_CODE,
                    FtpConstants.FTP_REP_NEED_USER_CODE,
                    resultCode);
            resultCode = client.pass("foobar");
            assertEquals("Result code should be " + FtpConstants.FTP_ERR_INVALID_USER_PWD_CODE,
                    FtpConstants.FTP_ERR_INVALID_USER_PWD_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests the anonymous connection on the server
     */
    @Test
    public void testAnonymousPassRequestOk() {
        try {
            int resultCode = client.user(FtpConstants.FTP_ANONYMOUS_NAME);
            assertEquals("Result code should be " + FtpConstants.FTP_REP_NEED_USER_CODE,
                    FtpConstants.FTP_REP_NEED_USER_CODE,
                    resultCode);
            resultCode = client.pass("foobar");
            assertEquals("Result code should be " + FtpConstants.FTP_REP_LOGIN_OK_CODE,
                    FtpConstants.FTP_REP_LOGIN_OK_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests the behaviour of a bad sequence in the USER/PASS requests on the server
     */
    @Test
    public void testBadUserPassRequest() {
        try {
            int resultCode = client.pass(FtpConstants.FTP_TEST_PASSWORD);
            assertEquals("Result code should be " + FtpConstants.FTP_ERR_BAD_SEQ_CODE,
                    FtpConstants.FTP_ERR_BAD_SEQ_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
