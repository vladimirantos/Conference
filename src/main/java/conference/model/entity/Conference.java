package conference.model.entity;

import conference.configuration.Constants;
import conference.model.FileStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Conference {
    private Long id;
    private String name;
    private String theme;
    private String address;
    private String city;
    private String state;
    private int month;
    private int year;

    public Long getId(){
        if(id == null){
            Date d = new Date();
            id = d.getTime();
        }
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getCreationDate() {
        return new Date(id);
    }

    public int getCountArticles(){
        return FileStorage.countFiles(String.valueOf(id));
    }

    @Override
    public String toString() {
        return Character.toString(name.charAt(0)).toUpperCase()+name.substring(1);
    }

    /**
     * Vytvoří složku do které se budou nahrávat články.
     */
    public void createDir(){
        FileStorage.createDir(String.valueOf(id));
    }
}
