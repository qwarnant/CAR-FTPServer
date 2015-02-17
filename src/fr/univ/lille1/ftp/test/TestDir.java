package fr.univ.lille1.ftp.test;

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
 * TestDir is the unit test class which test all the
 * directory management use cases on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class TestDir {

    private static FTPClient client;
    private static long lastTimestamp;

    /**
     * This method sets up the ftp client before each test
     * @throws Exception
     */
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

    /**
     * This method closes the ftp client connection after all tests
     * @throws Exception
     */
    @AfterClass
    public static void setUpAfter() throws Exception {
        if (client.isConnected()) {
            client.quit();

        }
    }

    /**
     * This method disconnects the ftp client user after each test
     * @throws Exception
     */
    @After
    public void after() throws Exception {
        client.logout();
    }

    /**
     * This method tests if the CWD command can change the current directory to the test directory
     */
    @Test
    public void testCWDRequestOk() {
        try {
            boolean result = client.changeWorkingDirectory("test");
            assertTrue("Result code should be true", result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests if the CWD command can change the current directory to a missing directory
     */
    @Test
    public void testCWDRequestKo() {
        try {
            boolean result = client.changeWorkingDirectory("foo");
            assertFalse("Result code should be false", result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests if the CDUP command can change the current directory to the top directory of the server
     */
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

    /**
     * This method tests if the CDUP command can change the current directory outside the main directory
     */
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

    /**
     * This method tests if the NLST command can list the current directory files
     * without data connection  
     */
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

    /**
     * This method tests if the NLST command can list the current directory files
     */
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

    /**
     * This method tests if the LIST command can list the current directory files
     */
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

    /**
     * This method tests if the NLST command can list the request directory files
     */
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

    /**
     * This method tests if the LIST command can list the request directory files
     */
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

    /**
     * This method tests if the MKD command can create a directory
     */
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

    /**
     * This method tests if the RMD command can remove a directory
     */
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
