package conference.model.repository;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import conference.configuration.Constants;
import conference.model.Log;
import conference.model.entity.Article;
import conference.model.entity.Author;
import conference.model.entity.DbArticle;
import conference.model.mapper.ArticleMapper;
import conference.model.mapper.AuthorMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.awt.print.Pageable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ArticleRepository implements IArticleRepository{
    private DataSource dataSource;

    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new JdbcTemplate(this.dataSource);
    }

    public void insertAll(final List<DbArticle> articles) {
        String sql = "INSERT INTO articles(conference, name, abstract, number_pages, file_name) VALUES(?,?,?,?,?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (DbArticle article : articles) {
                ps.setLong(1, article.getConference());
                ps.setString(2, article.getName());
                ps.setString(3, article.getAbstrct());
                ps.setInt(4, article.getNumberPages());
                ps.setString(5, article.getFileName());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    insertAuthors(article.getAuthors(), id);
                }
            }
        }catch(MySQLIntegrityConstraintViolationException ex){
            //zachycení výjimky pri přidávání článku se stejným jménem
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public List<DbArticle> fullTextSearch(String word){
        String sql = "SELECT DISTINCT a.id_article, a.conference as id_conference, a.name as title, abstract, number_pages, insertion_date, " +
                "c.name as conference, c.theme, c.month, c.year, c.building, " +
                "c.city, c.state, file_name, MATCH(a.name, a.abstract)AGAINST(?) AS score FROM articles as a " +
                "join conferences as c on c.id_conference = a.conference " +
                "WHERE MATCH(a.name, a.abstract) AGAINST(?) ORDER BY score DESC";
        return template.query(sql,new Object[]{word, word}, new ArticleMapper().setArticleRepository(this));
    }

    public List<DbArticle> fullTextSearch(String word, Pageable pageable){
        return new ArrayList<DbArticle>();
    }

    public List<DbArticle> getArticlesByAuthors(List<Author> authors){
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
        String sql = "select DISTINCT a.id_article, a.conference as id_conference, a.name as title, abstract, number_pages, insertion_date," +
                "c.name as conference, c.theme, c.month, c.year, c.building," +
                " c.city, c.state, file_name " +
                "from article_authors as au " +
                "join articles as a on a.id_article = au.id_article\n" +
                "join conferences as c on c.id_conference = a.conference\n" +
                "where ";

        int i = 0;
        List<String> names = new ArrayList<String>();
        List<String> lastNames = new ArrayList<String>();
        boolean hasName = false;
        for(Author a : authors){
            if(a.getName() != null){
                names.add(a.getName());
                hasName = true;
            }
            lastNames.add(a.getLastName());
        }
        sql += (hasName ? "au.name IN(:names) and " : " ") +"au.last_name IN(:lastNames) ORDER BY title ASC, insertion_date DESC";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        if(hasName)
            parameters.addValue("names", names);
        parameters.addValue("lastNames", lastNames);
        return namedParameterJdbcTemplate.query(sql, parameters, new ArticleMapper().setArticleRepository(this));
    }

    /**
     * Vyhledá články napsané v zadaném roce.
     */
    @Override
    public List<DbArticle> getArticlesByYear(int year) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
        String sql = "select DISTINCT a.id_article, a.conference as id_conference, a.name as title, abstract, number_pages, insertion_date," +
                "c.name as conference, c.theme, c.month, c.year, c.building," +
                " c.city, c.state, file_name " +
                "from article_authors as au " +
                "join articles as a on a.id_article = au.id_article " +
                "join conferences as c on c.id_conference = a.conference " +
                "where c.year=(:year) ORDER BY title ASC, insertion_date DESC";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("year", year);
        return namedParameterJdbcTemplate.query(sql, parameters, new ArticleMapper().setArticleRepository(this));
    }

    /**
     * Vyhledává články které jsou v rozmezí year1 a year2.
     */
    @Override
    public List<DbArticle> getArticlesByYear(int year1, int year2) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
        String sql = "select DISTINCT a.id_article, a.conference as id_conference, a.name as title, abstract, number_pages, insertion_date," +
                "c.name as conference, c.theme, c.month, c.year, c.building," +
                " c.city, c.state, file_name " +
                "from article_authors as au " +
                "join articles as a on a.id_article = au.id_article " +
                "join conferences as c on c.id_conference = a.conference " +
                "where c.year BETWEEN (:year1) and (:year2) ORDER BY title, year ASC";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("year1", year1);
        parameters.addValue("year2", year2);
        return namedParameterJdbcTemplate.query(sql, parameters, new ArticleMapper().setArticleRepository(this));
    }

    @Override
    public DbArticle getArticleById(int id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
        String sql = "select DISTINCT a.id_article, a.conference as id_conference, a.name as title, abstract, number_pages, insertion_date," +
                "c.name as conference, c.theme, c.month, c.year, c.building," +
                " c.city, c.state, file_name " +
                "from article_authors as au " +
                "join articles as a on a.id_article = au.id_article " +
                "join conferences as c on c.id_conference = a.conference " +
                "where a.id_article=(:id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        return namedParameterJdbcTemplate.query(sql, parameters, new ArticleMapper().setArticleRepository(this)).get(0);
    }

    public List<Author> getAuthors(long idArticle) {
        String sql = "SELECT id_article, name, last_name, institut, university, city, state FROM article_authors WHERE id_article = ?";
        return template.query(sql, new Object[]{idArticle}, new AuthorMapper());
    }

    private void insertAuthors(List<Author> authors, int id_article) {
        String sql = "INSERT INTO article_authors(id_article, name, last_name, institut, university, city, state)VALUES(?,?,?,?,?,?,?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (Author author : authors) {
                ps.setInt(1, id_article);
                ps.setString(2, author.getName());
                ps.setString(3, author.getLastName());
                ps.setString(4, author.getInstitut());
                ps.setString(5, author.getUniversity());
                ps.setString(6, author.getCity());
                ps.setString(7, author.getState());
                ps.executeUpdate();
//                ResultSet rs = ps.getGeneratedKeys();
//                if (rs.next()) {
//                    int id = rs.getInt(1);
//                    PreparedStatement preparedStatement =
//                            connection.prepareStatement("INSERT INTO authors_articles(author, article)VALUES(?,?)");
//                    preparedStatement.setInt(1, id);
//                    preparedStatement.setInt(2, id_article);
//                    preparedStatement.executeUpdate();
//                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}
