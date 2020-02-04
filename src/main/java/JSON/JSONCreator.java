package JSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import metroElements.*;


import java.io.FileWriter;
import java.io.IOException;


public class JSONCreator {
    private String jsonFileName;
    private String jsonStr;


    public JSONCreator(String jsonFileFullUrl) {
        this.jsonFileName = jsonFileFullUrl;
    }

    protected String createJSONString(Metro metro) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        jsonStr = gson.toJson(metro);
        return jsonStr;
    }

    public void createJSONFile(Metro metro) {
        try (FileWriter fileWriter = new FileWriter(jsonFileName)) {
            fileWriter.write(createJSONString(metro));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
