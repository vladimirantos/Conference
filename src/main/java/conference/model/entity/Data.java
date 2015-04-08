package conference.model.entity;

import org.springframework.web.multipart.MultipartFile;

public class Data {

    private MultipartFile article;

    public MultipartFile getArticle() {
        return article;
    }

    public void setArticle(MultipartFile article) {
        this.article = article;
    }
}
