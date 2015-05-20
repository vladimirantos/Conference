package conference.model;

import com.sun.javafx.fxml.expression.Expression;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import conference.model.entity.Author;
import conference.model.entity.Conference;
import conference.model.entity.DbArticle;

import javax.print.DocFlavor;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
//Omezení - mezi jménem a příjmením mohou být max 3 znaky
    public static void main(String[] args){
        //Parser p = new Parser("[autor -f={prijmeni -d={, } -b}, {jmeno -b -u -d={, }}]: [nadpis -i -u -fl] - [konference -fu], [datum -df={Y M}]", getArticle());
        //System.out.println(p.replace());

        ArticleParser p = new ArticleParser("[autor -f={jmeno -1 -l} {prijmeni -u -d={, }}]: [nadpis -u], [konference -fl]: [adresa] [mesto -fu], [stat]. [[datum -df={Y.M}]].", getArticle());
        //ArticleParser p = new ArticleParser("[konference -fl] - [datum -f={, }]", getArticle());
        System.out.println(p.replace());

        //autor\s-(f=\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b))+|.+))\})
//        String regex1 = "(?<tag>\\[(jmeno|prijmeni|nadpis|konference|tema|(datum\\s)|mesic|rok|adresa|mesto|stat)(\\s-(u|l|1|i|b|(d=\\(.+\\))|(f=\\((mm|m|M|y|Y).+(mm|m|M|y|Y)\\))))*\\])";
//        String regex = "(?<tag>\\[((autor\\s-(f=\\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b|(d=\\{.+\\})))+|.+))\\}))|nadpis|konference|tema|(datum\\s-(df=\\{(((m{0,2}|M).([yY])?)|(([yY].)?(m{0,2}|M)))\\}))|mesic|rok|adresa|mesto|stat)(\\s-(u|l|1|i|b))*\\])";
//
//        String pattern = "[autor -f={jmeno -b -u -d={, }}, {prijmeni -d={, }}]: [nadpis] - [konference], [datum -df={Y M}]";
////((m{0,2}|M)(.[yY])?)|(([yY])?(.(m{0,2}|M)))
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(pattern);
//        while (m.find()){
//            if(!m.group().isEmpty()) {
//                System.out.println(m.group("tag"));
//            }
//        }
    }

    public static DbArticle getArticle(){
        List<Author> authors = new LinkedList<Author>();
        Author a = new Author();
        a.setName("Vladimír");
        a.setLastName("Antoš");
        Author a1 = new Author();
        a1.setName("Karel");
        a1.setLastName("Dvorak");
        authors.add(a);
        authors.add(a1);

        Conference c = new Conference();
        c.setName("zrUŠMe JavU");
        c.setTheme("vypnutí javy");
        c.setAddress("TUL");
        c.setCity("Liberec");
        c.setState("Česká republika");
        c.setMonth(5);
        c.setYear(2015);
        DbArticle article = new DbArticle();
        article.setName("Java je na prd");
        article.setAuthors(authors);
        article.setConferenceObj(c);
        return article;
    }
}
