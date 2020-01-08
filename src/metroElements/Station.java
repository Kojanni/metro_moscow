package metroElements;

public class Station {
    String line;
    String number;
    String name;

    public Station( String line, String number, String name) {
        this.line = line;
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return line + " - " + number + ": " + name;
    }
}
