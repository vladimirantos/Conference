package conference.model.entity;

public enum SearchTypes {
    ARTICLE(1, "Článek", "articles"),

    AUTHORS(2, "Autoři", "article_authors"),

    CONFERENCE(3, "Konference", "conferences");

    private int id;
    private String name;
    private String table;

    private SearchTypes(int id, String name, String table){
        this.id = id;
        this.name = name;
        this.table = table;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
