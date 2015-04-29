package conference.model;

import conference.model.entity.Author;
import conference.model.entity.DbArticle;
import conference.model.entity.SearchAttributes;
import conference.model.entity.SearchTypes;
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
//            String[] authorsName = s.split(" ");
//            Author author = new Author();
//            author.setLastName(authorsName[0]);
//            if(authorsName.length > 1) //Kontrola jestli bylo zadáno i jméno
//                author.setName(authorsName[1]);
            authors.add(Author.fromString(s.trim()));
        }
        return authors;
    }
}