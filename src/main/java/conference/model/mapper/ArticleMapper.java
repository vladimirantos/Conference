package conference.model.mapper;

import conference.model.entity.Author;
import conference.model.entity.Conference;
import conference.model.entity.DbArticle;
import conference.model.repository.ArticleRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleMapper implements RowMapper<DbArticle>{

    private ArticleRepository articleRepository;

    public ArticleMapper setArticleRepository(ArticleRepository repository){
        articleRepository = repository;
        return this;
    }

    @Override
    public DbArticle mapRow(ResultSet resultSet, int i) throws SQLException {
        Conference conference = new Conference();
        conference.setId(resultSet.getLong("id_conference"));
        conference.setName(resultSet.getString("conference"));
        conference.setTheme(resultSet.getString("theme"));
        conference.setAddress(resultSet.getString("building"));
        conference.setCity(resultSet.getString("city"));
        conference.setState(resultSet.getString("state"));

        DbArticle article = new DbArticle();
        article.setId(resultSet.getInt("id_article"));
        article.setName(resultSet.getString("title"));
        article.setAbstrct(resultSet.getString("abstract"));
        article.setConferenceObj(conference);
        article.setAuthors(articleRepository.getAuthors(article.getId()));
        return article;
    }
}
