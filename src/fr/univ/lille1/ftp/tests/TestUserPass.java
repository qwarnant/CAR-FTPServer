package fr.univ.lille1.ftp.tests;

import fr.univ.lille1.ftp.client.FtpClient;
import fr.univ.lille1.ftp.util.FtpConstants;
import org.junit.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import static org.junit.Assert.assertTrue;

/**
 * TestUserPass is the unit test class which tests all the
 * USER and PASS use cases on the ftp server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class TestUserPass {

    private static SocketAddress sa;
    private static FtpClient normalClient;
    private static FtpClient anonymousClient;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        sa = new InetSocketAddress(
                InetAddress.getByName(FtpConstants.FTP_HOST_NAME),
                FtpConstants.FTP_SERVER_PORT);
    }

    @Before
    public void setUp() throws Exception {

        try {

            normalClient = new FtpClient(new Socket(), FtpConstants.FTP_TEST_USERNAME, FtpConstants.FTP_TEST_PASSWORD);
            normalClient.connect(sa);
            anonymousClient = new FtpClient(new Socket(), FtpConstants.FTP_ANONYMOUS_NAME, FtpConstants.FTP_TEST_PASSWORD);
            anonymousClient.connect(sa);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserNormalRequestOk() {

        try {

                // Get the input stream
                InputStreamReader isr = new InputStreamReader(
                        normalClient.getControlSocket().getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String commandLine = br.readLine().trim();
          //  normalClient.doUserRequest();

         //   commandLine = br.readLine().trim();
                System.out.println(commandLine);

                int resultCode = Integer.parseInt(commandLine.substring(0, 3));

                System.out.println(resultCode);
                System.out.println(commandLine);



        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUserAnonymousRequestOk() {

        try {
            normalClient.doUserRequest();



        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testUserRequestKo() {
        assertTrue(true);
    }

    @Test
    public void testPassRequestOk() {
        assertTrue(true);
    }

    public void testQuitRequestOk() {
        try {
            normalClient.doQuitRequest();
            anonymousClient.doQuitRequest();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void setUpAfter() throws Exception {

        normalClient.doQuitRequest();
        anonymousClient.doQuitRequest();
        normalClient.close();
        anonymousClient.close();
    }
}
