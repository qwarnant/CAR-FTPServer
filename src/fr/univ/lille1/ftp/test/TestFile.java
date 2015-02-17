package fr.univ.lille1.ftp.test;

import fr.univ.lille1.ftp.util.FtpConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * TestFile is the unit test class which test all the
 * file management use cases on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class TestFile {

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
    public  void after() throws Exception {
        client.logout();
    }

    /**
     * This method tests the PORT command on the server
     */
    @Test
    public void testPortRequestOk() {

        try {
            int resultCode = client.port(client.getLocalAddress(), client.getRemotePort());
            assertEquals("The result code should be " + FtpConstants.FTP_REP_CMD_OK_CODE,
                    FtpConstants.FTP_REP_CMD_OK_CODE,
                    resultCode
            );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests to STOR a new file with PORT data connection on the server
     */
    @Test
    public void testStorPortRequestOk() {

        try {

            lastTimestamp = System.currentTimeMillis();
            String filename =  lastTimestamp + ".temp";
            File file = new File("data/" + filename);
            file.createNewFile();

            InputStream fs = new FileInputStream(file);

            boolean ok = client.storeFile(filename, fs);
            assertTrue("The result should be true", ok);

            fs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests to RETR a file with PORT data connection on the server
     */
    @Test
    public void testRetrPortRequestOk() {

        try {
            String filename = lastTimestamp + ".temp";
            File file = new File("data/" + filename);
            file.createNewFile();

            OutputStream os = new FileOutputStream(file);

            boolean ok = client.retrieveFile(filename, os);
            assertTrue("The result should be true", ok);

            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
