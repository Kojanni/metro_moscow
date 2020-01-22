import com.google.gson.Gson;
import metroElements.Line;

import java.util.ArrayList;


public class JSONCreator {

    public JSONCreator() {
        super();
    }

    public void createJSON(ArrayList<Line> lines) {
        Gson gson = new Gson();
        lines.forEach(l -> {
            String json = gson.toJson(l);
                });

    }

}
