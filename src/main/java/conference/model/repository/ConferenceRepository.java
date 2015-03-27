package conference.model.repository;

import conference.model.Log;
import conference.model.entity.Conference;
import conference.model.mapper.ConferenceMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

public class ConferenceRepository implements IConferenceRepository{

    private DataSource dataSource;

    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        template = new JdbcTemplate(this.dataSource);
    }

    @Override
    public void insert(Conference conference) {
        String sql = "INSERT INTO conferences(id_conference, name, theme, month, year, building, city, state)" +
                "VALUES(?,?,?,?,?,?,?,?)";
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, conference.getId());
            ps.setString(2, conference.getName());
            ps.setString(3, conference.getTheme());
            ps.setInt(4, conference.getMonth());
            ps.setInt(5, conference.getYear());
            ps.setString(6, conference.getAddress());
            ps.setString(7, conference.getCity());
            ps.setString(8, conference.getState());
            ps.executeUpdate();
            ps.close();
        }catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityExistsException("Tato konference již byla vytvořena.");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public Conference findById(long id) {
        return new Conference();
    }

    @Override
    public List<Conference> findAll() {
        String sql = "SELECT id_conference, name, theme, building, city, state, month, year FROM conferences ORDER BY id_conference DESC, name ASC";
        List <Conference> students = template.query(sql,
                new ConferenceMapper());
        return students;
    }


}
