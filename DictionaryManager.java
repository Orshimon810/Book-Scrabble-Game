package test;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    public static DictionaryManager dictionary_Instance; //The only DictionaryManager member
    private int size;
    public Map <String , test.Dictionary> dictionaryMap = null; //Class Member

    public DictionaryManager() {
        this.dictionaryMap = new HashMap<>();
        size = 0;
    }

    public static DictionaryManager get(){
        if(dictionary_Instance == null){
            dictionary_Instance = new DictionaryManager();
        }
        return dictionary_Instance;
    }

    public int getSize(){
        return size;
    }

    public void checkBook(String...args){
        for(int i = 0;  i < args.length-1; i++) {
            String curr_arg = args[i];

            if (!dictionaryMap.containsKey(curr_arg)) { //if the map is not contains the book
                dictionaryMap.put(curr_arg, new test.Dictionary(curr_arg));
                size++;
            }
        }
    }

    public boolean query(String...args){
        boolean tmp = false;
        boolean res = false;
        checkBook(args);

            for (Map.Entry<String, test.Dictionary> set : dictionaryMap.entrySet()) {
                tmp = set.getValue().query(args[args.length-1]);
                if(tmp)
                    res = true;
            }
            return res;
    }

    public boolean challenge(String...args){
        boolean res = false;
        boolean tmp = false;
        checkBook(args);

        for (Map.Entry<String, test.Dictionary> set : dictionaryMap.entrySet()) {
            tmp = set.getValue().challenge(args[args.length-1]);
            if(tmp)
                res = true;
        }
        return res;
    }










}
