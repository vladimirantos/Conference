package conference.model;

import conference.model.entity.Author;
import conference.model.entity.DbArticle;
import org.hibernate.annotations.SourceType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//todo: přidat fl a fu ke jménu
public class ArticleParser {

    //|(datum(\s-df=\{(((m{0,2}|M).([yY])?)|(([yY].)?(m{0,2}|M)))\})*)
    //(datum(\s-df=\{((m{0,2}|M)?.+([yY])?)|(([yY])?.+(m{0,2}|M)?)\})*)
    private final String regex = "(?<tag>\\[((autor\\s-f=(\\{(jmeno|prijmeni)(\\s-(u|l|1|i|b|fu|fl|(d=\\{.+\\})))*\\})?.{0,3}(\\{(jmeno|prijmeni)(\\s-(u|l|1|i|b|fu|fl|(d=\\{.+\\})))*\\})?)|nadpis|konference|isbn|(datum\\s-df=\\{(((m|M)?.*[yY]?)|([yY]?.*(m|M)?))\\})|mesic|rok|adresa|mesto|stat)(\\s-(u|l|i|b|fu|fl))*\\])";
    //(?<tag>\[((autor\s-(f=\{(jmeno|prijmeni)(((\s-(u|l|1|i|b|fu|fl|(d=\{.+\})))*|.+))\}))|nadpis|konference|isbn|datum(\s-(f=\{.+\}))?|mesic|rok|adresa|mesto|stat)(\s-(u|l|i|b|fu|fl))*\])
    //*(?<tag>\[((autor\s-f=(\{(jmeno|prijmeni)(\s-(u|l|1|i|b|fu|fl|(d=\{.+\})))*\})?.{0,3}(\{(jmeno|prijmeni)(\s-(u|l|1|i|b|fu|fl|(d=\{.+\})))*\})?)|nadpis|konference|isbn|(datum\s-f=\{(((m|mm|M)?.*[yY]?)|([yY]?.*(m|mm|M)?))\})|mesic|rok|adresa|mesto|stat)(\s-(u|l|i|b|fu|fl))*\])
   // (?<tag>\[((autor\s-(f=\{(jmeno|prijmeni)(((\s-(u|l|1|i|b|fu|fl|(d=\{.+\})))*|.+))\})?)|nadpis|konference|isbn|datum(\s-df=\{\})|mesic|rok|adresa|mesto|stat)(\s-(u|l|i|b|fu|fl))*\])

    //(?<tag>\[((autor\s-f=(\{(jmeno|prijmeni)(\s-(u|l|1|i|b|fu|fl|(d=\{.+\})))*\})?.{0,3}(\{(jmeno|prijmeni)(\s-(u|l|1|i|b|fu|fl|(d=\{.+\})))*\})?)|nadpis|konference|isbn|(datum\s-f=\{(((m|mm|M)?.*[yY]?)|([yY]?.*(m|mm|M)?))\})|mesic|rok|adresa|mesto|stat)(\s-(u|l|i|b|fu|fl))*\])

    private String pattern;

    private DbArticle article;

    private List<String> tokens = new LinkedList<String>();

    public ArticleParser(String pattern, DbArticle data){
        this.pattern = pattern;
        article = data;
        tokens = createTokens();
    }

    public String replace(){
        System.out.println(tokens.size());
        for(String token : tokens) {
            String text = parseToken(token);
            if (text != null)
                this.pattern = this.pattern.replace(token, text);
        }
        return pattern;
    }

    /**
     * @return Vrací seznam tokenů vytvořených ze zadaného výrazu
     */
    private List<String> createTokens(){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pattern);
        List<String> tmptokens = new LinkedList<String>();
        while (m.find()){
            if(!m.group().isEmpty()) {
                tmptokens.add(m.group());
            }
        }
        return tmptokens;
    }

    private String parseToken(String token){
        token = token.replaceAll("(\\[|\\])", " ");
        String pattern = "(?<tokenName>"+Tokens.all()+")(?<tokenArgs>(\\s-(u|l|1|i|b|fu|fl|(f=\\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b|fu|fl|(d=\\{.+\\})))*\\}|.+))*|(df=\\{(((m|M).([yY])?)|(([yY].)?(m|M)))\\}))))*)";
        //String pattern = "(?<tokenName>\"+Tokens.all()+\")(?<tokenArgs>(\\s-(u|l|1|i|b|fu|fl|(f=\\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b|fu|fl|(d=\\{.+\\})))*\\}|.+))*|df=\\{(((m|mm|M)?.*[yY]?)|([yY]?.*(m|mm|M)?))\\})))*)";
        //(?<tokenName>"+Tokens.all()+")(?<tokenArgs>(\s-(u|l|1|i|b|fu|fl|(f=\{(jmeno|prijmeni)(((\s-(u|l|1|i|b|fu|fl|(d=\{.+\})))*\}|.+))*|f=\{(((m|mm|M)?.*[yY]?)|([yY]?.*(m|mm|M)?))\})))*)

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(token);
        String tokenName = new String();
        String tokenArgs = new String();
        while (m.find()){
            tokenName = m.group("tokenName");
            tokenArgs = m.group("tokenArgs"); //trim - na začátku je mezera
        }

        if(tokenName.equals(Tokens.title))
            return formatText(article.getName(), tokenArgs);
        if(tokenName.equals(Tokens.author))
            return formatAuthors(article.getAuthors(), tokenArgs);
        if(tokenName.equals(Tokens.conference))
            return formatText(article.getConferenceObj().getName(), tokenArgs);
        if(tokenName.equals(Tokens.isbn))
            return formatText(article.getConferenceObj().getIsbn(), tokenArgs);
        if(tokenName.equals(Tokens.date))
            return formatDate(article.getConferenceObj().getMonth(), article.getConferenceObj().getYear(), tokenArgs);
        if(tokenName.equals(Tokens.month))
            return formatText(String.valueOf(article.getConferenceObj().getMonth()), tokenArgs);
        if(tokenName.equals(Tokens.year))
            return formatText(String.valueOf(article.getConferenceObj().getYear()), tokenArgs);
        if(tokenName.equals(Tokens.address))
            return formatText(article.getConferenceObj().getAddress(), tokenArgs);
        if(tokenName.equals(Tokens.city))
            return formatText(article.getConferenceObj().getCity(), tokenArgs);
        if(tokenName.equals(Tokens.state))
            return formatText(article.getConferenceObj().getState(), tokenArgs);
        return "";
    }

    private String formatText(String text, String args){
        List<String> arguments = new ArrayList<String>();
        Pattern p = Pattern.compile("(?<tokenArg>((u|l|1|i|b|fu|fl|(f=\\{(jmeno|prijmeni)(((\\s-(u|l|1|i|b|fu|fl(d=\\{.+\\})))*|.+))|(df=\\{(((m|M).([yY])?)|(([yY].)?(m|M)))\\}))))*)");
        Matcher m = p.matcher(args);
        while (m.find()) {   //pole formátovacích argumentů
            arguments.add(m.group("tokenArg"));

        }

        for(String arg : arguments){
            if (arg.equals("u"))
                text = text.toUpperCase();
            if (arg.equals("l"))
                text = text.toLowerCase();
            if(arg.equals("fu"))
                text = StringUtils.firstUpper(text);
            if(arg.equals("fl"))
                text = StringUtils.firstLower(text);
            if(arg.equals("i")){

            }

            if(arg.equals("b")){

            }
        }
        return text;
    }

    private String formatDate(int month, int year, String args){
        Pattern p = Pattern.compile("-df=\\{(?<dateFormat>(((m|M)?.*[yY]?)|([yY]?.*(m|M)?)))\\}");
        Matcher m = p.matcher(args);
        String format = new String();
        while(m.find()){
            format = m.group("dateFormat");
        }
        return getDate(format);
    }

    private String getDate(String format){
        String g1 = format.substring(0,1);
        String g2 = format.substring(format.length() - 1, format.length());
        String m = format.substring(1, format.length() - 1); //výpln
        Calendar cal = Calendar.getInstance();
        String month = new String();
        String year = new String();
        String[] months = new String[]{"leden", "únor", "březen", "duben", "květen", "červen", "červenec", "srpen", "září", "říjen", "listopad", "prosinec"};
        if(g1.matches("m|M")){ //formát měsíc - rok
            if(g1.equals("m"))
                month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            else if(g1.equals("M")){
                month = months[cal.get(Calendar.MONTH)];
            }
            if(g2.equals("y"))
                year = String.valueOf(cal.get(Calendar.YEAR)).substring(2);
            else if(g2.equals("Y"))
                year = String.valueOf(cal.get(Calendar.YEAR));
            return month + m + year;
        }else{ //formát rok - měsíc
            if(g2.equals("m"))
                month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            else if(g2.equals("M")){
                month = months[cal.get(Calendar.MONTH)];
            }
            if(g1.equals("y"))
                year = String.valueOf(cal.get(Calendar.YEAR)).substring(2);
            else if(g1.equals("Y"))
                year = String.valueOf(cal.get(Calendar.YEAR));
            return year + m + month;
        }
    }

    private String formatAuthors(List<Author> authors, String args){
        args = args.replace("-f=", "").trim();
        // (?<tokenArgs>(\s-(u|l|1|i|b|fu|fl|(f=\{(jmeno|prijmeni)(((\s-(u|l|1|i|b|(d=\{.+\})))+|.+))|(df=\{(((m{0,2}|M).([yY])?)|(([yY].)?(m{0,2}|M)))\}))))*)

        //Pattern p = Pattern.compile("(\\{(?<tokenName>(prijmeni|jmeno))(?<tokenArgs>(\\s-(u|l|1|i|b|d=\\{.+\\}))*)\\})*");
        Pattern p = Pattern.compile("(?<token1>\\{(jmeno|prijmeni)(\\s-(u|l|1|i|b|fu|fl|d=\\{.+\\}))*\\}).{0,3}(?<token2>\\{(jmeno|prijmeni)(\\s-(u|l|1|i|b|fu|fl|d=\\{.+\\}))*\\})");
        //Pattern p = Pattern.compile("(f=\\{(?<user1>(jmeno)(((\\s-(u|l|1|i|b|(d=\\{.+\\})))+|.+))))"); //todo chyba v reguláru
        Matcher m = p.matcher(args);
        String name1 = new String();
        String name2 = new String();
        while (m.find()) {
            name1 = m.group("token1");
            name2 = m.group("token2");
        }

        StringBuilder sb = new StringBuilder();
        String tmp = new String();
        String originalArgs = args;
        boolean isEnd = false;
        int i = 1;
        for(Author author : authors){
            if(i == authors.size())
                isEnd = true;
            args = args.replace(name1, parseName(author, name1, isEnd));
            args = args.replace(name2, parseName(author, name2, isEnd));
            sb.append(args);
            args = originalArgs;
            i++;
        }
        return sb.toString();
    }

    private String parseName(Author author, String pattern, boolean last){
        Pattern p = Pattern.compile("\\{(?<tokenName>(jmeno|prijmeni))(?<tokenArgs>(\\s-(u|l|1|i|b|fu|fl|d=\\{.+\\}))*)\\}");
        Matcher m = p.matcher(pattern);
        String tokenName = new String();
        String tokenArgs = new String();
        while (m.find()) {
            tokenName = m.group("tokenName");
            tokenArgs = m.group("tokenArgs");
        }

        if(tokenName.equals("jmeno"))
            return formatName(author.getName(), tokenArgs, last);
        else if(tokenName.equals("prijmeni"))
            return formatName(author.getLastName(), tokenArgs, last);
        return "CHYBNY FORMAT";
    }

    private String formatName(String name, String args, boolean last){
        List<String> arguments = new ArrayList<String>();
        Pattern p = Pattern.compile("\\s-(?<tokenArg>((u|l|1|i|b|fu|fl|(d=\\{.+\\}))*))");
        Matcher m = p.matcher(args);
        while (m.find()) {   //pole formátovacích argumentů
            arguments.add(m.group("tokenArg"));
        }

        for (String argument : arguments){
            if(argument.equals("u"))
                name = name.toUpperCase();
            if(argument.equals("l"))
                name = name.toLowerCase();
            if(argument.equals("1"))
                name = name.substring(0,1) + ".";
            if(argument.equals("i"))
                name = name; //todo kurzíva
            if(argument.equals("b"))
                name = name; //todo tučné
            if(argument.equals("fu"))
                name = StringUtils.firstUpper(name);
            if(argument.equals("fl"))
                name = StringUtils.firstLower(name);
            if(argument.contains("d")){
                name = name + (!last ? argument.substring(argument.indexOf("{") + 1, argument.indexOf("}")) : "");
            }
        }
        return name;
    }
}

/**
 * Seznam všech klíčových slov pro export
 */
class Tokens{
    public static final String author = "autor";
    public static final String title = "nadpis";
    public static final String conference = "konference";
    public static final String isbn = "isbn";
    public static final String date = "datum";
    public static final String month = "mesic";
    public static final String year = "rok";
    public static final String address = "adresa";
    public static final String city = "mesto";
    public static final String state = "stat";

    public static final String all(){
        return "("+author+"|"+title+"|"+conference+"|"+isbn+"|"+date+"|"+month+"|"+year+"|"+address+"|"+city+"|"+state+")";
    }
}

class StringUtils{

    public static String firstUpper(String data){
        data = data.toLowerCase();
        return Character.toString(data.charAt(0)).toUpperCase()+data.substring(1);
    }

    public static String firstLower(String data){
        data = data.toUpperCase();
        return Character.toString(data.charAt(0)).toLowerCase()+data.substring(1);
    }
}