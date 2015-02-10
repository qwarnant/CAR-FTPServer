package fr.univ.lille1.ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.univ.lille1.ftp.server.request.*;
import fr.univ.lille1.ftp.util.FtpConstants;

public class FtpThread extends Thread {

    private String username;
    private String currentRemoteIp;
    private int currentRemotePort;
    private char currentType;

    private Socket socket;
    private List<FtpRequest> history;

    private DataOutputStream out;

    public FtpThread(Socket socket) throws IOException {
        super();
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.history = new ArrayList<FtpRequest>();
    }

    @Override
    public void run() {

        try {

            // Make a FTP reponse to say that the server is ready
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            FtpResponse response = new FtpResponse(
                    FtpConstants.FTP_REP_READY_CODE,
                    FtpConstants.FTP_REP_READY_MSG + sdf.format(now));

            // Print welcome message
            out.writeBytes("******************************\n");
            out.writeBytes("Welcome to my great FTP server\n");
            out.writeBytes("******************************\n");
            out.writeBytes(response.toString());

            // Starting to catch all FTP requests ...
            while (true) {
                this.handleInputRequest();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleInputRequest() {

        try {

            // Get the input stream
            InputStreamReader isr = new InputStreamReader(
                    socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            // Read the request type
            String commandLine = br.readLine();
            String command = commandLine.substring(0, 4).trim();
            System.out.println("Receive request : " + command);

            if (command.equals(FtpConstants.FTP_CMD_USER)) {

                // Make a USER request
                FtpUserRequest ruser = new FtpUserRequest(commandLine);
                this.storeAndExecute(ruser);
                this.username = ruser.getResultUsername();

            } else if (command.equals(FtpConstants.FTP_CMD_PASS)) {

                // Make a PASS request
                FtpRequest rpass = new FtpPassRequest(commandLine, this.username);
                this.storeAndExecute(rpass);

            } else if (command.equals(FtpConstants.FTP_CMD_PORT)) {

                // Make a PORT request
                FtpPortRequest rport = new FtpPortRequest(commandLine);
                this.storeAndExecute(rport);

                this.currentRemoteIp = rport.getRemoteIp();
                this.currentRemotePort = rport.getRemotePort();
            } else if (command.equals(FtpConstants.FTP_CMD_PASV)) {

                // Make a PASV request
                FtpPasvRequest rpasv = new FtpPasvRequest(commandLine);
                this.storeAndExecute(rpasv);
                //TODO

            } else if (command.equals(FtpConstants.FTP_CMD_TYPE)) {

                FtpTypeRequest rtype = new FtpTypeRequest(commandLine);
                this.storeAndExecute(rtype);

                this.currentType = rtype.getType();

            } else {
                // Return a FTP response error
                FtpResponse response = new FtpResponse(
                        FtpConstants.FTP_ERR_INVALID_COMMAND_CODE,
                        FtpConstants.FTP_ERR_INVALID_COMMAND_MSG);
                out.writeBytes(response.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeAndExecute(FtpRequest request) throws IOException {
        history.add(request);
        System.out.println(request);
        FtpResponse response = request.process();
        System.out.println(response.toString());
        // Write the answer to the socket
        out.writeBytes(response.toString());
    }
}
