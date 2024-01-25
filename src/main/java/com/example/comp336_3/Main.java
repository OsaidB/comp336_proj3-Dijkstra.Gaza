package com.example.comp336_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {
    public static File file;
    static ComboBox<String> sourceCombo = new ComboBox<String>();
    static ComboBox<String> targetCombo = new ComboBox<String>();
    static ToggleButton click = new ToggleButton("Click in map");
    static ToggleButton combo = new ToggleButton("Combo Box");
    ////////////////////////////////////////////////////////////////////////////////////
    static int numOf_SelectedCities = 0;
    static Pane pane2 = new Pane();
    private Alert error = new Alert(AlertType.ERROR);
    static ArrayList<Vertex> citiesAndStreets = new ArrayList<>();
    ////////////////////////////////////////////////////////////////////////////////////
    //original coordinates
    static double org_xMin = 34.1707489947603;//measure X {Minimum}
    static double org_xMax = 34.575060834817954;//measure X {Maximum}
    static double org_yMin = 31.614521165206845;//measure Y {Minimum}
    static double org_yMax = 31.208163033163977;//measure Y {Maximum}

    ////////////////////////////////////////////////////////////////////////////////////
    //picture coordinates
    static double pic_xMin = 0;//measure X {Minimum}
    static double pic_xMax = 589;//measure X {Maximum}
    static double pic_yMin = 0;//measure Y {Minimum}
    static double pic_yMax = 695;//measure Y {Maximum}
    ////////////////////////////////////////////////////////////////////////////////////
    Button run;
    Button reset;
    AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator();
    boolean flag_CrossroadsVisible = false;
    String finalResult = "";


    @Override
    public void start(Stage primaryStage) throws URISyntaxException, FileNotFoundException {

        primaryStage.setTitle("OsaidB_1203115");

        //main pane that contains everything
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(10));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//0
        //making all of the lift side of the interface

        //0.1   :   setting up Image and making it zoomable
        String imageUrlD = getClass().getClassLoader().getResource("Gaza.png").toExternalForm();
        Image mG = new Image(imageUrlD);
        ImageView image = new ImageView(mG);
        pane2.getChildren().add(image);

        StackPane zoomableImagePane = new StackPane();
        zoomableImagePane.getChildren().add(new Circle(100, 100, 10));
        zoomableImagePane.getChildren().add(new Circle(200, 200, 20));
        zoomableImagePane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double zoomFactor = 1.5;
                if (event.getDeltaY() <= 0) {
                    // zoom out
                    zoomFactor = 1 / zoomFactor;
                }
                zoomOperator.zoom(zoomableImagePane, zoomFactor, event.getSceneX(), event.getSceneY());
            }
        });

        zoomableImagePane.getChildren().add(pane2);
        zoomableImagePane.setAlignment(Pos.CENTER);
/////////////////////////////////////////////////////////////////////
        //0.2   :   putting zoomableImagePane inside a ScrollPane
        ScrollPane finalImagePane = new ScrollPane(zoomableImagePane);
        //not to make it scrollable,
        // but to make like a boarder around the map

/////////////////////////////////////////////////////////////////////
        //0.3   :   setting up the two Buttons under the "finalImagePane"
        Button btnResetImage = new Button("Reset View");
        btnResetImage.setOnAction(event -> resetPane(zoomableImagePane));

        Button btnShowCross = new Button("Show Crossroads");
        btnShowCross.setOnAction(event -> showCrossroads());

/////////////////////////////////////////////////////////////////////
        //0.4_summing   :   finalImagePane  +   btns
        VBox vbLeftSide = new VBox(finalImagePane, btnResetImage, btnShowCross);
        vbLeftSide.setAlignment(Pos.BASELINE_RIGHT);
        vbLeftSide.setSpacing(20);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//1
        //getting the file and starts reading it
        Path filePath = Paths.get(Main.class.getClassLoader().getResource("Data.txt").toURI());
        file = new File(filePath.toUri());
        readFile(file);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//2
        //assigning {setOnAction} for "sourceCombo" AND targetCombo
        //2.1
        sourceCombo.setOnAction(e -> {

            for (Vertex vertex : citiesAndStreets) {
                String type = vertex.getLocation().getType();
                if (type.equals("City") && vertex.getLocation().getName().equals(sourceCombo.getSelectionModel().getSelectedItem())) {

                    String fromPinUrl = getClass().getClassLoader().getResource("fromPin.png").toExternalForm();
                    Image imageFromPin = new Image(fromPinUrl);
                    ImageView vImageFromPin = new ImageView(imageFromPin);

                    vImageFromPin.setFitHeight(32);
                    vImageFromPin.setFitWidth(16);

                    vertex.getLocation().getRadioButton().setGraphic(vImageFromPin);
                    vertex.getLocation().getRadioButton().setSelected(true);//////////////////////////////////

                    numOf_SelectedCities += 1;
                    vertex.getLocation().setSourceOrTarget(true);
                    if (numOf_SelectedCities == 2) {
                        lockAllCities();
                        run.setDisable(false);
                        reset.setDisable(false);

                        targetCombo.setDisable(true);
                        sourceCombo.setDisable(true);
                        vertex.getLocation().getRadioButton().setDisable(false);
                    } else if (numOf_SelectedCities == 1) {
                        reset.setDisable(false);
                    }

                    break;
                }
            }

        });

        //2.2
        targetCombo.setOnAction(e -> {

            for (Vertex v : citiesAndStreets) {
                String type = v.getLocation().getType();


                if (type.equals("City") && v.getLocation().getName().equals(targetCombo.getSelectionModel().getSelectedItem())) {

                    String toPinUrl = getClass().getClassLoader().getResource("toPin.png").toExternalForm();
                    Image imageToPin = new Image(toPinUrl);
                    ImageView vImageToPin = new ImageView(imageToPin);

                    vImageToPin.setFitHeight(32);
                    vImageToPin.setFitWidth(16);

                    v.getLocation().getRadioButton().setGraphic(vImageToPin);
                    v.getLocation().getRadioButton().setSelected(true);

                    numOf_SelectedCities += 1;
                    v.getLocation().setSourceOrTarget(true);
                    if (numOf_SelectedCities == 2) {
                        lockAllCities();
                        run.setDisable(false);
                        reset.setDisable(false);

                        targetCombo.setDisable(true);
                        sourceCombo.setDisable(true);
                        v.getLocation().getRadioButton().setDisable(false);
                    } else if (numOf_SelectedCities == 1) {
                        reset.setDisable(false);
                    }

                    break;
                }


            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//3
        //creating interface
        //3.1   :   sources
        Label source = new Label("Source :");
        source.setStyle("-fx-text-fill: #dfdbd8;");
        source.setDisable(true);
        source.setPadding(new Insets(7));

        sourceCombo.setMinHeight(30);
        sourceCombo.setMinWidth(440);

        //filling sourceCombo with data
        for (Vertex v : citiesAndStreets) {
            if (v.getLocation().getType().equals("City")) {
                sourceCombo.getItems().add(v.getLocation().getName());
            }
        }
        VBox vbSources = new VBox(source, sourceCombo);
        vbSources.setAlignment(Pos.TOP_LEFT);
/////////////////////////////////////////////////////////////////////
        //3.2   :   targets
        Label target = new Label("Target :");

        target.setStyle("-fx-text-fill: #dfdbd8;");
        target.setDisable(true);
        target.setPadding(new Insets(7));

        targetCombo.setMinHeight(30);
        targetCombo.setMinWidth(440);

        //filling targetCombo with data
        for (Vertex v : citiesAndStreets) {
            if (v.getLocation().getType().equals("City")) {
                targetCombo.getItems().add(v.getLocation().getName());
            }
        }
        VBox vbTargets = new VBox(target, targetCombo);
        vbTargets.setAlignment(Pos.TOP_LEFT);
/////////////////////////////////////////////////////////////////////
        //3.3   :   run/reset
        run = new Button("Run");
        run.setMinWidth(440);
        run.setDisable(true);
        run.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        reset = new Button("Reset");
        reset.setMinWidth(440);
        reset.setDisable(true);
        reset.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");

        VBox vbBTNS = new VBox(20, run, reset);
        vbBTNS.setAlignment(Pos.CENTER);
/////////////////////////////////////////////////////////////////////
        //3.3_summingSection1   :   summing up {sources,targets,run/reset}
        VBox vbInputs = new VBox(vbSources, vbTargets, vbBTNS);
        vbInputs.setPadding(new Insets(20));
        vbInputs.setSpacing(30);
        vbInputs.setStyle("-fx-border-color: #9d7463; -fx-border-width: 2px;");
/////////////////////////////////////////////////////////////////////
        //3.4   :   results Section
        Button lblResultsSec = new Button("Results");
        lblResultsSec.setDisable(true);
        lblResultsSec.setPrefWidth(480);
        lblResultsSec.setMinHeight(50);
        lblResultsSec.setStyle("-fx-background-color: #455954 ;-fx-opacity: 1;-fx-font-size: 20px;-fx-font-weight: bold;");

//////////////////////
        Label lblPath = new Label("Shortest Path:");
        lblPath.setPadding(new Insets(7));
        lblPath.setPadding(new Insets(7));

        TextArea resultPath_txtAr = new TextArea();
        resultPath_txtAr.setStyle("-fx-font-size: 20px;");
        resultPath_txtAr.setPrefWidth(280);

        HBox hBox_path = new HBox(lblPath, resultPath_txtAr);
        hBox_path.setSpacing(57);
        hBox_path.setAlignment(Pos.TOP_LEFT);
//////////////////////
        Label lblDistance = new Label("Distance (in meters):");
        lblDistance.setStyle("-fx-font-size: 20px;");
        lblDistance.setMinHeight(50);
        lblDistance.setAlignment(Pos.CENTER_LEFT);
        lblDistance.setPadding(new Insets(7));

        TextField txtDistance = new TextField();
        txtDistance.setMinHeight(40);
        txtDistance.setPrefWidth(280);
        txtDistance.setEditable(false);

        HBox hBox_distance = new HBox(lblDistance, txtDistance);
        hBox_distance.setAlignment(Pos.CENTER_LEFT);
/////////////////////////////////////////////////////////////////////
        //3.4_summingSection2   :   summing up results Section
        VBox vbResult = new VBox(lblResultsSec, hBox_path, hBox_distance);
        vbResult.setSpacing(18);
        vbResult.setStyle("-fx-border-color: #9d7463; -fx-border-width: 2px;");
/////////////////////////////////////////////////////////////////////
        //3.5_summingTheTwoSec   :   {source/target}+{results Section}
        VBox vb_TheTwoSections = new VBox(vbInputs, vbResult);
        vb_TheTwoSections.setSpacing(7);

        VBox vbRightSide = new VBox(10, vb_TheTwoSections);//no need but it's okay
        vbRightSide.setAlignment(Pos.CENTER);
        vbRightSide.setSpacing(20);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//4
        //finishing the visual part
        //4.1   :   putting left side and right side together
        HBox mainBox = new HBox(vbLeftSide, vbRightSide);
        mainBox.setSpacing(30);
        mainBox.setPadding(new Insets(30));

        mainBox.setAlignment(Pos.CENTER);
        /////////////////////////////////////////////////////////////////////
        //4.2_summingTheTwoSec   :   {source/target}+{results Section}
        /////////////////////////////////////////////////////////////////////
        mainPane.setCenter(mainBox);
        mainPane.setAlignment(mainBox, Pos.CENTER);

        mainPane.setStyle("-fx-background-color: #565c5e;");
        mainPane.setMinWidth(1600);
        mainPane.setMinHeight(900);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//5
        //Points on map     +   setOnActions for run & reset
        //5.1   :   Points on map
        setPointsOnMap();//////////

        //5.2   :   setOnActions for run & reset
        run.setOnAction(e -> {
            if (flag_CrossroadsVisible == false) {
                showCrossroads();
            }

            Vertex vertx1 = null;
            Vertex vertx2 = null;

            String s1 = sourceCombo.getValue();
            System.out.println(s1);

            String s2 = targetCombo.getValue();
            System.out.println(s2);

            for (int i = 0; i < citiesAndStreets.size(); i++) {
                if (citiesAndStreets.get(i).getLocation().getName().equals(s1)) {
                    vertx1 = citiesAndStreets.get(i);
                }
                if (citiesAndStreets.get(i).getLocation().getName().equals(s2)) {
                    vertx2 = citiesAndStreets.get(i);
                }
            }

            if (vertx1 != null && vertx2 != null) {

                int i = drawLine(Dijkstra(vertx1, vertx2));
                if (i == 0){
                    txtDistance.setText("0");
                } else if (i == 1){
                    txtDistance.setText(String.valueOf(vertx2.distance));
                }

                resultPath_txtAr.setText(finalResult);
            }

        });

        reset.setOnAction(l -> {
            flag_CrossroadsVisible = false;
            pane2.getChildren().clear();

            targetCombo.getSelectionModel().select(null);
            sourceCombo.getSelectionModel().select(null);

            txtDistance.setText("");

            numOf_SelectedCities = 0;

            pane2.getChildren().add(image);

            String pinUrl = getClass().getClassLoader().getResource("pin.png").toExternalForm();
            Image imagePin = new Image(pinUrl);


            for (Vertex ve : citiesAndStreets) {
                if (ve.getLocation().getType().equals("City")) {
                    ImageView vi = new ImageView(imagePin);
                    vi.setFitHeight(32);
                    vi.setFitWidth(16);

                    ve.getLocation().getRadioButton().setGraphic(vi);
                    ve.getLocation().getRadioButton().setSelected(false);

                    resultPath_txtAr.clear();

                    ve.getLocation().setSourceOrTarget(false);

                }


            }
            freeAllCities();

            for (Vertex ver : citiesAndStreets) {
                ver.visited = false;
                ver.previous = null;
            }

            setPointsOnMap();

            run.setDisable(true);
            reset.setDisable(true);

            targetCombo.setDisable(false);
            sourceCombo.setDisable(false);

            primaryStage.close();
            primaryStage.show();
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//6
        //making the final look scrollable, and putting it in the main final scene
        ScrollPane scroll = new ScrollPane(mainPane);
        scroll.setStyle("-fx-background-color: #565c5e;");
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 1600, 920);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showCrossroads() {
        flag_CrossroadsVisible = true;
        double orgLat = 0;
        double orgLon = 0;

        double picLat = 0;
        double picLon = 0;

        for (int i = 0; i < citiesAndStreets.size(); i++) {
            String type = citiesAndStreets.get(i).getLocation().getType();
            if (type.equals("Street")) {
                RadioButton r = citiesAndStreets.get(i).getLocation().getRadioButton();

                orgLat = citiesAndStreets.get(i).getLocation().getLatitude();
                orgLon = citiesAndStreets.get(i).getLocation().getLongitude();

                picLat = calcPicLatX(orgLat);
                picLon = calcPicLonY(orgLon);

                System.out.println("setting point at (" + picLat + "," + picLon + ")");
                r.setLayoutX(picLat);
                r.setLayoutY(picLon);

                pane2.getChildren().add(r);
            }

        }
    }

    private void resetPane(Pane pane) {
        // Reset the pane to its original state
        pane.setTranslateX(0);
        pane.setTranslateY(0);
        pane.setScaleX(1.0);
        pane.setScaleY(1.0);
    }

    private int drawLine(Vertex target) {

        if (target == null) {
            error.setContentText("No path");
            error.show();
            return 0;
        }

        //"from" vertex
        double orgLat1_Converted = 0;
        double orgLon1_Converted = 0;

        //"to" vertex
        double orgLat2_Converted = 0;
        double orgLon2_Converted = 0;

        System.out.println("===============================================");

        List<Vertex> pathList = new ArrayList<>();
        for (Vertex v = target; v != null; v = v.previous) {

            System.out.println(v.location.getName());
            System.out.println("↓");

            pathList.add(v);
        }

        // V
        Collections.reverse(pathList);

/////////////////////////////////////////////////////////////////////////////////////////////////////

        int tempCounter = 0;

        String currName = "";
        finalResult = "";
        if (pathList.size() >= 1) {

            for (Vertex ver : pathList) {
                currName = ver.getLocation().getName();

                if (tempCounter == 0) {
                    finalResult = currName;
                } else {
                    finalResult = finalResult + "\n↓\n" + currName;
                }
                tempCounter++;
            }

        } else if (pathList.size() <= 1) {
            error.setContentText("No path");
            error.show();
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < pathList.size() - 1; i++) {
            Vertex fromVer = pathList.get(i);
            Vertex toVer = pathList.get(i + 1);

            //"from" vertex coordinates after converting them to pixels
            orgLat1_Converted = calcPicLatX(fromVer.location.getLatitude());
            orgLon1_Converted = calcPicLonY(fromVer.location.getLongitude());

            //"to" vertex coordinates after converting them to pixels
            orgLat2_Converted = calcPicLatX(toVer.location.getLatitude());
            orgLon2_Converted = calcPicLonY(toVer.location.getLongitude());

            Line line = new Line(orgLat1_Converted, orgLon1_Converted, orgLat2_Converted, orgLon2_Converted);

            pane2.getChildren().add(line);
        }

        return 1;

    }

    private void setPointsOnMap() {
        double orgLat = 0;
        double orgLon = 0;

        double picLat = 0;
        double picLon = 0;

        for (int i = 0; i < citiesAndStreets.size(); i++) {
            String type = citiesAndStreets.get(i).getLocation().getType();
            if (type.equals("City")) {
                RadioButton r = citiesAndStreets.get(i).getLocation().getRadioButton();

                orgLat = citiesAndStreets.get(i).getLocation().getLatitude();
                orgLon = citiesAndStreets.get(i).getLocation().getLongitude();

                picLat = calcPicLatX(orgLat);
                picLon = calcPicLonY(orgLon);

                System.out.println("setting point at (" + picLat + "," + picLon + ")");
                r.setLayoutX(picLat);
                r.setLayoutY(picLon);

                pane2.getChildren().add(r);
            }

        }

    }

    private double calcPicLatX(double orgLat) {

        double resultX1;
        //org_xMax-org_xMin	-->		pic_xMax-pic_xMin
        //		orgLat		-->		resultX1
        //cross multiplying
        System.out.println(orgLat + " before converting");

        double rightSideEquation = (orgLat - org_xMin) / (org_xMax - org_xMin);
        double calculating = rightSideEquation * (pic_xMax - pic_xMin);

        resultX1 = calculating + pic_xMin;

        return resultX1;
    }

    private double calcPicLonY(double orgLon) {
        double resultY;

        double rightSideEquation = (orgLon - org_yMin) / (org_yMax - org_yMin);
        double calculating = rightSideEquation * (pic_yMax - pic_yMin);

        resultY = calculating + pic_yMin;

        return resultY;
    }

    public static void main(String[] args) {

        launch(args);
    }

    public static void lockAllCities() {


        for (Vertex v : citiesAndStreets) {
            if (v.getLocation().getType().equals("City")) {
                if (v.getLocation().getSourceOrTarget()) {
                    //do nothing
                } else {
                    v.getLocation().getRadioButton().setDisable(true);
                }
            }
        }
    }

    public static void freeAllCities() {
        for (Vertex v : citiesAndStreets) {
            if (v.getLocation().getType().equals("City")) {
                v.getLocation().getRadioButton().setDisable(false);
            }
        }

    }

    public Vertex Dijkstra(Vertex Source, Vertex Destination) {// O(n) = (V(logV+E))
        Source.distance = 0;
        if (Source == Destination) {
            return null;
        }

//        double minimumDis = -1;
//        for (Vertex v : citiesAndStreets) {//searching for minimum unknown distance
//            if (!v.visited) {//unknown
//                if (v.getDistance() < minimumDis) {
//                    minimumDis = v.getDistance();
//                }
//
//            }
//        }


        PriorityQueue<Vertex> pq = new PriorityQueue<>(new Comparator<Vertex>() { // Log V
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return Double.compare(v1.distance, v2.distance);
                //returns a negative integer, zero,
                // or a positive integer
                // if the first object is less than,
                // equal to,
                // or greater than the second object
            }
        });

        pq.add(Source);

        while (!pq.isEmpty()) { // V
            Vertex currVer = pq.poll(); //// Log V

            currVer.visited = true;
            if (currVer.location.getName().equals(Destination.getLocation().getName())) {
                break;
            }

            for (Edge edge : currVer.getAdjacentsList()) { // E
                Vertex v = edge.targetVer;
                if (!v.visited) {
                    double currDistance = edge.calculatedDistance;
                    double distanceThroughU = currVer.distance + currDistance;
                    if (distanceThroughU < v.distance) {
                        v.distance = distanceThroughU;
                        v.previous = currVer;
                        pq.add(v);
                    }
                }
            }
        }

        return Destination;
    }

    public static void readFile(File file) throws FileNotFoundException {

        Scanner sc = new Scanner(file);
        String[] l = sc.nextLine().split(":");

        int numOf_citiesAndStreets = Integer.parseInt(l[0]);    //number of citiesAndStreets
        int numOf_adjacents = Integer.parseInt(l[1]);       //number of adjacents

        int counter = 0;

        //collecting citiesAndStreets, and sending each line of them to the constructor of location
        int num = 0;
        while (counter < numOf_citiesAndStreets) {
            String line = sc.nextLine();

            if (!line.trim().isEmpty()) {
                System.out.println(line);

                Vertex ver = new Vertex(new Location(line), num++);
                citiesAndStreets.add(ver);
                counter++;
            }

        }

        //collecting and assigning the adjacents of every element of citiesAndStreets(every location)
        counter = 0;
        while (counter < numOf_adjacents) {
            String line = sc.nextLine();

            if (!line.trim().isEmpty()) {

                String[] splitedLine = line.split(":");

                String adj1 = splitedLine[0];
                String adj2 = splitedLine[1];


                for (Vertex first : citiesAndStreets) {
                    if (first.getLocation().getName().equals(adj1)) {

                        for (Vertex second : citiesAndStreets) {
                            if (second.getLocation().getName().equals(adj2)) {
                                assignAdjacent(first, second);
                            }
                        }


                    }
                }
                counter++;
            }


        }
        sc.close();

    }

    private static void assignAdjacent(Vertex first, Vertex second) {
        double distance1 = haversine(first, second);
        first.adjacentsList.add(new Edge(first, second, distance1));
    }

    private static double haversine(Vertex vertex1, Vertex vertex2) {

        //first Point
        double lat1 = vertex1.getLocation().getLatitude();
        double lon1 = vertex1.getLocation().getLongitude();

        //second point
        double lat2 = vertex2.getLocation().getLatitude();
        double lon2 = vertex2.getLocation().getLongitude();


        double R = 6371.0;  //earth radius in kilometers

        //convert coordinates from degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;

        return distance;

    }

}
