import metroElements.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static final String SITE_URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";


    public static void main(String[] args) throws IOException {
        JSONObject outputJson = new JSONObject();

        JSONObject stationsObj = new JSONObject();
        JSONArray stationList = new JSONArray();

        JSONObject connectionsObj = new JSONObject();
        JSONObject linesObj = new JSONObject();
        JSONObject lineUnic = new JSONObject();

        //парсинг:
        HtmlParsing htmlParsing = new HtmlParsing(SITE_URL);
        ArrayList<Line> lines = htmlParsing.getLines();
        ArrayList<Station> stations = htmlParsing.getStations();
        ArrayList <Connection> connections = htmlParsing.getConnections();

//OUTPUT:
        System.out.println("---STATIONS---");
        for (int i = 0; i < stations.size(); i++) {
            System.out.println(stations.get(i));
        }
        System.out.println("---LINES---" + lines.size());
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(lines.get(i));
        }
        System.out.println("---CONNECTIONS---");
        for (int i = 0; i < connections.size(); i++) {
            System.out.println(connections.get(i));
        }
    }
}
