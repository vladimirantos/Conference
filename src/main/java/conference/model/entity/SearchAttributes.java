package conference.model.entity;


public class SearchAttributes {
    private String text;

    private SearchTypes type;

    private int tmpType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SearchTypes getSearchType() {
        switch (tmpType) {
            case 1:
                return SearchTypes.ARTICLE;
            case 2:
                return SearchTypes.AUTHORS;
            case 3:
                return SearchTypes.YEAR;
        }
        throw new UnsupportedOperationException("Illegal type of search");
    }

    public void setType(int type) {
        this.tmpType = type;
    }

    public int getType(){
        return tmpType;
    }
}
