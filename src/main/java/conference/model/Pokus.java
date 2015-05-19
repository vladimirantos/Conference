//package conference.model;
//
//import com.sun.javafx.fxml.expression.Expression;
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
//import conference.model.entity.Author;
//import conference.model.entity.Conference;
//import conference.model.entity.DbArticle;
//import org.apache.commons.lang.ArrayUtils;
//
//import java.util.*;
//import java.util.regex.MatchResult;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//class Tokenks{
//    public static final String name = "jmeno";
//    public static final String lastName = "prijmeni";
//    public static final String title = "nadpis";
//    public static final String conference = "konference";
//    public static final String theme = "tema";
//    public static final String date = "datum";
//    public static final String month = "mesic";
//    public static final String year = "rok";
//    public static final String address = "adresa";
//    public static final String city = "mesto";
//    public static final String state = "stat";
//
//    public static String tokensRegex(){
//        return "(jmeno|prijmeni|nadpis|konference|tema|datum|mesic|rok|adresa|mesto|stat)";
//    }
//}
//
//class Parkser{
//
//    String pattern;
//
//    //Kompletní regulární výraz
//    String patternRegex = "(?<tag>\\[(jmeno|prijmeni|nadpis|konference|tema|datum|mesic|rok|adresa|mesto|stat)(\\s-(u|l|1|i|b|(d=\\(.+\\))|(f=\\((mm|m|M|y|Y).+(mm|m|M|y|Y)\\))))*\\])";//"(?<tag>\\[(jmeno|prijmeni|nadpis|konference|tema|datum|mesic|rok|budova|mesto|stat)(\\s-(u|l|1|i|b|(d=.+)))*\\])";
//
//    //Formátovací značky
//    String formatterRegex = "(\\s-(u|l|1|i|b|(d=\\(.+\\))|(f=\\((mm|m|M|y|Y).+(mm|m|M|y|Y)\\))))*";
//
//    List<String> tokens = new LinkedList<String>();
//
//    DbArticle article;
//
//    public Parser(String pattern, DbArticle data){
//        this.pattern = pattern;
//        article = data;
//        tokens = createTokens();
//    }
//
//    public String replace(){
//        for(String token : tokens){
//            String text = replaceToken(token);
//            if(text != null)
//                this.pattern = this.pattern.replace(token, text);
//        }
//        return pattern;
//    }
//
//    /**
//     * Metoda přijímá token včetně formátovacích značek a vrací naformátovaný řetězec.
//     */
//    private String replaceToken(String token){
//        token = token.replaceAll("(\\[|\\])", " ");
//        String pattern = "(?<tokenName>"+Tokens.tokensRegex()+")(?<tokenArgs>(\\s-(u|l|1|i|b|(d=\\(.+\\))|(f=\\((mm|m|M|y|Y).+(mm|m|M|y|Y)\\))))*)";
//        Pattern p = Pattern.compile(pattern);
//        Matcher m = p.matcher(token);
//        String tokenName = new String();
//        String tokenArgs = new String();
//        while (m.find()){
//             tokenName = m.group("tokenName");
//             tokenArgs = m.group("tokenArgs"); //trim - na začátku je mezera
//        }
//        if(tokenName.equals(Tokens.name))
//            return formatText(article.getAuthors(), tokenArgs, Tokens.name);
//        if(tokenName.equals(Tokens.lastName))
//            return formatText(article.getAuthors(), tokenArgs, Tokens.lastName);
//        if(tokenName.equals(Tokens.title))
//            return formatText(article.getName(), tokenArgs);
//        else if(tokenName.equals(Tokens.conference))
//            return formatText(article.getConferenceObj().getName(), tokenArgs);
//        else if(tokenName.equals(Tokens.theme))
//            return formatText(article.getConferenceObj().getTheme(), tokenArgs);
//        else if(tokenName.equals(Tokens.month))
//            return formatText(String.valueOf(article.getConferenceObj().getMonth()), tokenArgs);
//        else if(tokenName.equals(Tokens.year))
//            return formatText(String.valueOf(article.getConferenceObj().getYear()), tokenArgs);
//        else if(tokenName.equals(Tokens.address))
//            return formatText(article.getConferenceObj().getAddress(), tokenArgs);
//        else if(tokenName.equals(Tokens.city))
//            return formatText(article.getConferenceObj().getCity(), tokenArgs);
//        else if(tokenName.equals(Tokens.state))
//            return formatText(article.getConferenceObj().getState(), tokenArgs);
//        return null;
//    }
//
//    /**
//     * Naformátuje řetězec podle zadaných značek
//     * @param text
//     * @param args Řetězec argumentů z tokenu
//     * @return Vrací naformátovaný řetězec
//     */
//    private String formatText(String text, String args) {
//        List<String> arguments = new ArrayList<String>();
//        Pattern p = Pattern.compile("\\s-(?<tokenArg>(u|l|1|i|b|(d=\\(.+\\))|(f=\\((mm|m|M|y|Y).+(mm|m|M|y|Y)\\)))*)");
//        Matcher m = p.matcher(args);
//        while (m.find()) {   //pole formátovacích argumentů
//            arguments.add(m.group("tokenArg"));
//        }
//
//        for (String arg : arguments) {
//            if (arg.equals("u"))
//                text = text.toUpperCase();
//            if (arg.equals("l"))
//                text = text.toLowerCase();
//        }
//
//        return text;
//    }
//
//    /**
//     * @param args
//     * @param authors seznam autorů
//     * @param type jméno nebo příjmení
//     * @return
//     */
//    private String formatText(List<Author> authors, String args, String type){
//        String result = null;
//        for(Author author: authors){
//            result += type.equals(Tokens.name) ? formatText(author.getName(), args) : formatText(author.getLastName(), args);
//        }
//        return result;
//    }
//
//    /**
//     * @return Vrací seznam tokenů
//     */
//    private List<String> createTokens(){
//        Pattern p = Pattern.compile(patternRegex);
//        Matcher m = p.matcher(pattern);
//        List<String> tmptokens = new LinkedList<String>();
//        while (m.find()){
//            if(!m.group().isEmpty()) {
//                tmptokens.add(m.group());
//                //System.out.println(m.group());
//            }
//        }
//        return tmptokens;
//    }
//}
//
//public class Pokus {
//
//    public static DbArticle getArticle(){
//        List<Author> authors = new LinkedList<Author>();
//        Author a = new Author();
//        a.setName("Vladimír");
//        a.setLastName("Antoš");
//        Author a1 = new Author();
//        a1.setName("Karel");
//        a1.setLastName("Dvorak");
//        authors.add(a);
//        authors.add(a1);
//
//        Conference c = new Conference();
//        c.setName("Zrušme Javu");
//        c.setTheme("Téma: vypnutí javy");
//        c.setAddress("TUL");
//        c.setCity("Liberec");
//        c.setState("Česká republika");
//        c.setMonth(5);
//        c.setYear(2015);
//        DbArticle article = new DbArticle();
//        article.setName("Název článku");
//        article.setAuthors(authors);
//        article.setConferenceObj(c);
//        return article;
//    }
//
//    public static void main(String[] args){
//        //String pattern = "[jmeno] [prijmeni -d=(, ) -u -l]: [nadpis -u]. [datum] - [konference -u], [adresa], [mesto] [stat]";
//        String pattern = "[jmeno] [prijmeni]";
//
//        Parser p = new Parser(pattern, getArticle());
//        System.out.println(p.replace());
////        String regex = "(?<tag>\\[(jmeno|prijmeni|nadpis|konference|tema|datum|mesic|rok|budova|mesto|stat)(\\s-(u|l|1|i|b|(d=\\(.+\\))|(f=\\((mm|m|M|y|Y).+(mm|m|M|y|Y)\\))))*\\])";
////        Pattern pattern1 = Pattern.compile(regex); //Pattern.compile("(?<tag>\\[(jmeno|prijmeni|nadpis|konference|tema|datum|mesic|rok|budova|mesto|stat)(\\s-(u|l|1|i|b|(d=.+)))*\\])");
////        Matcher matcher = pattern1.matcher(pattern);
////
////        String jmeno = "Vlada";
////        String prijmeni = "Antos";
////        String result = null;
////        while(matcher.find()){
////            System.out.println(matcher.group("tag"));
////
////        }
//
//    }
//}
