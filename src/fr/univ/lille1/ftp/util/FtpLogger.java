package fr.univ.lille1.ftp.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * FtpLogger is a class which contains all methods
 * to log the ftp server activity in a specified file
 * The log methods are working with the official
 * Java Logger class
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpLogger {

    private Logger logger;
    private FileHandler fh;

    /**
     * Class constructor
     *
     * @param logFile String the local path of the log file
     * @throws IOException
     */
    public FtpLogger(String logFile) throws IOException {
        this.logger = Logger.getLogger(FtpConstants.FTP_LOGGER_NAME);
        this.logger.setLevel(Level.INFO);

        this.fh = new FileHandler(logFile);
        this.logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        this.fh.setFormatter(formatter);
    }

    /**
     * This methods logs the specified message on the logger
     * with the info level
     *
     * @param message String the message to log
     */
    public void info(String message) {
        if (!FtpConstants.LOGGER_ENABLED) {
            return;
        }
        this.logger.info(message);
    }

    /**
     * This methods logs the specified message on the logger
     * with the severe/error level
     *
     * @param message String the message to log
     */
    public void error(String message) {
        if (!FtpConstants.LOGGER_ENABLED) {
            return;
        }
        this.logger.log(Level.SEVERE, message);
    }

    /**
     * This method closes the file handler associated to
     * the current logger
     */
    public void close() {
        this.fh.close();
    }
}
