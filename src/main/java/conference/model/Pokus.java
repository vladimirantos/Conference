package conference.model;

import java.util.Random;

/**
 * Created by Vladimír on 12. 4. 2015.
 */
public class Pokus {

    public static void main(String[] args){
        for(int i = 0; i < 100; i++)
        {
            Random r = new Random();
            int id = r.nextInt(1000);
            System.out.println(id);
        }
    }
}
