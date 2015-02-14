package fr.univ.lille1.ftp.util;

/**
 * Created by Warnant on 14-02-15.
 */
public class FtpPath {

    private String currentDirectory;
    private String path;
    private boolean relative;

    public FtpPath(String currentDirectory, String path, boolean relative) {
        this.currentDirectory = currentDirectory;
        this.path = path;
        this.relative = relative;
    }

    public String getPath() {
        if(this.isRelative()) {
            return FtpUtils.refactorPath(this.currentDirectory + this.path);
        }
        return this.path;
    }

    public boolean isRelative() {
        return this.relative;
    }

    @Override
    public String toString() {
        return getPath();
    }
}

