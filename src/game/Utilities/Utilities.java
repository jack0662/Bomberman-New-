package game.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;

/**
 * Created by Jack on 17/11/2016.
 */
public class Utilities {

    public static String loadFileAsString(String path){

        String s1 ="";
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            s1 = sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return s1;

    }

    public static String[] stringToArray(String s1){
        return s1.split("\\s+");
    }

    //Test code
    public static void main(String[] args) {

        loadFileAsString("resources/worlds/default.txt");
    }

}
