package fr.univ.lille1.ftp.server;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univ.lille1.ftp.util.FtpConstants;

/**
 * Created by Warnant on 09-02-15.
 */
public class FtpPortManager {

    private static FtpPortManager instance;

    private List<Integer> busyPorts;


    private FtpPortManager() {
        this.busyPorts = new ArrayList<Integer>();
    }

    public static FtpPortManager getInstance() {
        if (instance == null) {
            instance = new FtpPortManager();
        }
        return instance;
    }

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
}
