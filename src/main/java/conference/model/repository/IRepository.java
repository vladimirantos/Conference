package conference.model.repository;

import javax.sql.DataSource;

public interface IRepository {
    void setDataSource(DataSource dataSource);
}
