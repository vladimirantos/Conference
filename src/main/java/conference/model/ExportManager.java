package conference.model;

import conference.model.entity.Export;
import conference.model.repository.IExportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExportManager {

    @Autowired
    private IExportRepository exportRepository;

    public void save(Export export){
        exportRepository.insert(export);
    }

    public List<Export> getPatterns(){
        return exportRepository.selectPatterns();
    }
}
