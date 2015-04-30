package conference.model.entity;

/**
 * Created by Vladimír on 30. 4. 2015.
 */
public class Export {
    int id_export;

    String name;

    String pattern;

    public int getId_export() {
        return id_export;
    }

    public void setId_export(int id_export) {
        this.id_export = id_export;
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
}
