package conference.model;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pokus {

    public static void main(String[] args){
        String data = "Conkie Alistair,Ann k.";
        String[] x = data.split("(\\w+),(\\w+)?");
        for(String s : x)
            System.out.println(s);
    }
}
