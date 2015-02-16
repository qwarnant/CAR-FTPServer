package fr.univ.lille1.ftp.tests;

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
 * TestUserPass is the unit test class which tests all the
 * file management use cases on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class TestFilePass {

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
    public  void after() throws Exception {
        client.logout();
    }

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
            //client.logout();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
            //client.logout();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
