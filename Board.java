package test;
import java.util.ArrayList;


public class Board {
    public static final int BOARD_SIZE = 15;
    public static final int STAR = 7;
    private static Board instance = null;
    public static Tile[][] scrabbleBoard = new Tile[BOARD_SIZE][BOARD_SIZE];
    public static final String[][] bonusMat = {{"3W", " ", " ", "2L", " ", " ", " ", "3W", " ", " ", " ", "2L", " ", " ", "3W"},
            {" ", "2W ", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "2W", " "},
            {" ", " ", "2W", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2W", " ", " "},
            {"2L", " ", " ", "2W", " ", " ", " ", "2L", " ", " ", " ", "2W", " ", " ", "2L"},
            {" ", " ", " ", "2L", "2W", " ", " ", " ", " ", " ", "2W", " ", " ", " ", "3W"},
            {" ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " "},
            {" ", " ", "2L", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2L", " ", " "},
            {"3W", " ", " ", "2L", " ", " ", " ", "*", " ", " ", " ", "2L", " ", " ", "3W"},
            {" ", " ", "2L", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2L", " ", " "},
            {" ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " "},
            {" ", " ", " ", " ", "2W", " ", " ", " ", " ", " ", "2W", " ", " ", " ", " "},
            {"2L", " ", " ", "2W", " ", " ", " ", "2L", " ", " ", " ", "2W", " ", " ", "2L"},
            {" ", " ", "2W", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2W", " ", " "},
            {" ", "2W", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "2W", " "},
            {"3W", " ", " ", "2L", " ", " ", " ", "3W", " ", " ", " ", "2L", " ", " ", "3W"},
    };
    public static boolean is_started = false;
    public static int word_count = 0;
    public static ArrayList<Word> word_in_board = new ArrayList<>();


    public Board(Tile[][] scrabbleBoard, boolean is_started, int word_count, ArrayList<Word> word_in_board) {
        Board.scrabbleBoard = scrabbleBoard;
        Board.is_started = is_started;
        Board.word_count = word_count;
        Board.word_in_board = word_in_board;
    }


    public Tile[][] getTiles() {
        Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        Tile[][] tiles_copy = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                tiles[i][j] = Board.scrabbleBoard[i][j];
            }
        }
        System.arraycopy(tiles, 0, tiles_copy, 0, tiles.length);
        return tiles_copy;
    }


    public static Board getBoard() {
        if (instance == null) {
            instance = new Board(scrabbleBoard, is_started, word_count, word_in_board);
        }
        return instance;
    }

    public boolean boardLegal(Word w) {
        int count = 0, i, flag;
        int col = w.getCol();
        int row = w.getRow();
        Tile tmpTile;
        boolean tmp;
        int wordSize = w.getTiles().length;

        // if the word index is in limits
        if (col < 0 || row < 0 || col > BOARD_SIZE || row > BOARD_SIZE) {
            return false;
        }
        // if the word size is in limit under 15 and above 0
        if ((!w.isVertical() && wordSize + col > BOARD_SIZE) || (w.isVertical() && wordSize + row > BOARD_SIZE)) {
            return false;
        }

        // checking first word on star
        tmp = checkStars(w);
        if (!tmp) {
            return false;
        }
        // only when board is not empty
        if (Board.scrabbleBoard[STAR][STAR] != null) {
            // checking if word is close to other tiles
            flag = checkWordPlace(w);
            if (flag != 1) {
                return false;
            } else {
                // check if tile is not running over existing tile
                flag = checkBoardTiles(w);
                if (flag != 1) {
                    return false;
                }
            }
        }
        return true;
    }
    private int checkWordPlace(Word w) {
        int i, wordSize = w.getTiles().length, flag = 0;
        int row = w.getRow();
        int col = w.getCol();
        if(Board.scrabbleBoard[STAR][STAR] == null){
            return flag = 1;
        }
        for (i = 0; i < wordSize; i++) {
            if (w.isVertical()) {
                // check up
                if (i == 0 && row - 1 > 0) {
                    if (Board.scrabbleBoard[row - 1][col] != null) {
                        return flag = 1;
                    }
                }
                // check left
                if (col - 1 > 0) {
                    if (Board.scrabbleBoard[row + i][col - 1] != null) {
                        return flag = 1;
                    }
                }
                // check right
                if (col + 1 < BOARD_SIZE) {
                    if (Board.scrabbleBoard[row + i][col + 1] != null) {
                        return flag = 1;
                    }
                }
                // check down
                if (i == wordSize - 1 && row + 1 < BOARD_SIZE) {
                    if (Board.scrabbleBoard[row + 1][col + i + 1] != null) {
                        return flag = 1;
                    }
                }
            }

            if (!w.isVertical()) {
                //check left
                if (i == row && col - 1 > 0) {
                    if (Board.scrabbleBoard[row][col - 1] != null) {
                        return flag = 1;
                    }
                }
                //check up
                if (row - 1 > 0) {
                    if (Board.scrabbleBoard[row - 1][col + i] != null) {
                        return flag = 1;
                    }
                }
                //check down
                if (row + 1 < BOARD_SIZE) {
                    if (Board.scrabbleBoard[row + 1][col + i] != null) {
                        return flag = 1;
                    }
                }
                //check right
                if (i == wordSize - 1 && col + 1 < BOARD_SIZE) {
                    if (Board.scrabbleBoard[row + i + 1][col] != null) {
                        return flag = 1;
                    }
                }

            }

        }
        return flag;
    }

    private int checkBoardTiles(Word w) {
        int flag = 0,i,wordSize = w.getTiles().length;
        int count = 0;
        int row = w.getRow();
        int col = w.getCol();

        for(i =0; i < wordSize; i++){
            if(w.getTiles()[i] != null){
                count++;
            }
            if(w.isVertical()){
                // checking words like "FA_M", the R is already on board
                if(Board.scrabbleBoard[row+i][col] != null && w.getTiles()[i] == null){
                    return flag = 1;
                }
            }
            if(!w.isVertical()){
                if(Board.scrabbleBoard[row][col+i] != null && w.getTiles()[i] == null){
                    return flag =1;
                }
            }
        }

        // only when a word is full, with no null Tiles
        if(count == wordSize){
            flag = 1;
        }
        return flag;
    }
    private boolean checkStars(Word w){
        int row = w.getRow();
        int col = w.getCol();
        int length = w.getTiles().length;

        if(Board.scrabbleBoard[STAR][STAR] == null){
            if(w.isVertical()){
                if(row + length >= STAR && col == STAR){
                    return true;
                }
            }
        if(!w.isVertical()){
            if(col + length >= STAR && row == STAR){
                return true;
            }
        }
    }
        else{
        return true;
            }
        return false;
    }

    private boolean dictionaryLegal(Word w) {
        return true;
    }


    //Function which calculates the Word score
    private int getScore(Word w) {
        int length = w.getTiles().length;
        int row = w.getRow();
        int col = w.getCol();
        int score = 0;
        int word_bonus;
        int let_bonus;
        int tripelWord = 0;
        int doubleWord = 0;


        if (!is_started) {  //if that's the first word
            doubleWord++;
        }

        for (int i = 0; i < length; i++) {
            let_bonus = getletBonus(row, col);   //if there is a bonus for a letter
            score += Board.scrabbleBoard[row][col].score * let_bonus;
            word_bonus = getWordBonus(row, col);
            if (word_bonus == 2) {
                doubleWord++;
            } else if (word_bonus == 3) {
                tripelWord++;
            }
            if (w.isVertical()) {
                row++;
            } else {
                col++;
            }
        }

        if (tripelWord > 0) {
            score = score * (int) Math.pow((double) 3, (double) tripelWord);
        }

        if (doubleWord > 0) {
            score = score * (int) Math.pow((double) 2, (double) doubleWord);
        }
        return score;
    }

    private int getletBonus(int row, int col) {
        int let_bonus = 0;
        switch (bonusMat[row][col]) {
            case "3L":
                let_bonus = 3;
                break;
            case "2L":
                let_bonus = 2;
                break;
            default:
                let_bonus = 1;
        }
        return let_bonus;
    }

    private int getWordBonus(int row, int col) {
        int word_bonus = 0;
        switch (bonusMat[row][col]) {
            case "3W":
                word_bonus = 3;
                break;
            case "2W":
                word_bonus = 2;
                break;
            default:
                word_bonus = 1;
        }
        return word_bonus;
    }

    public int tryPlaceWord(Word w) {
        int row = w.getRow() , col = w.getCol();
        int length = w.getTiles().length;
        int score = 0;
        ArrayList<Word> tmp = getWords(w);


            for (int i = 0; i < tmp.size(); i++) {
                if (!boardLegal(tmp.get(i)) && dictionaryLegal(tmp.get(i))) {  //Check if the secondary words are also legal
                    return score;
                }
            }
                    word_count++;
                    for (int j = 0; j < length; j++) {
                        if (w.isVertical()) {
                            if (Board.scrabbleBoard[row][col] == null) {
                                Board.scrabbleBoard[row][col] = w.getTiles()[j];
                                row++;
                            }
                            else{
                                row++;
                            }
                        } else if (!w.isVertical()) {
                            if (Board.scrabbleBoard[row][col] == null) {
                                Board.scrabbleBoard[row][col] = w.getTiles()[j];
                                col++;
                            }
                            else{
                                col++;
                            }
                        }
                    }
                for (int j = 0; j < tmp.size(); j++) {
                    score += getScore(tmp.get(j));
                    is_started = true;
                  }
            return score;
        }


    private ArrayList<Word> getWords(Word w){
        int newWord_length;
        int row = w.getRow() , col = w.getCol() , i = 1 , j = 1;
        int leftCol = w.getCol() , rightCol = w.getCol();
        int upRow = w.getRow() , downRow = w.getRow();
        int word_length = w.getTiles().length;
        Tile [] newWordTiles;
        Word newWord;
        ArrayList<Word> sec_words = new ArrayList<>();
        sec_words.add(w);


        for(int k = 0; k < word_length; k++){
            if(w.isVertical()){
                if(Board.scrabbleBoard[row][col] == null) {
                    while (leftCol - i >= 0 && Board.scrabbleBoard[row][leftCol - i] != null) {
                        i++;
                    }

                    while (rightCol + j < BOARD_SIZE && Board.scrabbleBoard[row][rightCol + j] != null) {
                        j++;
                    }
                }
                rightCol = rightCol + j - 1;
                leftCol = leftCol - i + 1;


                if(i != 1 || j != 1) {
                    newWord_length = rightCol - leftCol + 1;
                    newWordTiles = new Tile[newWord_length];

                    for (int z = 0; z < newWord_length; z++) {
                        if (Board.scrabbleBoard[row][leftCol + z] != null) {
                            newWordTiles[z] = Board.scrabbleBoard[row][leftCol + z];
                        } else {
                            newWordTiles[z] = w.getTiles()[k];
                        }
                    }

                    newWord = new Word(newWordTiles, row, leftCol, false);
                    if (dictionaryLegal(newWord)) {
                        sec_words.add(newWord);
                    }
                }
                        row++;
                        j = 1;
                        i = 1;
                        leftCol = col;
                        rightCol = col;
                }


            else { //if im not vertical
                if(Board.scrabbleBoard[row][col] == null) {
                    while (upRow - i >= 0 && Board.scrabbleBoard[upRow - i][col] != null) {
                        i++;
                    }
                    while (downRow + j < BOARD_SIZE && Board.scrabbleBoard[downRow + j][col] != null) {
                        j++;
                    }
                }

                upRow = upRow - i + 1;
                downRow = downRow + j -1;

                if (i != 1 || j != 1) {
                    newWord_length = downRow - upRow + 1;
                    newWordTiles = new Tile[newWord_length];

                    for (int z = 0; z < newWord_length; z++) {
                        if (Board.scrabbleBoard[upRow + z][col] != null) {
                            newWordTiles[z] = Board.scrabbleBoard[upRow + z][col];
                        } else {
                            newWordTiles[z] = w.getTiles()[k];
                        }
                    }

                    newWord = new Word(newWordTiles, upRow, col, true);
                    if (dictionaryLegal(newWord)) {
                        sec_words.add(newWord);
                    }
                }
                i = 1;
                j = 1;
                col++;
                upRow = row;
                downRow = row;
               }
            }
          return sec_words;
        }
    }
