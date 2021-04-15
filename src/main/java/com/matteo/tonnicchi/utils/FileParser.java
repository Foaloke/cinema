package com.matteo.tonnicchi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public final class FileParser {
    
    public static void parse(InputStream inputStream, Consumer<String> lineParser){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        try {
            while(reader.ready()) {
                lineParser.accept(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
