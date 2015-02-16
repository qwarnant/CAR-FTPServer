package fr.univ.lille1.ftp.tests;

import fr.univ.lille1.ftp.util.FtpConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * TestUserPass is the unit test class which tests all the
 * directory management use cases on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class TestDir {

    private static FTPClient client;
    private static long lastTimestamp;

    @Before
    public void setUp() throws Exception {

        try {

            client = new FTPClient();
            client.connect(FtpConstants.FTP_HOST_NAME, FtpConstants.FTP_SERVER_PORT);
            client.login(FtpConstants.FTP_TEST_USERNAME, FtpConstants.FTP_TEST_PASSWORD);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void setUpAfter() throws Exception {
        if (client.isConnected()) {
            client.quit();

        }
    }

    @After
    public void after() throws Exception {
        client.logout();
    }

    @Test
    public void testCWDRequestOk() {
        try {
            boolean result = client.changeWorkingDirectory("test");
            assertTrue("Result code should be true", result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCWDRequestKo() {
        try {
            boolean result = client.changeWorkingDirectory("foo");
            assertFalse("Result code should be false", result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCdupRequestOk() {
        try {
            client.changeWorkingDirectory("test");
            int resultCode = client.cdup();
            assertEquals("Result code should be " + FtpConstants.FTP_REP_CMD_OK_CODE,
                    FtpConstants.FTP_REP_CMD_OK_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCdupRequestAccessDenied() {
        try {
            int resultCode = client.cdup();
            assertEquals("Result code should be " + FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCdupRequest() {
        try {
            int resultCode = client.cdup();
            assertEquals("Result code should be " + FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    FtpConstants.FTP_ERR_ACTION_NOT_TAKEN,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testNlstRequestKo() {
        try {
            int resultCode = client.nlst();
            assertEquals("Result code should be " + FtpConstants.FTP_ERR_CONNECTION_FAILED_CODE,
                    FtpConstants.FTP_ERR_CONNECTION_FAILED_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testNlstRequestOk() {
        try {
            client.enterLocalActiveMode();
            client.port(client.getLocalAddress(), client.getRemotePort());
            int resultCode = client.nlst();
            assertEquals("Result code should be " + FtpConstants.FTP_REP_DATA_OK_CODE,
                    FtpConstants.FTP_REP_DATA_OK_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testListRequestOk() {
        try {
            client.enterLocalActiveMode();
            client.port(client.getLocalAddress(), client.getRemotePort());

            int resultCode = client.list();
            assertEquals("Result code should be " + FtpConstants.FTP_REP_DATA_OK_CODE,
                    FtpConstants.FTP_REP_DATA_OK_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testNlstDirectoryRequestOk() {
        try {
            client.port(client.getLocalAddress(), client.getRemotePort());
            int resultCode = client.nlst("test");
            assertEquals("Result code should be " + FtpConstants.FTP_REP_DATA_OK_CODE,
                    FtpConstants.FTP_REP_DATA_OK_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testListDirectoryRequestOk() {
        try {
            client.port(client.getLocalAddress(), client.getRemotePort());

            int resultCode = client.list("test");
            assertEquals("Result code should be " + FtpConstants.FTP_REP_DATA_OK_CODE,
                    FtpConstants.FTP_REP_DATA_OK_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testListMkdRequestOk() {
        try {
            lastTimestamp = System.currentTimeMillis();
            String filename =  lastTimestamp + "";
            int resultCode = client.mkd(filename);

            assertEquals("Result code should be " + FtpConstants.FTP_REP_PWD_CODE,
                    FtpConstants.FTP_REP_PWD_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testListRmdRequestOk() {
        try {
            String filename =  lastTimestamp + "";
            int resultCode = client.rmd(filename);

            assertEquals("Result code should be " + FtpConstants.FTP_REP_PWD_CODE,
                    FtpConstants.FTP_REP_PWD_CODE,
                    resultCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
