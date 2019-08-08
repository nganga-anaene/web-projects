package com.anaene.airlineserver.config.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class NameReader {

    private Set<String> maleNames = new HashSet<>();
    private Set<String> femaleNames = new HashSet<>();
    private Set<String> surnames = new HashSet<>();
    private final String namesDirectory = "popular-baby-names-master";

    public NameReader() {
        readFiles();
    }

    private void readFiles() {
        try {
            JsonParser parser = new JsonParser();
            readFirstNames(parser);
            readSurnames(parser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readSurnames(JsonParser parser) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(namesDirectory + "/surnames.json").getFile());
        JsonArray surnameList = parser.parse(new FileReader(file)).getAsJsonObject().getAsJsonArray("surnames");
        surnameList.forEach(jsonElement -> surnames.add(jsonElement.getAsString()));
    }

    private void readFirstNames(JsonParser parser) throws IOException {
        for (int i = 0; i < 5; i++) {
            File boysNamesFile = new File(getClass().getClassLoader().getResource(namesDirectory + "/boy_names_" + (2012 + i) + ".json").getFile());
            File girlsNamesFile = new File(getClass().getClassLoader().getResource(namesDirectory + "/girl_names_" + (2012 + i) + ".json").getFile());
            JsonArray maleNamesArray = parser.parse(new FileReader(boysNamesFile)).getAsJsonObject().getAsJsonArray("names");
            JsonArray femaleNamesArray = parser.parse(new FileReader(girlsNamesFile)).getAsJsonObject().getAsJsonArray("names");
            maleNamesArray.forEach(jsonElement -> maleNames.add(jsonElement.getAsString()));
            femaleNamesArray.forEach(jsonElement -> femaleNames.add(jsonElement.getAsString()));
        }
    }

    public Set<String> getFemaleNames() {
        return femaleNames;
    }

    public Set<String> getMaleNames() {
        return maleNames;
    }

    public Set<String> getSurnames() {
        return surnames;
    }
}
