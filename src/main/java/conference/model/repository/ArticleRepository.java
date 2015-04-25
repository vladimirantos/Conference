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

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Random;

public class ArticleRepository {
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
            //zachycen� v�jimky p�id�n� �l�nku se stejn�m n�zvem
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
                "c.city, c.state, file_name, MATCH(a.name, a.abstract)AGAINST('" + word + "') AS score FROM articles as a " +
                "join conferences as c on c.id_conference = a.conference " +
                "WHERE MATCH(a.name, a.abstract) AGAINST('" + word + "') ORDER BY score DESC";
        return template.query(sql, new ArticleMapper().setArticleRepository(this));
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
