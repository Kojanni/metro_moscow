package metroElements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {
    private static String RED = "#EF161E";
    private static String GREEN = "#2DBE2C";
    private static String BLUE = "#0078BE";
    private static String LIGHT_BLUE = "#00BFFF";
    private static String BROWN = "#8D5B2D";
    private static String ORANGE = "#ED9121";
    private static String PURPLE = "#800080";
    private static String YELLOW = "#FFD702";
    private static String GREY = "#999999";
    private static String LIME = "#2DBE2C";
    private static String TEAL = "#82C0C0";
    private static String BLUE_GREY = "#A1B3D4";
    private static String PALE_VIOLET = "#DE64A1";
    private static String MCR_14 = "#FFFFFF";
    private static String MM = "#9999FF";
    private static String NONE = "none";
    private static String LIGHT_GREEN = "#99CC00";

    private String number;
    private String name;
    private String color;

    public Line(String number, String name, String color) {
        this.number = number;
        this.name = name;
        this.color = determLineColor(color);
    }

    @Override
    public String toString() {
        return number + ": " + name + " - " + color;
    }

    public String getNumber(){
        return number;
    }

    //переводит нумерной цвет в именной
    public static String determLineColor(String background) {
        String lineColor = null;
        if (background.contains(RED)) {
            lineColor = "Red";
        }
        if (background.contains(GREEN)) {
            lineColor = "Bright green";
        }
        if (background.contains(BLUE)) {
            lineColor = "Blue";
        }
        if (background.contains(LIGHT_BLUE)) {
            lineColor = "Light blue";
        }
        if (background.contains(BROWN)) {
            lineColor = "Ring  line";
        }
        if (background.contains(ORANGE)) {
            lineColor = "Orange";
        }
        if (background.contains(PURPLE)) {
            lineColor = "Purple";
        }
        if (background.contains(YELLOW)) {
            lineColor = "Yellow";
        }
        if (background.contains(GREY)) {
            lineColor = "Grey";
        }
        if (background.contains(LIME)) {
            lineColor = "Lime";
        }
        if (background.contains(TEAL)) {
            lineColor = "Teal";
        }
        if (background.contains(BLUE_GREY)) {
            lineColor = "Blue-grey";
        }
        if (background.contains(PALE_VIOLET)) {
            lineColor = "Pale violet";
        }
        if (background.contains(MCR_14)) {
            lineColor = "Moscow central ring";
        }
        if (background.contains(MM)) {
            lineColor = "Moscow Monorail";
        }

        if (background.contains(LIGHT_GREEN)) {
            lineColor = "Light green";
        }
        if (lineColor == null) {
            lineColor = "STATION CONSTRUCTION";
        }
        return lineColor;
    }
}
