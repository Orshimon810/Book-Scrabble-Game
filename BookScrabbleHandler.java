package test;

import java.io.*;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;


public class BookScrabbleHandler implements ClientHandler{
    Scanner in;
    PrintWriter out;
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        in = new Scanner(new InputStreamReader(inFromclient));
        out = new PrintWriter(outToClient,true);

            String[] args = in.next().split(",");
            String ask = args[0];

            String[] bookNames = new String[args.length - 1];

            System.arraycopy(args, 1, bookNames, 0, bookNames.length);

            if (ask.equals("Q")) {
                DictionaryManager dm = new DictionaryManager();
                if (dm.query(bookNames))
                    out.println("true");
                else
                    out.println("false");
            } else {
                DictionaryManager dm = new DictionaryManager();
                if (dm.query(bookNames))
                    out.println("true");
                else
                    out.println("false");
            }
    }

    @Override
    public void close() {
     in.close();
     out.close();
    }
}
