package conference.model.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ArticleRepository {
    private DataSource dataSource;

    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        template = new JdbcTemplate(this.dataSource);
    }
}
