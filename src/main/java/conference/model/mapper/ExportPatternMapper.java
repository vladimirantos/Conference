package conference.model.mapper;

import conference.model.entity.Export;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vladimír on 30. 4. 2015.
 */
public class ExportPatternMapper implements RowMapper<Export>{
    @Override
    public Export mapRow(ResultSet resultSet, int i) throws SQLException {
        Export export = new Export();
        export.setIdPattern(resultSet.getInt("id_pattern"));
        export.setName(resultSet.getString("name"));
        export.setPattern(resultSet.getString("pattern"));
        return export;
    }
}
