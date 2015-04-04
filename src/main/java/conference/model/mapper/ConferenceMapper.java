package conference.model.mapper;

import conference.model.entity.Conference;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceMapper implements RowMapper<Conference> {
    @Override
    public Conference mapRow(ResultSet resultSet, int i) throws SQLException {
        Conference conference = new Conference();
        conference.setId(resultSet.getLong("id_conference"));
        conference.setName(resultSet.getString("name"));
        conference.setTheme(resultSet.getString("theme"));
        conference.setAddress(resultSet.getString("building"));
        conference.setCity(resultSet.getString("city"));
        conference.setState(resultSet.getString("state"));
        conference.setMonth(resultSet.getInt("month"));
        conference.setYear(resultSet.getInt("year"));
        return conference;
    }
}
