package test;
import java.io.*;
import java.util.Scanner;

public class IOSearcher {

    public static boolean search(String word,String...FileNames) {
        for (String FileName : FileNames) {
            File f = new File(FileName);

            try {
                Scanner scan1 = new Scanner(f);
                String tmp;

                while (scan1.hasNext()) {
                    tmp = scan1.next();
                    if (tmp.equals(word)) {
                        return true;
                    }
                }
                scan1.close();
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFound");
            }
        }
        return false;
    }
    //end of class
}
