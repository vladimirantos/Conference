package conference.model.entity;

/**
 * Created by Vladimír on 30. 4. 2015.
 */
public class Export {
    int idPattern = 0;

    int idArticle;

    String name;

    String pattern;

    public int getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(int idPattern) {
        this.idPattern = idPattern;
    }

    public String getName() {
        return name.toUpperCase();
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

    public int getArticle() {
        return idArticle;
    }

    public void setArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    @Override
    public String toString() {
        return pattern;
    }
}
