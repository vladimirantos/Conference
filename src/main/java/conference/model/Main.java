package conference.model;

import conference.model.entity.DbArticle;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tokens{
    public static final String name = "jmeno";
    public static final String lastName = "prijmeni";
    public static final String title = "nadpis";
    public static final String conference = "konference";
    public static final String theme = "tema";
    public static final String date = "datum";
    public static final String month = "mesic";
    public static final String year = "rok";
    public static final String address = "adresa";
    public static final String city = "mesto";
    public static final String state = "stat";
}

class Parser{
    private final String regex = "(?<tag>\\[((autor\\s-(f=\\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b|(d=\\{.+\\})))+|.+))\\}))|nadpis|konference|tema|(datum\\s-(df=\\{(((m{0,2}|M).([yY])?)|(([yY].)?(m{0,2}|M)))\\}))|mesic|rok|adresa|mesto|stat)(\\s-(u|l|1|i|b))*\\])";

    private String pattern;

    private DbArticle article;

    public Parser(String pattern, DbArticle data){
        this.pattern = pattern;
        article = data;
    }
}

public class Main {

    public static void main(String[] args){
        //autor\s-(f=\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b))+|.+))\})
        String regex1 = "(?<tag>\\[(jmeno|prijmeni|nadpis|konference|tema|(datum\\s)|mesic|rok|adresa|mesto|stat)(\\s-(u|l|1|i|b|(d=\\(.+\\))|(f=\\((mm|m|M|y|Y).+(mm|m|M|y|Y)\\))))*\\])";
        String regex = "(?<tag>\\[((autor\\s-(f=\\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b|(d=\\{.+\\})))+|.+))\\}))|nadpis|konference|tema|(datum\\s-(df=\\{(((m{0,2}|M).([yY])?)|(([yY].)?(m{0,2}|M)))\\}))|mesic|rok|adresa|mesto|stat)(\\s-(u|l|1|i|b))*\\])";

        String pattern = "[autor -f={jmeno -b -u -d={, }}, {prijmeni -d={, }}]: [nadpis] - [konference], [datum -df={Y M}]";
//((m{0,2}|M)(.[yY])?)|(([yY])?(.(m{0,2}|M)))
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pattern);
        while (m.find()){
            if(!m.group().isEmpty()) {
                System.out.println(m.group("tag"));
            }
        }
    }
}
