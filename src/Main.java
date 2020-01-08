import metroElements.Line;
import metroElements.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static final String CITE_URL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";


    public static void main(String[] args) throws IOException {
        JSONObject outputJson = new JSONObject();

        JSONObject stationsObj = new JSONObject();
        JSONArray stationList = new JSONArray();

        JSONObject connectionsObj = new JSONObject();
        JSONObject linesObj = new JSONObject();
        JSONObject lineUnic = new JSONObject();

        //парсинг:
        ArrayList<Station> stations = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();

        Document doc = Jsoup.connect(CITE_URL).get();
        Elements element = doc.select("td");
        String [] stationCreateInfo = new String[3];
        element.forEach(e -> {

            String lineName = e.select("span").attr("title");
            if ((lineName.split(" ").length <= 3) && (lineName.split(" ").length > 1)
                    && (lineName.contains("линия") || (lineName.contains("кольцо")) || (lineName.contains("монорельс")))) {

                String lineColor = e.attr("style");


                //System.out.println("line name: " + lineName + "\nLine color: " + lineColor);

            }
            for (int i = 0; i <= 2; i++) {
                System.out.print(" --- " + stationCreateInfo[i]);
            }
            System.out.println();
            String lineStationNumbers = e.select("span").text();
            if (lineStationNumbers.trim().split(" ").length == 3) {
            String lineINFO[] = e.select("span").text().trim().split(" ");
            if (lineINFO[0].matches("\\d+.") && lineINFO[2].matches("\\d+")) {
                String lineNumber = lineINFO[0];
                String stationNumber = lineINFO[2];
                System.out.println("LINE NUMBER: " + lineNumber);
                System.out.println("STATION NUMBER: " + stationNumber);
                stationCreateInfo[0] = lineNumber;
                stationCreateInfo[1] = stationNumber;
            }
                System.out.println("get numbers:");
                for (int i = 0; i <= 2; i++) {
                    System.out.print(" --- " + stationCreateInfo[i]);
                }
                System.out.println();

            }
            if (e.select("a").attr("title").contains("станция метро")) {
                String stationName = e.select("a").attr("title");
                System.out.println("STATION NAME: " + e.select("a").attr("title"));
                stationCreateInfo[2] = stationName;
            }
            if (stationCreateInfo[2] != null) {
                stations.add(new Station(stationCreateInfo[0], stationCreateInfo[1], stationCreateInfo[2]));
                for (int i = 0; i <= 2; i++) {
                    System.out.println(stationCreateInfo[i]);
                    stationCreateInfo[i] = null;
                }
            }


            //System.out.println(lineStationNumbers.trim().split(" ").length + " Output: " + lineStationNumbers);


        });
        for (int i = 0; i < stations.size(); i++) {
            System.out.println(stations.get(i));
        }

    }


}
