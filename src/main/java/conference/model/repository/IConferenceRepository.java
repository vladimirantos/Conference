package conference.model.repository;

import conference.model.entity.Conference;

import java.util.List;

/**
 * Created by Vladimír on 27. 3. 2015.
 */
public interface IConferenceRepository {
    public void insert(Conference conference);

    public Conference findById(long id);

    public List<Conference> findAll();
}
