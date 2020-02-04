package metroElements;

import JSON.HtmlParsing;
import java.io.IOException;
import java.util.ArrayList;


public class Metro implements Cloneable {
    private transient ArrayList<Station> stations;
    private transient ArrayList<Connection> connections;
    private ArrayList<Line> lines;


    public Metro(ArrayList<Station> stations, ArrayList<Line> lines, ArrayList<Connection> connections) {
        this.stations = new ArrayList<>(stations);
        this.lines = new ArrayList<>(lines);
        this.connections = new ArrayList<>(connections);

    }

    public Metro(String SiteUrl) throws IOException {
        HtmlParsing htmlParsing = new HtmlParsing(SiteUrl);
        ArrayList<Line> lines = htmlParsing.getLines();
        ArrayList<Station> stations = htmlParsing.getStations();
        ArrayList<Connection> connections = htmlParsing.getConnections();
        this.stations = new ArrayList<>(stations);
        this.lines = new ArrayList<>(lines);
        this.connections = new ArrayList<>(connections);
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void writeAllStations() {
        System.out.println("---STATIONS---");
        for (int i = 0; i < stations.size(); i++) {
            System.out.println(stations.get(i));
        }
    }

    public void writeAllLines() {
        System.out.println("---LINES---");
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(lines.get(i));
        }
    }

    public void writeAllConnections() {
        System.out.println("---CONNECTIONS---");
        for (int i = 0; i < connections.size(); i++) {
            System.out.println(connections.get(i));
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
