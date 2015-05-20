package conference.model.repository;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import conference.exceptions.ApplicationException;
import conference.model.Log;
import conference.model.entity.Export;
import conference.model.mapper.ExportPatternMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vladimír on 30. 4. 2015.
 */
public class ExportPatternRepository implements IExportRepository {

    private DataSource dataSource;

    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        template = new JdbcTemplate(this.dataSource);
    }

    @Override
    public void insert(Export export) {
        String sql = "INSERT INTO export_patterns(name, pattern)VALUES(?,?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, export.getName());
            ps.setString(2, export.getPattern());
            ps.executeUpdate();
            ps.close();
        }catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex){
            throw new EntityExistsException("Tento formát už existuje.");
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

    @Override
    public List<Export> selectPatterns() {
        String sql = "SELECT id_pattern, name, pattern FROM export_patterns ORDER BY id_pattern DESC, name ASC";
        return template.query(sql, new ExportPatternMapper());
    }

    @Override
    public Export getPatternById(int id) {
        Log.message("ida", String.valueOf(id), this);
        String sql = "SELECT id_pattern, name, pattern FROM export_patterns WHERE id_pattern = ?";
        return template.queryForObject(sql, new Object[]{id}, new ExportPatternMapper());
    }
}
