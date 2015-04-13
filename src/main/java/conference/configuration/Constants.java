package conference.configuration;

import java.io.File;

public class Constants {
    public static final Boolean DEBUG_MODE = true;
    public static final String APP_NAME = "Conference";

    /**
     * Domovský adresář pro nahrávání souborů
     */
    public static final String FILE_ROOT = System.getProperty("catalina.home") + File.separator + "/articles";

    public static final String DATETIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static final int MAX_ID = 1000000;
}
