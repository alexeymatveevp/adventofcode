package com.luckystar;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Alexey Matveev on 12/9/2018.
 */
public class InputLoader {

    public static String readInput(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(InputLoader.class.getClassLoader().getResource(path).toURI())));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
