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
        readMetroLine();
        readMetroStation();
        readMetroConnection();
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

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    public void readMetroStation() throws IOException {
        ArrayList<Station> readStation = new ArrayList<>();
        Document doc = Jsoup.connect(this.siteUrl).maxBodySize(0).get();
        Elements element = doc.select("td");
        String[] stationCreateInfo = new String[3];
        element.forEach(e ->
        {
            String lineStationNumbers = e.select("span").text();
            if (lineStationNumbers.trim().split(" ").length >= 3) {
                String lineINFO[] = e.select("span").text().trim().split(" ");
                if (lineINFO[0].matches("\\d+.") && lineINFO[2].matches("\\d+")) {
                    String lineNumber = lineINFO[e.select("span").text().trim().split(" ").length - 3];
                    String stationNumber = lineINFO[e.select("span").text().trim().split(" ").length - 1];
                    stationCreateInfo[0] = lineNumber;
                    stationCreateInfo[1] = stationNumber;
                }
            }
            if ((e.select("a").attr("title").contains(" (станция"))
                    || (e.select("a").attr("title").matches("Фонвизинская"))
                    || (e.select("a").attr("title").matches("Полежаевская"))) {
                String stationName = e.select("a").attr("title");
                stationCreateInfo[2] = stationName;
            }
            if (stationCreateInfo[2] != null && stationCreateInfo[0] != null) {
                if (stationCreateInfo[2].contains("(")) {
                    int indexStartComment = stationCreateInfo[2].indexOf("(");
                    //System.out.println(stationCreateInfo[2] + ": " + indexStartComment);
                    stationCreateInfo[2] = stationCreateInfo[2].substring(0, (indexStartComment - 1));
                }
                readStation.add(new Station(stationCreateInfo[0], stationCreateInfo[1], stationCreateInfo[2]));
                for (int i = 0; i <= 2; i++) {
                    stationCreateInfo[i] = null;
                }
            }
        });
        stations = readStation;
    }

    public void readMetroLine() throws IOException {
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
        this.lines = readLines;
    }

    public void readMetroConnection() throws IOException {
//Connection
        ArrayList<Connection> readConnections = new ArrayList<>();
        ArrayList<String> stationsLineInfo = new ArrayList<>();
        ArrayList<String> stationsConnectInfo = new ArrayList<>();
        ArrayList<Station> connectionStations = new ArrayList<>();
        String[] stationCreateInfo = new String[3];

        Document doc = Jsoup.connect(this.siteUrl).maxBodySize(0).get();
        Elements element = doc.select("td");
        element.forEach(e ->
        {
            String lineStationNumbers = e.select("span").text();
            if (lineStationNumbers.trim().split(" ").length >= 3) {
                String lineINFO[] = e.select("span").text().trim().split(" ");
                if (lineINFO[0].matches("\\d+.") && lineINFO[2].matches("\\d+")) {
                    String lineNumber = lineINFO[e.select("span").text().trim().split(" ").length - 3];
                    String stationNumber = lineINFO[e.select("span").text().trim().split(" ").length - 1];
                    stationCreateInfo[0] = lineNumber;
                    stationCreateInfo[1] = stationNumber;
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
                stationCreateInfo[2] = stationName;
            }
            if (stationCreateInfo[2] != null && stationCreateInfo[0] != null) {
                if (stationCreateInfo[2].contains("(")) {
                    int indexStartComment = stationCreateInfo[2].indexOf("(");
                    stationCreateInfo[2] = stationCreateInfo[2].substring(0, (indexStartComment - 1));
                }
                stationsLineInfo.clear();
                stationsConnectInfo.clear();
                connectionStations.clear();
                connectionStations.add(new Station(stationCreateInfo[0], stationCreateInfo[1], stationCreateInfo[2]));
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
        setConnections(readConnections);
    }
}
