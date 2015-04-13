package conference.model.repository;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import conference.configuration.Constants;
import conference.model.Log;
import conference.model.entity.Article;
import conference.model.entity.Author;
import conference.model.entity.DbArticle;
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
        String sql = "INSERT INTO articles(conference, name, abstract, number_pages) VALUES(?,?,?,?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (DbArticle article : articles) {

                ps.setLong(1, article.getConference());
                ps.setString(2, article.getName());
                ps.setString(3, article.getAbstrct());
                ps.setInt(4, article.getNumberPages());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    insertAuthors(article.getAuthors(), id);
                }
            }
        }catch(MySQLIntegrityConstraintViolationException ex){
            //zachycení výjimky pøidání èlánku se stejným názvem
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

    private void insertAuthors(List<Author> authors, int id_article) {
        String sql = "INSERT INTO authors(name, last_name, institut, university, city, state)VALUES(?,?,?,?,?,?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (Author author : authors) {
                ps.setString(1, author.getName());
                ps.setString(2, author.getLastName());
                ps.setString(3, author.getInstitut());
                ps.setString(4, author.getUniversity());
                ps.setString(5, author.getCity());
                ps.setString(6, author.getState());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    PreparedStatement preparedStatement =
                            connection.prepareStatement("INSERT INTO authors_articles(author, article)VALUES(?,?)");
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, id_article);
                    preparedStatement.executeUpdate();
                }
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
//        template.batchUpdate("INSERT INTO articles(id_article, conference, name, abstract, number_pages)VALUES(?,?,?,?,?)",
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                        DbArticle article = articles.get(i);
//                        long id = createId("SELECT count(*) as count from articles where id_article = ?");
//                        article.setId(id);
//                        preparedStatement.setLong(1, id);
//                        preparedStatement.setLong(2, article.getConference());
//                        preparedStatement.setString(3, article.getName());
//                        preparedStatement.setString(4, article.getAbstrct());
//                        preparedStatement.setInt(5, article.getNumberPages());
//                    }
//
//                    @Override
//                    public int getBatchSize() {
//                        return articles.size();
//                    }
//                });
//        insertAuthors(articles);
//    }

//    private void insertAuthors(final List<DbArticle> articles) {
//        template.batchUpdate("INSERT INTO authors(id_author, name, last_name, institut, university, city, state)VALUES(?, ?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                try{
//                    DbArticle article = articles.get(i);
//                    for(int x = 0; x < article.getAuthors().size(); x++) {
//                        Author a = article.getAuthors().get(x);
//                        long id = createId("SELECT count(*) as count from authors where id_author = ?");
//                        a.setId(id);
//                        preparedStatement.setLong(1, id);
//                        preparedStatement.setString(2, a.getName());
//                        preparedStatement.setString(3, a.getLastName());
//                        preparedStatement.setString(4, a.getInstitut());
//                        preparedStatement.setString(5, a.getUniversity());
//                        preparedStatement.setString(6, a.getCity());
//                        preparedStatement.setString(7, a.getState());
//                    }
//                }catch(BatchUpdateException ex){
//
//                }
//            }
//
//            @Override
//            public int getBatchSize() {
//                return articles.size();
//            }
//        });
//        insertAuthorsArticle(articles);
//    }
//
//    private void insertAuthorsArticle(final List<DbArticle> articles){

//        template.batchUpdate("INSERT INTO authors_articles(author, article)VALUES (?,?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                Author a = authors.get(i);
//                preparedStatement.setLong(1, a.getId());
//                preparedStatement.setLong(2, id_article);
//            }
//
//            @Override
//            public int getBatchSize() {
//                return authors.size();
//            }
//        });
//    }


//    private int createId(String sql){
//        int id;
//        do {
//            Random r = new Random();
//            id = r.nextInt(Constants.MAX_ID);
//        }while((int)(template.queryForObject(sql,
//                Integer.class, id))== 1);
//        return id;
//    }

}
