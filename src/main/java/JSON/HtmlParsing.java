package JSON;
import metroElements.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HtmlParsing {
    private String siteUrl;
    private ArrayList<Station> stations;
    private ArrayList<Line> lines;
    private ArrayList<Connection> connections;

    public HtmlParsing(String siteUrl) throws IOException {
        this.siteUrl = siteUrl;
        this.stations = readMetroStation(siteUrl);
        this.lines = readMetroLine(siteUrl);
        this.connections = readMetroConnection(siteUrl);
    }

    public ArrayList<Station> getStations() {
        return this.stations;
    }

    public ArrayList<Line> getLines() {
        return this.lines;
    }

    public ArrayList<Connection> getConnections() {
        return this.connections;
    }

    public ArrayList<Station> readMetroStation(String siteUrl) throws IOException {
        ArrayList<Station> readStation = new ArrayList<>();
        Document doc = Jsoup.connect(siteUrl).maxBodySize(0).get();
        Elements element = doc.select("td");
        String[] stationCreateInfo = new String[3];
        int lineNumberIndex = 0;
        int stationNumberIndex = 1;
        int stationNameIndex = 2;
        int lineInfo_line = 0;
        int lineInfo_numberStation = 2;

        element.forEach(e ->
        {
            String lineStationNumbers = e.select("span").text();
            if (lineStationNumbers.trim().split(" ").length >= 3) {
                String lineINFO[] = e.select("span").text().trim().split(" ");
                if (lineINFO[lineInfo_line].matches("\\d+.") && lineINFO[lineInfo_numberStation].matches("\\d+")) {
                    String lineNumber = lineINFO[e.select("span").text().trim().split(" ").length - 3];
                    String stationNumber = lineINFO[e.select("span").text().trim().split(" ").length - 1];
                    stationCreateInfo[lineNumberIndex] = lineNumber;
                    stationCreateInfo[stationNumberIndex] = stationNumber;
                }
            }
            if ((e.select("a").attr("title").contains(" (станция"))
                    || (e.select("a").attr("title").matches("Фонвизинская"))
                    || (e.select("a").attr("title").matches("Полежаевская"))) {
                String stationName = e.select("a").attr("title");
                stationCreateInfo[stationNameIndex] = stationName;
            }
            if (stationCreateInfo[stationNameIndex] != null && stationCreateInfo[lineNumberIndex] != null) {
                if (stationCreateInfo[stationNameIndex].contains("(")) {
                    int indexStartComment = stationCreateInfo[stationNameIndex].indexOf("(");
                    stationCreateInfo[stationNameIndex] = stationCreateInfo[stationNameIndex].substring(0, (indexStartComment - 1));
                }
                readStation.add(new Station(stationCreateInfo[lineNumberIndex], stationCreateInfo[stationNumberIndex], stationCreateInfo[stationNameIndex]));
                for (int i = 0; i <= 2; i++) {
                    stationCreateInfo[i] = null;
                }
            }
        });
        return readStation;
    }

    public ArrayList<Line> readMetroLine(String siteUrl) throws IOException {
        ArrayList<Line> readLines = new ArrayList<>();
        Document doc = Jsoup.connect(siteUrl).maxBodySize(0).get();
        Elements element = doc.select("td");
        element.forEach(e -> {
            String lineName = e.select("span").attr("title");
            if ((lineName.split(" ").length <= 3) && (lineName.split(" ").length > 1)
                    && ((lineName.contains("линия")) || (lineName.contains("кольцо")) || (lineName.contains("монорельс")))) {
                String lineColor = e.attr("style");
                if (e.select("span").text().trim().split(" ").length == 3) {
                    String lineNumb = e.select("span").get(0).text();
                    boolean notNewLine = false;
                    if (readLines.size() == 0) {
                        readLines.add(new Line(lineNumb, lineName, lineColor));
                    } else {
                        for (int i = 0; i < readLines.size(); i++) {
                            notNewLine = readLines.get(i).getNumber().contains(lineNumb);
                        }
                        if (!notNewLine) {
                            readLines.add(new Line(lineNumb, lineName, lineColor));
                        }
                    }
                }
            }
        });
        return readLines;
    }

    public ArrayList<Connection> readMetroConnection(String siteUrl) throws IOException {
//Connection
        ArrayList<Connection> readConnections = new ArrayList<>();
        ArrayList<String> stationsLineInfo = new ArrayList<>();
        ArrayList<String> stationsConnectInfo = new ArrayList<>();
        ArrayList<Station> connectionStations = new ArrayList<>();
        String[] stationCreateInfo = new String[3];
        int lineNumberIndex = 0;
        int stationNumberIndex = 1;
        int stationNameIndex = 2;
        int lineInfo_line = 0;
        int lineInfo_numberStation = 2;

        Document doc = Jsoup.connect(siteUrl).maxBodySize(0).get();
        Elements element = doc.select("td");
        element.forEach(e ->
        {
            String lineStationNumbers = e.select("span").text();
            if (lineStationNumbers.trim().split(" ").length >= 3) {
                String lineINFO[] = e.select("span").text().trim().split(" ");
                if (lineINFO[lineInfo_line].matches("\\d+.") && lineINFO[lineInfo_numberStation].matches("\\d+")) {
                    String lineNumber = lineINFO[e.select("span").text().trim().split(" ").length - 3];
                    String stationNumber = lineINFO[e.select("span").text().trim().split(" ").length - 1];
                    stationCreateInfo[lineNumberIndex] = lineNumber;
                    stationCreateInfo[stationNumberIndex] = stationNumber;
                }
            }
            if (lineStationNumbers.trim().split(" +").length >= 1) {
                String lineINFO[] = e.select("span").text().trim().split(" +");
                boolean allNum = true;
                for (int i = 0; i < lineINFO.length; i++) {
                    if (!lineINFO[i].matches("\\d+.")) {
                        allNum = false;
                    }
                    if (allNum) {
                        stationsLineInfo.add(lineINFO[i]);
                    }
                }
            }
            if ((e.select("a").attr("title").contains(" (станция"))
                    || (e.select("a").attr("title").matches("Фонвизинская"))
                    || (e.select("a").attr("title").matches("Полежаевская"))) {
                String stationName = e.select("a").attr("title");
                stationCreateInfo[stationNameIndex] = stationName;
            }
            if (stationCreateInfo[stationNameIndex] != null && stationCreateInfo[lineNumberIndex] != null) {
                if (stationCreateInfo[stationNameIndex].contains("(")) {
                    int indexStartComment = stationCreateInfo[stationNameIndex].indexOf("(");
                    stationCreateInfo[stationNameIndex] = stationCreateInfo[stationNameIndex].substring(0, (indexStartComment - 1));
                }
                stationsLineInfo.clear();
                stationsConnectInfo.clear();
                connectionStations.clear();
                connectionStations.add(new Station(stationCreateInfo[lineNumberIndex], stationCreateInfo[stationNumberIndex], stationCreateInfo[stationNameIndex]));
                for (int i = 0; i <= 2; i++) {
                    stationCreateInfo[i] = null;
                }
            }
            if (e.select("span").attr("title").contains("Переход ")) {
                e.select("span").forEach(e2 -> {
                    if (e2.attr("title").contains("Переход ")) {
                        stationsConnectInfo.add(e2.attr("title"));
                    }
                });
                for (int i = 0; i < stationsConnectInfo.size(); i++) {
                    for (int j = 0; j < stations.size(); j++) {
                        if (stationsLineInfo.get(i).contains(stations.get(j).getLine())
                                && (stationsConnectInfo.get(i).contains(stations.get(j).getName()))) {
                            connectionStations.add(stations.get(j));
                        }

                    }
                }
            }
            if (connectionStations.size() >= 2) {
                readConnections.add(new Connection(new ArrayList<Station>(connectionStations)));
                connectionStations.clear();
            }
        });
        return readConnections;
    }
}
