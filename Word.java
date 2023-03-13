package test;

import java.util.Arrays;
import java.util.Objects;

public class Word {

    private Tile[] word;
    private int row;
    private int col;
    private boolean vertical;

    //Word Ctor
    public Word(Tile[] word, int row, int col, boolean vertical) {
        this.word = word;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    //Getters , setters , hashcode and equals methods
    public Tile[] getTiles() {
        return word;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return row == word1.row && col == word1.col && vertical == word1.vertical && Arrays.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(row, col, vertical);
        result = 31 * result + Arrays.hashCode(word);
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "tiles=" + Arrays.toString(word) +
                ", row=" + row +
                ", col=" + col +
                ", vertical=" + vertical +
                '}';
    }
}
