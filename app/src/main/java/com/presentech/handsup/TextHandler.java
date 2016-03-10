package com.presentech.handsup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Noor on 06/03/2016.
 */

public class TextHandler {

    String retrievedText;
    StringBuilder textFromFile = new StringBuilder();


    public String retrieveText (InputStream inputStream, String text) {
        //Read and display text from a source file
        if (inputStream != null){

            try {
                //FileReader fr = new FileReader(sourceFile);
               // InputStream in = this.getClass().getClassLoader().getResourceAsStream("source.txt");
                InputStreamReader inputReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputReader);
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    textFromFile.append(aLine).append("\n");
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                retrievedText = "<i>Source file couldn't be found.</i>";
            } catch (IOException e) {
                e.printStackTrace();
                retrievedText = "<i>Source file couldn't be opened.</i>";
            }
            retrievedText = textFromFile.toString();
        } else if (text != null){
            // If no input file then get text from method parameter
            retrievedText = text;
        } else {
            retrievedText = "";
        }


        return retrievedText;
    }
}
