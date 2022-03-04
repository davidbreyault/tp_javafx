package david.color.model;

import java.util.regex.Pattern;

public class Color {

    private int red;
    private int green;
    private int blue;
    private String hexValue;
    private final int MIN_RGB_VALUE = 0;
    private final int MAX_RGB_VALUE = 255;
    private static final String HEX_WEBCOLOR_PATTERN = "^#([A-F0-9]{6}|[A-F0-9]{3})$";
    private static final Pattern pattern = Pattern.compile("^#([A-F0-9]{6}|[A-F0-9]{3})$");

    public Color(int red, int green, int blue) {
        if (this.rgbCorrectValue(red) && this.rgbCorrectValue(green) && this.rgbCorrectValue(blue)) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.hexValue = String.format("#%02X%02X%02X", red, green, blue);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Color(String codeHexa) {
        if (codeHexa != null && pattern.matcher(codeHexa).matches()) {
            this.hexValue = codeHexa;
            this.red = Integer.valueOf(codeHexa.substring(1, 3), 16);
            this.green = Integer.valueOf(codeHexa.substring(3, 5), 16);
            this.blue = Integer.valueOf(codeHexa.substring(5, 7), 16);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean rgbCorrectValue(int rgbValue) {
        return rgbValue >= 0 && rgbValue <= 255;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public String getHexValue() {
        return this.hexValue;
    }

    public void setRed(int redValue) {
        if (this.rgbCorrectValue(redValue)) {
            this.red = redValue;
            this.hexValue = String.format("#%02X%02X%02X", redValue, this.green, this.blue);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setGreen(int greenValue) {
        if (this.rgbCorrectValue(greenValue)) {
            this.green = greenValue;
            this.hexValue = String.format("#%02X%02X%02X", this.red, greenValue, this.blue);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setBlue(int blueValue) {
        if (this.rgbCorrectValue(blueValue)) {
            this.blue = blueValue;
            this.hexValue = String.format("#%02X%02X%02X", this.red, this.green, blueValue);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setHexValue(String codeHexa) {
        if (codeHexa != null && pattern.matcher(codeHexa).matches()) {
            this.hexValue = codeHexa;
            this.red = Integer.valueOf(codeHexa.substring(1, 3), 16);
            this.green = Integer.valueOf(codeHexa.substring(3, 5), 16);
            this.blue = Integer.valueOf(codeHexa.substring(5, 7), 16);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String toString() {
        return "[value=#D58D35, r=213, g=141, b=53]";
    }
}
