package conference.model.mapper;

import conference.model.entity.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vladimír on 24. 4. 2015.
 */
public class AuthorMapper implements RowMapper<Author>{
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        Author author = new Author();
        author.setIdArticle(resultSet.getInt("id_article"));
        author.setName(resultSet.getString("name"));
        author.setLastName(resultSet.getString("last_name"));
        author.setInstitut(resultSet.getString("institut"));
        author.setUniversity(resultSet.getString("university"));
        author.setCity(resultSet.getString("city"));
        author.setState(resultSet.getString("state"));
        return author;
    }
}
