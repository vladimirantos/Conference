package conference.model;

import conference.model.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ArticleManager{

    private long idConference;

    @Autowired
    private ArticleRepository articleRepository;

    public long getIdConference() {
        return idConference;
    }

    public ArticleManager setIdConference(long idConference) {
        this.idConference = idConference;
        return this;
    }

    public void uploadConfigFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (!"".equalsIgnoreCase(fileName))
            file.transferTo(new File(FileStorage.getPath(idConference) + fileName));
    }
}
