package com.bpositive.technician.core.mockData;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConvertRawJsonToGSON {

    private Context context;

    public ConvertRawJsonToGSON(Context context) {
        this.context = context;
    }

    public String getTransitJsonDataFromLocalFile(int rawJson) {

//        InputStream XmlFileInputStream = context.getResources().openRawResource(R.raw.mock_transit_data);

        InputStream XmlFileInputStream = context.getResources().openRawResource(rawJson);

        //2 This reads your JSON file
        String jsonString = readTextFile(XmlFileInputStream);

        Log.d("TAG", "getJsonDataFromLocalFile: "+jsonString);

        return jsonString;
    }

    //3 Converts the input stream into a String
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

}
