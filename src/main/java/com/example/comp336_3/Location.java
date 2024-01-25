package com.example.comp336_3;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Location {

    private String name;
    private double longitude;
    private double latitude;
    private String type;
    private boolean sourceOrTarget = false;

    /////////////////////////////////////////////////////////

    private RadioButton radioButton = new RadioButton();
    private ToggleGroup group;
    static ObservableList<String> items;

    /////////////////////////////////////////////////////////


    public Location() {
        // TODO Auto-generated constructor stub
    }

    public Location(String line) {
        String[] arr = line.split(":");

        this.name = arr[0];
        this.latitude = Double.parseDouble(arr[1]);
        this.longitude = Double.parseDouble(arr[2]);
        this.type = arr[3];

        radioButton.setToggleGroup(group);


        if (type.equals("City")) {

            radioButton.setPadding(new Insets(-15, 0, -15, -6));
            String imageUrlD = getClass().getClassLoader().getResource("pin.png").toExternalForm();
            Image mG = new Image(imageUrlD);
            ImageView vi = new ImageView(mG);
            vi.setFitHeight(32);
            vi.setFitWidth(16);

            radioButton.setGraphic(vi);
        } else if (type.equals("Street")) {
            radioButton.setPadding(new Insets(-7.5, 0, -7.5, -3));
            String imageUrlD = getClass().getClassLoader().getResource("rec.png").toExternalForm();
            Image mG = new Image(imageUrlD);
            ImageView vi = new ImageView(mG);
            vi.setFitHeight(4);
            vi.setFitWidth(4);

            radioButton.setGraphic(vi);
        } else {
            System.out.println("Error");
        }

        /////////////////////////////////////////////////
        Tooltip tip = new Tooltip(this.getName());
        tip.setFont(new Font(16));
        tip.setStyle("-fx-background-color:grey;");
        tip.setShowDelay(Duration.seconds(0));
        radioButton.setTooltip(tip);

        if (type.equals("City")) {
            radioButton.setOnAction(o -> {
                if (Main.numOf_SelectedCities == 0) {//source
                    System.out.println(Main.numOf_SelectedCities);


                    String fromPinUrl = getClass().getClassLoader().getResource("fromPin.png").toExternalForm();
                    Image imageFromPin = new Image(fromPinUrl);
                    ImageView vImageFromPin = new ImageView(imageFromPin);

                    vImageFromPin.setFitHeight(32);
                    vImageFromPin.setFitWidth(16);
                    radioButton.setGraphic(vImageFromPin);

                    int index = getItemIndex(this.getName());

                    System.out.println(radioButton.getText());
                    if (index != -1) {
                        Main.sourceCombo.getSelectionModel().select(index);// this line increases Main.numOf_SelectedCities by default
                        Main.numOf_SelectedCities--;
                    } else {
                        System.out.println("****************************Error! Item not found!****************************");
                    }

                    System.out.println(Main.numOf_SelectedCities);
                } else if (Main.numOf_SelectedCities == 1) {//target

                    String toPinUrl = getClass().getClassLoader().getResource("toPin.png").toExternalForm();
                    Image imageToPin = new Image(toPinUrl);
                    ImageView vImageToPin = new ImageView(imageToPin);

                    vImageToPin.setFitHeight(32);
                    vImageToPin.setFitWidth(16);

                    radioButton.setGraphic(vImageToPin);

                    int index = getItemIndex(this.getName());


                    System.out.println(radioButton.getText());
                    if (index != -1) {
                        Main.targetCombo.getSelectionModel().select(index);// this line increases Main.numOf_SelectedCities by default
                        Main.numOf_SelectedCities--;
                    } else {
                        System.out.println("****************************Error! Item not found!****************************");
                    }
                    System.out.println(Main.numOf_SelectedCities);
                }

                radioButton.setSelected(true);
                sourceOrTarget = true;
                Main.numOf_SelectedCities += 1;
                if (Main.numOf_SelectedCities == 2) {
                    Main.lockAllCities();
                    radioButton.setDisable(false);
                }

                if (Main.click.isSelected()) {
                    if (Main.numOf_SelectedCities == 2) {
                        Main.targetCombo.getSelectionModel().select(getName());
                    }
                    if (Main.numOf_SelectedCities == 1) {
                        Main.sourceCombo.getSelectionModel().select(getName());
                    }
                }
                System.out.println(Main.numOf_SelectedCities);
            });
        }

    }

    private int getItemIndex(String nameOfItem) {
        items = Main.sourceCombo.getItems();

        int index = -1;

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(nameOfItem)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void setSourceOrTarget(boolean sourceOrTarget) {
        this.sourceOrTarget = sourceOrTarget;
    }

    public boolean getSourceOrTarget() {

        return sourceOrTarget;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

}
