package fr.univ.lille1.ftp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * FtpPortManager is the class that handles the data port access of the FTP server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpPortManager {

    private static FtpPortManager instance;

    private List<Integer> busyPorts;

    /**
     * Default constructor
     */
    private FtpPortManager() {
        this.busyPorts = new ArrayList<Integer>();
    }

    /**
     * This method returns the current instance of the FtpPortManager
     * If there is no instance, the method creates one
     *
     * @return FtpPortManager the current instance
     */
    public static FtpPortManager getInstance() {
        if (instance == null) {
            instance = new FtpPortManager();
        }
        return instance;
    }

    /**
     * This method returns a new available free port of the ftp server
     * for the data connection with the client
     *
     * @return int a new free available port between 1024 and 65535
     */
    public int getNewPort() {

        int min = FtpConstants.FTP_SERVER_PORT + 1;
        int max = 65535;
        int portNum = 0;

        do {
            Random random = new Random();
            portNum = random.nextInt((max - min) + 1) + min;
        } while (busyPorts.contains(portNum));

        busyPorts.add(portNum);

        return portNum;
    }

    /**
     * This method frees the parameter port number of the server
     *
     * @param portNumber int the port number to free
     * @return boolean true if the port has been freed; false if not
     */
    public boolean freePort(int portNumber) {
        if (portNumber == 0) {
            return false;
        }
        if (!busyPorts.contains(portNumber)) {
            return false;
        }
        return busyPorts.remove(new Integer(portNumber));
    }
}
