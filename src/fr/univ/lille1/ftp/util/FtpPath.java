package fr.univ.lille1.ftp.util;

/**
 * FtpPath is the class used for the relative and absolute path processing
 *
 * @author Quentin Warnant
 * @version 1.0
 */
public class FtpPath {

    private String currentDirectory;
    private String path;
    private boolean relative;

    /**
     * Class constructor
     *
     * @param currentDirectory String the current ftp directory
     * @param path             String the new path requested by the user
     * @param relative         boolean true if the requested path is relative
     */
    public FtpPath(String currentDirectory, String path, boolean relative) {
        this.currentDirectory = currentDirectory;
        this.path = path;
        this.relative = relative;
    }

    /**
     * This method returns the formatted current path
     *
     * @return String the path
     */
    public String getPath() {
        if (this.isRelative()) {
            return FtpUtils.refactorPath(this.currentDirectory + this.path);
        }
        return this.path;
    }

    /**
     * This method returns true if the current path is relative
     *
     * @return boolean
     */
    public boolean isRelative() {
        return this.relative;
    }

    @Override
    public String toString() {
        return getPath();
    }
}

