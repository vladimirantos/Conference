package conference.model.repository;

import conference.model.entity.Author;
import conference.model.entity.DbArticle;

import java.awt.print.Pageable;
import java.util.List;


public interface IArticleRepository extends IRepository{
    void insertAll(List<DbArticle> articles);

    List<DbArticle> fullTextSearch(String word);

    List<DbArticle> fullTextSearch(String word, Pageable pageable);

    List<DbArticle> getArticlesByAuthors(List<Author> authors);

    List<Author> getAuthors(long idArticle);
}