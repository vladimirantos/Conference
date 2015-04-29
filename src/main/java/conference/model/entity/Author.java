package conference.model.entity;

import java.util.Date;

public class Author {
    private Long idArticle;

    private String name;

    private String lastName;

    private String institut;

    private String university;

    private String city;

    private String state;

    public Long getIdArticle(){
        return idArticle;
    }

    public void setIdArticle(long id) {
        this.idArticle = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInstitut() {
        return institut;
    }

    public void setInstitut(String institut) {
        this.institut = institut;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }

    /**
     * Vytvoří objekt Author z řetězce příjmení jméno. Jméno nemusí být zadáno.
     * @param name
     * @return
     */
    public static Author fromString(String name){
        String[] split = name.split(" ");
        Author a = new Author();
        a.setLastName(split[0]);
        if(split.length > 1)
            a.setName(split[1]);
        return a;
    }
}
