package conference.model.entity;

import java.util.Date;
import java.util.List;

public class DbArticle {
    private Long id;

    private long conference;

    private Conference conferenceObj;

    private List<Author> authors;

    private String name;

    private String abstrct;

    private int numberPages;

    private Date insertionDate;

    private String fileName;

    public DbArticle(){

    }

    public DbArticle(long id, long conference, List<Author> authors, String name, String abstrct,
                     int numberPages, Date insertionDate) {
        this.id = id;
        this.conference = conference;
        this.authors = authors;
        this.name = name;
        this.abstrct = abstrct;
        this.numberPages = numberPages;
        this.insertionDate = insertionDate;
    }

    public Long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConference() {
        return conference;
    }

    public void setConference(long conference) {
        this.conference = conference;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getName() {
        return name.trim();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbstrct() {
        return abstrct;
    }

    public void setAbstrct(String abstrct) {
        this.abstrct = abstrct;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Conference getConferenceObj() {
        return conferenceObj;
    }

    public void setConferenceObj(Conference conferenceObj) {
        this.conferenceObj = conferenceObj;
    }

    public String authors(){
        StringBuilder sb = new StringBuilder();
        for(Author author : this.authors){
            sb.append(author.getLastName().toUpperCase()+" "+author.getName()+", ");
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 2);
    }
}
