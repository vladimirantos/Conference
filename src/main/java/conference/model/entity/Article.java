package conference.model.entity;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public class Article {
    private MultipartFile configFile;

    public MultipartFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(MultipartFile configFile) {
        this.configFile = configFile;
    }
}