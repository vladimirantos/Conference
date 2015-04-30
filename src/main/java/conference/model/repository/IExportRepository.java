package conference.model.repository;

import conference.model.entity.Export;

import java.util.List;

/**
 * Created by Vladimír on 30. 4. 2015.
 */
public interface IExportRepository {
    void insert(Export export);

    List<Export> selectPatterns();

    List<Export> getPatternById();
}
