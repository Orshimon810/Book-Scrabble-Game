package test;

import java.util.Objects;
import java.util.Random;

public class Tile {
   public final char letter;
   public final int score;


    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "letter=" + letter +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }


    public static class Bag {
        public static final int SIZE = 26;
        public static final int scoreArr[] = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        public static final int amountArr[] = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        private static Bag bag = null;
        private int[] num_of_let;
        private Tile[] Tiles;


        private Bag(int[] num_of_let, Tile[] tiles) {
            this.num_of_let = num_of_let;
            this.Tiles = tiles;
        }


        //function which random a tile
        public Tile getRand() {
            Random random = new Random();
            int i = random.nextInt(26);

            if (num_of_let[i] > 0) {
                num_of_let[i]--;
                return Tiles[i];
            }
               else {
                int index = (i + 1) % 26;
                while (index != i) {
                    if (num_of_let[index] > 0) {
                        num_of_let[index]--;
                        return Tiles[index];
                    }
                    else
                        index = (i + 1) % 26;
                }
            }
            return null;
        }

        public Tile getTile(char let) {
            int index = getIndex(let);
            if(index < 0 || index > 26)
                return null;

            if (num_of_let[index] > 0) {
                num_of_let[index]--;
                return Tiles[index];
            } else
                return null;
        }

        public static int getIndex(char letter) {
            return letter - 'A';
        }

        public static char getLet(int num) {
            return (char) (num + 'A');
        }

        public void put(Tile t) {
            int index = getIndex(t.letter);

            if( getBag().num_of_let[index] < getBag().amountArr[index] )
                getBag().num_of_let[index]++;
        }

        public int size() {
            int size = 0;
            for (int i = 0; i < num_of_let.length; i++)
                size += num_of_let[i];

            return size;
        }

        public int[] getQuantities() {
            int[] copy = new int[num_of_let.length];
            System.arraycopy(num_of_let, 0, copy, 0, num_of_let.length);

            return copy;
        }
        public static Bag getBag() {
            if (bag == null) {
                Tile[] tiles = new Tile[SIZE];
                int[] num_of_let = new int[SIZE];

                for (int i = 0; i < SIZE; i++) {
                    num_of_let[i] = amountArr[i];
                    tiles[i] = new Tile(getLet(i), scoreArr[i]);
                }
                bag = new Bag(num_of_let, tiles);
            }
            return bag;
        }
    }
}
