package conference.model;

import conference.model.entity.DbArticle;
import conference.model.entity.SearchAttributes;
import conference.model.entity.SearchTypes;
import conference.model.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchFactory {

    private SearchAttributes attributes;

    @Autowired
    private ArticleRepository articleRepository;

    public SearchFactory setAttributes(SearchAttributes searchAttributes){
        this.attributes = searchAttributes;
        return this;
    }

    public <E> List<E> search(){
        ISearch searcher = prepare();
        return searcher.search();
    }

    private ISearch prepare(){
        SearchTypes type = attributes.getSearchType();
        switch (type){
            case TITLE:
                ArticleSearch articleSearch = new ArticleSearch(attributes.getSearchType(), attributes.getText());
                articleSearch.setArticleRepository(articleRepository);
                return articleSearch;
        }

        throw new UnsupportedOperationException("Illegal type of search");
    }
}

interface ISearch<E> {
    List<E> search();
}

class ArticleSearch implements ISearch {

    private SearchTypes type;

    private String text;

    private ArticleRepository articleRepository;

    public void setArticleRepository(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public ArticleSearch(SearchTypes type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public List<DbArticle> search() {
        return articleRepository.fullTextSearch(text);
    }
}

