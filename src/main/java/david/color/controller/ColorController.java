package david.color.controller;

import david.color.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorController implements Initializable {

    private Color color;

    @FXML
    private TextField textFieldRedValue;
    @FXML
    private TextField textFieldGreenValue;
    @FXML
    private TextField textFieldBlueValue;
    @FXML
    private TextField textFieldHexValue;

    @FXML
    private Slider progressRed;
    @FXML
    private Slider progressGreen;
    @FXML
    private Slider progressBlue;

    @FXML
    private Pane paneWindowColor;
    @FXML
    private Pane paneOne;
    @FXML
    private Pane paneTwo;
    @FXML
    private Pane paneThree;
    @FXML
    private Pane paneFour;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        color = new Color(24, 220, 11);

        this.updateColorValues();

        // CHANGE SLIDERS
        this.detectSlideChange(this.progressRed, textFieldRedValue);
        this.detectSlideChange(this.progressGreen, textFieldGreenValue);
        this.detectSlideChange(this.progressBlue, textFieldBlueValue);

        // CHANGE CHAMPS INPUT RGB
        this.detectRgbTextFieldChange(this.progressRed, this.textFieldRedValue);
        this.detectRgbTextFieldChange(this.progressGreen, this.textFieldGreenValue);
        this.detectRgbTextFieldChange(this.progressBlue, this.textFieldBlueValue);

        // CHANGE CHAMP INPUT HEXA
        this.detectHexTextFieldChange();

        // UPDATE COLOR VIEW
        this.updatePaneWindowColor();
        this.updatePanesSamples();
    }

    /**
     * Mise à jour de la couleur dans la fenêtre
     */
    public void updatePaneWindowColor() {
        this.paneWindowColor.setStyle("-fx-background-color: rgb("
                + this.color.getRed() + ", "
                + this.color.getGreen() + ", "
                + this.color.getBlue() + ")"
        );
        this.updatePanesSamples();
    }

    public void updatePanesSamples() {
        int paneOneValue = (this.color.getRed() + 50 > 255) ? this.color.getRed() - 50 : this.color.getRed() + 50;
        int paneTwoValue = (this.color.getGreen() + 50 > 255) ? this.color.getGreen() - 50 : this.color.getGreen() + 50;
        int paneThreeValue = (this.color.getBlue() + 50 > 255) ? this.color.getBlue() - 50 : this.color.getBlue() + 50;

        this.paneOne.setStyle("-fx-background-color: rgb(" + paneOneValue + ", " + this.color.getGreen() + ", " + this.color.getBlue() + ")");
        this.paneTwo.setStyle("-fx-background-color: rgb(" + this.color.getRed() + ", " + paneTwoValue + ", " + this.color.getBlue() + ")");
        this.paneThree.setStyle("-fx-background-color: rgb(" + this.color.getRed() + ", " + this.color.getGreen() + ", " + paneThreeValue + ")");
        this.paneFour.setStyle("-fx-background-color: rgb(" + paneOneValue + ", " + paneTwoValue + ", " + paneThreeValue + ")");
    }

    /**
     * Mise à jour de la valeur hexa, des valeurs RGB et des positions des sliders
     */
    public void updateColorValues() {
        // SLIDERS
        this.progressRed.setValue(color.getRed());
        this.progressGreen.setValue(color.getGreen());
        this.progressBlue.setValue(color.getBlue());
        // TEXT FIELDS
        this.textFieldRedValue.setText(String.valueOf(color.getRed()));
        this.textFieldGreenValue.setText(String.valueOf(color.getGreen()));
        this.textFieldBlueValue.setText(String.valueOf(color.getBlue()));
        this.textFieldHexValue.setText(this.color.getHexValue());
    }

    /**
     * Changement de l'un des 3 sliders
     * @param slider L'un des 3 slides RGB
     * @param textField Le text field associé à la couleur RGB
     */
    public void detectSlideChange(Slider slider, TextField textField) {
        slider.setOnMouseReleased(mouseEvent -> {
            // Récupération de la valeur de position du slide
            int colorValue = (int) Math.round(slider.getValue());
            // Mise à jour de la valeur
            useTheRightSetMethod(textField, colorValue);
            // Mise à jour du text field rgb
            textField.setText(String.valueOf(colorValue));
            // Mise à jour du text field hexa
            this.textFieldHexValue.setText(String.format("#%02X%02X%02X", this.color.getRed(), this.color.getGreen(), this.color.getBlue()));
            // Mise à jour de la couleur dans sa fenêtre
            this.updatePaneWindowColor();
        });
    }

    /**
     * Chagement de l'un des champs text field RGB
     * @param slider L'un des 3 slides RGB
     * @param textField Le text field associé à la couleur RGB
     */
    public void detectRgbTextFieldChange(Slider slider, TextField textField) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                //System.out.println(textField.getId());
                this.useTheRightSetMethod(textField, Integer.parseInt(newValue));
                slider.setValue(Double.parseDouble(newValue));
                // Mise à jour de la couleur dans sa fenêtre
                this.updatePaneWindowColor();
                // Mise à jour de la valeur hexadécimale
                this.textFieldHexValue.setText(String.format("#%02X%02X%02X", this.color.getRed(), this.color.getGreen(), this.color.getBlue()));

            } catch (NumberFormatException e) {
                System.err.println("Seul les chiffres sont autorisés !");
                textField.setText("0");
                slider.setValue(0);
                this.useTheRightSetMethod(textField, Integer.parseInt(newValue));

            } catch (IllegalArgumentException e) {
                System.err.println("Les valeurs RGB doit être comprises entre 0 et 255");
                int defaultValue = Integer.parseInt(newValue) > 255 ? 255 : 0;
                textField.setText(String.valueOf(defaultValue));
                slider.setValue(defaultValue);
                this.useTheRightSetMethod(textField, Integer.parseInt(newValue));
                // Mise à jour de la couleur dans sa fenêtre
                this.updatePaneWindowColor();
                // Mise à jour de la valeur hexadécimale
                this.textFieldHexValue.setText(String.format("#%02X%02X%02X", this.color.getRed(), this.color.getGreen(), this.color.getBlue()));
            }
        });
    }

    /**
     * Changement du champ texte hexadécimal
     */
    public void detectHexTextFieldChange() {
        textFieldHexValue.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() == 7) {
                try {
                    this.color.setHexValue(newValue);
                    this.updatePaneWindowColor();
                    this.updateColorValues();
                } catch (IllegalArgumentException e) {
                    System.err.println("Ce n'est pas une bonne valeur hexadécimale.");
                    this.color.setHexValue(oldValue);
                    this.updatePaneWindowColor();
                    this.updateColorValues();
                }
            }
        });
    }

    /**
     * Déduction la bonne méthode set à utiliser, par rapport au text field modifié
     * @param textField Le text field à observer
     * @param rgbValue La valeur RGB à passer
     */
    public void useTheRightSetMethod(TextField textField, int rgbValue) {
        if (textField.getId().equals("textFieldRedValue")) {
            this.color.setRed(rgbValue);
        }
        if (textField.getId().equals("textFieldGreenValue")) {
            this.color.setGreen(rgbValue);
        }
        if (textField.getId().equals("textFieldBlueValue")) {
            this.color.setBlue(rgbValue);
        }
    }
}