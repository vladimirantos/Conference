package conference.model;

import conference.model.entity.*;
import conference.model.repository.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SearchFactory {

    private SearchAttributes attributes;

    @Autowired
    private IArticleRepository articleRepository;

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
            case ARTICLE:
                ArticleSearch articleSearch = new ArticleSearch(attributes.getSearchType(), attributes.getText());
                articleSearch.setArticleRepository(articleRepository);
                return articleSearch;
            case AUTHORS:
                AuthorsSearch authorsSearch = new AuthorsSearch(attributes.getSearchType(), attributes.getText());
                authorsSearch.setArticleRepository(articleRepository);
                return authorsSearch;
            case YEAR:
                ConferenceSearch conferenceSearch = new ConferenceSearch(attributes.getSearchType(), attributes.getText());
                conferenceSearch.setArticleRepository(articleRepository);
                return conferenceSearch;
        }
        throw new UnsupportedOperationException("Illegal type of search");
    }
}

interface ISearch<E> {
    List<E> search();
}

abstract class AbstractSearch implements ISearch{
    protected IArticleRepository articleRepository;

    protected SearchTypes type;

    protected String text;

    public AbstractSearch(SearchTypes type, String text) {
        this.type = type;
        this.text = text;
    }

    protected void setArticleRepository(IArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }
}

class ArticleSearch extends AbstractSearch{

    public ArticleSearch(SearchTypes type, String text){
        super(type, text);
    }

    @Override
    public List<DbArticle> search() {
        return articleRepository.fullTextSearch(text);
    }
}

class AuthorsSearch extends AbstractSearch{

    public AuthorsSearch(SearchTypes types, String text){
        super(types, text);
    }

    @Override
    public List<DbArticle> search() {
        List<Author> authors = parseAuthor();
        return articleRepository.getArticlesByAuthors(authors);
    }

    private List<Author> parseAuthor(){
        ArrayList<Author> authors = new ArrayList<Author>();
        String[] parseAuthors = text.split(",");

        for(String s : parseAuthors){
            authors.add(Author.fromString(s.trim()));
        }
        return authors;
    }
}

class ConferenceSearch extends AbstractSearch{

    public ConferenceSearch(SearchTypes type, String text) {
        super(type, text);
    }

    @Override
    public List<DbArticle> search() {
        if(type == SearchTypes.YEAR) {
            if (text.matches("(\\d{4})\\s?-\\s?(\\d{4})")) { //vyhledávání v rozmezí let
                String[] years = text.split("-");
                return searchByYear(Integer.parseInt(years[0].trim()), Integer.parseInt(years[1].trim()));
            }
            return searchByYear();
        }
        return new ArrayList<DbArticle>();
    }

    private List<DbArticle> searchByYear() {
        return articleRepository.getArticlesByYear(Integer.parseInt(text));
    }

    private List<DbArticle> searchByYear(int year1, int year2) {
        return articleRepository.getArticlesByYear(year1, year2);
    }
}