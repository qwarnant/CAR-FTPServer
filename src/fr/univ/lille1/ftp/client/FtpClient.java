package fr.univ.lille1.ftp.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class FtpClient {

    private Socket controlSocket;
    private String username;
    private String password;

    public FtpClient(Socket socket, String username, String password) {
        this.controlSocket = socket;
        this.username = username;
        this.password = password;
    }

    public void connect(SocketAddress sa) throws IOException {
        this.controlSocket.connect(sa);
    }

    public void close() throws IOException {
        this.controlSocket.close();
    }

    public Socket getControlSocket() {
        return this.controlSocket;
    }

    public void doUserRequest() throws IOException {
        DataOutputStream dos = new DataOutputStream(this.controlSocket.getOutputStream());
        dos.writeBytes("USER " + username);
        dos.flush();
    }

    public void doPassRequest() throws IOException {
        DataOutputStream dos = new DataOutputStream(this.controlSocket.getOutputStream());
        dos.writeBytes("PASS " + password);
    }

    public void doQuitRequest() throws IOException {
        DataOutputStream dos = new DataOutputStream(this.controlSocket.getOutputStream());
        dos.writeBytes("QUIT");
    }

}
