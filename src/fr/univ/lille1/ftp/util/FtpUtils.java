package fr.univ.lille1.ftp.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * FtpUtils is an utility class which contains all utility
 * methods for the FTP server
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpUtils {

    /**
     * This method returns the data transfer type string associated to the char type
     *
     * @param transferType char the data character type
     * @return String the data transfer type string
     */
    public static String getTransferTypeString(char transferType) {
        if (transferType == FtpConstants.FTP_ASCII_TYPE) {
            return "ASCII";
        } else if (transferType == FtpConstants.FTP_BINARY_TYPE) {
            return "BINARY";

        }
        return "unknown";
    }

    /**
     * This method returns the ftp path object associated to the incoming command line from the client
     *
     * @param commandLine      String the requested command line from the ftp client
     * @param currentDirectory String the current ftp server directory
     * @return FtpPath the ftp path object
     */
    public static FtpPath extractArgumentFromCommandLine(String commandLine, String currentDirectory) {

        String[] tokens = commandLine.split(" ");

        if (tokens.length > 1) {
            return new FtpPath(currentDirectory, commandLine.substring(tokens[0].length(), commandLine.length()).trim(), true);
        } else {
            return new FtpPath(currentDirectory, currentDirectory, false);
        }
    }

    /**
     * This method returns the request path without any redundancy ".." or "." in the local path
     *
     * @param currentPath String the requested path
     * @return String the new refactored local path
     */
    public static String refactorPath(String currentPath) {
        if (currentPath == null) return "";

        boolean useSlashes = currentPath.contains("/");
        
        String separatorRegex = (!useSlashes) ? "\\\\" : "/";
        String separatorToken = (!useSlashes) ? "\\" : "/";

        String[] paths = currentPath.split(separatorRegex);

        String resultPath = (System.getProperty("os.name").startsWith("Win")) ? ""
                : "/";

        int lastIndex = 0;
        boolean up = false;
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].equals("..")) {
                up = true;
                break;
            }
            lastIndex = i;
        }

        for (int i = 0; i < paths.length; i++) {
            String token = paths[i];
            if (up && lastIndex == i) {
                continue;
            }
            if (token.equals("..") || token.trim().isEmpty()) {
                continue;
            }
            resultPath += token + separatorToken;

        }

        return resultPath;
    }

    /**
     * This method returns the file sub-list of the current folder file
     *
     * @param folder    File the current folder
     * @param recursive boolean true if the search must be recursive, false otherwise
     * @param verbose   boolean true if all information of the file must be gotten, false otherwise
     * @return String the file sub-list
     */
    public static String listFilesInFolder(final File folder, boolean recursive, boolean verbose) {
        String folderFiles = "";

        if (folder == null || folder.listFiles() == null) {
            return folderFiles;
        }

        // Check if the current system is Linux
        if (verbose && !System.getProperty("os.name").startsWith("Win")) {
            folderFiles += executeShellCommand("ls -l " + folder.getAbsolutePath());
        } else {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    if (recursive) {
                        folderFiles += listFilesInFolder(fileEntry, true, verbose) + "\n";
                    } else {
                        folderFiles += fileEntry.getName() + "/\n";
                    }
                } else {
                    folderFiles += fileEntry.getName() + "\n";
                }
            }
        }

        return folderFiles;
    }

    /**
     * This method executes a shell command from Java
     *
     * @param commandName String the requested command name with args
     * @return String the output of the command processing
     */
    public static String executeShellCommand(String commandName) {
        String result = "";

        // Put the english locale for the list command
        Map<String, String> environment = new HashMap<String, String>(System.getenv());
        environment.put("LC_ALL", "en_EN");
        String[] envp = new String[environment.size()];
        int count = 0;
        for (Map.Entry<String, String> entry : environment.entrySet()) {
            envp[count++] = entry.getKey() + "=" + entry.getValue();
        }
        
        try {
            Process p = Runtime.getRuntime().exec(commandName, envp);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }

            return result;
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * This method deletes recursively all the sub-files of the requested directory
     *
     * @param folder File the requested folder
     * @return int the deleted file count
     */
    public static int deleteFilesInFolder(final File folder) {

        int deleteCount = 0;

        if (folder == null || folder.listFiles() == null) {
            return deleteCount;
        }

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                deleteCount += deleteFilesInFolder(fileEntry);
            }
            if (fileEntry.delete()) {
                deleteCount++;
            }

        }

        if (folder.delete()) {
            deleteCount++;
        }

        return deleteCount;
    }

    /**
     * This method copies a input stream into an output stream
     *
     * @param is InputStream the input stream
     * @param os OutputStream the output stream
     */
    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

}
