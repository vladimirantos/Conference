package conference.model.repository;

import conference.model.entity.Conference;

import java.util.List;

public interface IConferenceRepository {
    public void insert(Conference conference);

    public Conference findById(long id);

    public List<Conference> findAll();
}
