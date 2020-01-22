package metroElements;

import java.util.ArrayList;

public class Connection {
    private ArrayList<Station> connectionStations = new ArrayList<>();

    public Connection(ArrayList<Station> stations) {
        setConnectionStations(stations);
    }

    public ArrayList<Station> getConnectionStations() {
        return connectionStations;
    }

    public void setConnectionStations(ArrayList<Station> connectionStations) {
        this.connectionStations = connectionStations;
    }

    @Override
    public String toString() {
        String returnStr = "Line  |  Station\n";
        for (Station stName : getConnectionStations()) {
            returnStr += stName.line + "  |  " + stName.name + "\n";
        }
        return returnStr;
    }
}
