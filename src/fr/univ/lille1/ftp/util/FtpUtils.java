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
	
    public static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

}
