package test;
import java.util.HashSet;
import java.util.Set;

public class CacheManager {
    //class Members:
    private Set <String> cache = new HashSet<>();
    private CacheReplacementPolicy crp;
    private int max_size;

    public CacheManager(int size , CacheReplacementPolicy crp){  //Ctor
        this.max_size = size;
        this.crp = crp;
    }

    public boolean query (String word){
        return cache.contains(word);
    }


    public void add (String word){
        if(cache.size() >= max_size){
            String to_be_deleted = crp.remove();
            cache.remove(to_be_deleted);
            cache.add(word);
        }

        else{
            cache.add(word);
            crp.add(word);
        }
    }
    //End of class
}
