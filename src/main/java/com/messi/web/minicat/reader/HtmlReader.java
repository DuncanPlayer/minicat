package com.messi.web.minicat.reader;

import com.messi.web.minicat.inherit.ReadSources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HtmlReader implements ReadSources {

    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public StringBuilder read(String resourePath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(resourePath))));
            String line = "";
            while((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            return stringBuilder;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
