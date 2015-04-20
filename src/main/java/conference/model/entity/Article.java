package conference.model.entity;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public class Article {
    private MultipartFile configFile;

    private Long conference;

    private List<MultipartFile> articles;

    public MultipartFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(MultipartFile configFile) {
        this.configFile = configFile;
    }

    public Long getConference() {
        return conference;
    }

    public void setConference(Long conference) {
        this.conference = conference;
    }

    public List<MultipartFile> getArticles() {
        return articles;
    }

    public void setArticles(List<MultipartFile> articles) {
        this.articles = articles;
    }
}