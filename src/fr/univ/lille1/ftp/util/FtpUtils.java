package fr.univ.lille1.ftp.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class FtpUtils {

    public static String getTransferTypeString(char transferType) {
        if (transferType == FtpConstants.FTP_ASCII_TYPE) {
            return "ASCII";
        } else if (transferType == FtpConstants.FTP_BINARY_TYPE) {
            return "BINARY";

        }
        return "unknown";
    }

    public static FtpPath extractArgumentFromCommandLine(String commandLine, String currentDirectory) {

        String[] tokens = commandLine.split(" ");

        if (tokens.length > 1) {
            return new FtpPath(currentDirectory, commandLine.substring(tokens[0].length(), commandLine.length()).trim(), true);
        } else {
            return new FtpPath(currentDirectory, currentDirectory, false);
        }
    }

    public static String refactorPath(String currentPath) {
        if (currentPath == null) return "";
        String[] paths = currentPath.split("\\\\");
        String resultPath = "";

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
            resultPath += token + "\\";

        }

        return resultPath;
    }

    public static String listFilesInFolder(final File folder, boolean recursive) {
        String folderFiles = "";

        if (folder == null || folder.listFiles() == null) {
            return folderFiles;
        }

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                if (recursive) {
                    folderFiles += listFilesInFolder(fileEntry, true) + "\n";
                } else {
                    folderFiles += fileEntry.getName() + "/\n";
                }
            } else {
                folderFiles += fileEntry.getName() + "\n";
            }
        }
        return folderFiles;
    }


    public static int deleteFilesInFolder(final File folder) {

        int deleteCount = 0;

        if (folder == null || folder.listFiles() == null) {
            return deleteCount;
        }

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                   deleteCount += deleteFilesInFolder(fileEntry);
            }
            if(fileEntry.delete()) {
                deleteCount++;
            }

        }

        if(folder.delete()) {
            deleteCount++;
        }

        return deleteCount;
    }

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
