package conference.model.entity;

/**
 * Created by Vladimír on 30. 4. 2015.
 */
public class Export {
    int idPattern;

    String name;

    String pattern;

    public int getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(int idPattern) {
        this.idPattern = idPattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }
}
