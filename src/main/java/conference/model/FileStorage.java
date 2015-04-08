package conference.model;

import conference.configuration.Constants;

import java.io.File;
import java.util.List;

/**
 * Třída pro práci se soubory.
 */
public class FileStorage {

    private static final String ROOT = Constants.FILE_ROOT;

    public static final void createDir(String name){
        File f = new File(ROOT + File.separator + name);
        f.mkdirs();
    }

    public static final boolean dirExist(String name){
        return true;
    }

    public static final int countFiles(String name){
        return new File(ROOT + File.separator + name).list().length;
    }

    public static final String getPath(long id){
        return ROOT + File.separator + id + File.separator;
    }

    /*public static final List<String> getFiles(String dir){

    }

    public static final String getFile(String path){

    }*/
}
