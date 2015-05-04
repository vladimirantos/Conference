package conference.model;

import conference.model.entity.DbArticle;
import conference.model.entity.Export;
import conference.model.repository.IArticleRepository;
import conference.model.repository.IExportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExportManager {

    @Autowired
    private IExportRepository exportRepository;

    @Autowired
    private IArticleRepository articleRepository;

    public void save(Export export){
        exportRepository.insert(export);
    }

    public List<Export> getPatterns(){
        return exportRepository.selectPatterns();
    }

    public Export getPatternById(int id){
        return exportRepository.getPatternById(id);
    }

    public void export(Export export){
        String pattern = getPatternById(export.getIdPattern()).getPattern();
        DbArticle article = articleRepository.getArticleById(export.getArticle());
    }
}


class ExportArticleParser{

    String pattern;

    DbArticle article;

    public ExportArticleParser(String pattern, DbArticle data){
        this.pattern = pattern;
        this.article = data;
    }

    public String parse(){
            return null;
    }
}