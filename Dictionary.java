package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    //class members:

    private CacheManager wordsNotExist;
    private CacheManager wordsExist;
    private BloomFilter bl;
    private String[] FilesNames;



    public Dictionary(String...FilesNames) {
        wordsExist = new CacheManager(400 , new LRU());
        wordsNotExist = new CacheManager(100 , new LFU());
        bl = new BloomFilter(256 , "MD5" , "SHA1");

        this.FilesNames = new String[FilesNames.length];
        System.arraycopy(FilesNames, 0, this.FilesNames, 0, FilesNames.length);

        for (String filesName : FilesNames) {
            try {
                File f = new File(filesName);
                Scanner scan = new Scanner(f);

                while (scan.hasNext()) {
                    bl.add(scan.next());
                }
                scan.close();
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFound");
            }
        }
    }

    public boolean query(String word){
        if(wordsExist.query(word)){
            return true;
        }

        else {
            if (wordsNotExist.query(word)) {
                return false;
            }
            else{
                if(bl.contains(word)){
                    //Update cacheManager
                    wordsExist.add(word);
                    return true;
                }
                else{
                    //Update cacheManager
                    wordsNotExist.add(word);
                }
            }
        }
        return false;
    }


    public boolean challenge (String word) {
        if(IOSearcher.search(word , FilesNames)){
            //Update cacheManager
            wordsExist.add(word);
            return true;
        }
        else {
            //Update cacheManager
            wordsNotExist.add(word);
            return false;
        }
    }

    //End of class
}
