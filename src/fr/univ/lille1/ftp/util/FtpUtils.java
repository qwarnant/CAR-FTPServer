package fr.univ.lille1.ftp.util;

import java.io.File;

public class FtpUtils {

	public static String getTransferTypeString(char transferType) {
		if (transferType == FtpConstants.FTP_ASCII_TYPE) {
			return "ASCII";
		} else if (transferType == FtpConstants.FTP_BINARY_TYPE) {
			return "BINARY";

		}
		return "unknown";
	}

	public static String listFilesInFolder(final File folder) {
		String folderFiles = "";
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				folderFiles += listFilesInFolder(fileEntry) + "\n";
			} else {
				folderFiles += fileEntry.getName() + "\n";
			}
		}
		return folderFiles;
	}

}
