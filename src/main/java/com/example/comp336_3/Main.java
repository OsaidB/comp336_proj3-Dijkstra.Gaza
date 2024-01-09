package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {
	public static File file;
	static ComboBox<String> scourseText = new ComboBox<String>();
	static ComboBox<String> targetText = new ComboBox<String>();
	static ToggleButton click = new ToggleButton("Click in map");
	static ToggleButton combo = new ToggleButton("Combo Box");
	static int numOfPointChoice = 0;
	static Pane pane2 = new Pane();
	private Alert error = new Alert(AlertType.ERROR);

	ArrayList<PathTable> tableData = new ArrayList<PathTable>();
	ObservableList<PathTable> data = FXCollections.observableArrayList(tableData);
	static ArrayList<Vertex> Colleges = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("BZU MAP");

		Image m = new Image("G:\\My Drive\\UNIVERSITY\\3rd year\\2nd Semester\\COMP336\\Projects\\P3\\BZU.jpg");
		ImageView image = new ImageView(m);
		image.setFitHeight(536);
		image.setFitWidth(955);
		pane2.getChildren().add(image);

		primaryStage.getIcons()
				.add(new Image("G:\\My Drive\\UNIVERSITY\\3rd year\\2nd Semester\\COMP336\\Projects\\P3\\DAlgo.png"));
		Label title = new Label("BZU Map");
//		title.setStyle("-fx-background-color: gray");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 0));
		title.setPadding(new Insets(15));
		file = new File("INFO2.txt");
		readFile(file);

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10));
		pane.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		Label choose = new Label("Choose College by :");
		choose.setPadding(new Insets(15));
		ToggleGroup tg = new ToggleGroup();

		click.setToggleGroup(tg);
		combo.setToggleGroup(tg);
		icons(click);
		icons(combo);

		click.setOnAction(e -> {
			click.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + " -fx-text-fill: #ff6800;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
			combo.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n"
					+ "-fx-background-color: #f6f6f6;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");

		});

		combo.setOnAction(e -> {
			combo.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + " -fx-text-fill: #ff6800;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
			click.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n"
					+ "-fx-background-color: #f6f6f6;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");

		});

		scourseText.setOnAction(e -> {
			if (combo.isSelected()) {
				for (int i = 0; i < Colleges.size(); i++) {
					if (Colleges.get(i).getCollege().getName()
							.equals(scourseText.getSelectionModel().getSelectedItem())) {
						ImageView vi0 = new ImageView(new Image(
								"G:\\My Drive\\UNIVERSITY\\3rd year\\2nd Semester\\COMP336\\Projects\\P3\\location-pin.png"));
						vi0.setFitHeight(16);
						vi0.setFitWidth(16);
						Colleges.get(i).getCollege().getRadioButton().setGraphic(vi0);
						Colleges.get(i).getCollege().getRadioButton().setSelected(true);
						numOfPointChoice += 1;
						if (numOfPointChoice == 2) {
							lock();
						}
						break;
					}
				}
			}
		});

		targetText.setOnAction(e -> {
			if (combo.isSelected()) {
				for (int i = 0; i < Colleges.size(); i++) {
					if (Colleges.get(i).getCollege().getName()
							.equals(targetText.getSelectionModel().getSelectedItem())) {
						ImageView vi0 = new ImageView(new Image(
								"G:\\My Drive\\UNIVERSITY\\3rd year\\2nd Semester\\COMP336\\Projects\\P3\\location-pin (2).png"));
						vi0.setFitHeight(16);
						vi0.setFitWidth(16);
						Colleges.get(i).getCollege().getRadioButton().setGraphic(vi0);
						Colleges.get(i).getCollege().getRadioButton().setSelected(true);
						numOfPointChoice += 1;
						if (numOfPointChoice == 2) {
							lock();
						}
						break;
					}
				}
			}
		});

		HBox hx = new HBox(10, click, combo);
		hx.setAlignment(Pos.CENTER);
		hx.setPadding(new Insets(3));

		IconedTextFieled(choose, hx);
		HBox h = new HBox(choose, hx);
		h.setAlignment(Pos.CENTER);

		Label scourse = new Label("Sourse :");
		scourse.setPadding(new Insets(7));
		scourseText.setMinWidth(150);
		for (int i = 0; i < Colleges.size(); i++) {
			scourseText.getItems().add(Colleges.get(i).getCollege().getName());
		}

		IconedTextFieled(scourse, scourseText);
		HBox h1 = new HBox(scourse, scourseText);
		h1.setAlignment(Pos.CENTER);

		Label target = new Label("Target :");
		target.setPadding(new Insets(7));
		for (int i = 0; i < Colleges.size(); i++) {
			targetText.getItems().add(Colleges.get(i).getCollege().getName());
		}
		targetText.setMinWidth(150);
		IconedTextFieled(target, targetText);

		HBox h2 = new HBox(target, targetText);
		h2.setAlignment(Pos.CENTER);

		Button run = new Button("Go ahead");
		Button reset = new Button("Reset");

		HBox butBox = new HBox(20, run, reset);
		butBox.setAlignment(Pos.CENTER);
		icons(reset);
		icons(run);
		butoonEffect(reset);
		butoonEffect(run);

		Label path = new Label("Shortest Path :");
		path.setPadding(new Insets(7));
		// path.setMinHeight(200);
		path.setPadding(new Insets(7));
//		TextField t = new TextField();
		TextArea t = new TextArea();
		t.setPrefWidth(300);
		t.setPrefHeight(300);

//		TableView<PathTable> pathText = new TableView<PathTable>();
//
//		TableColumn<PathTable, String> scource = new TableColumn<PathTable, String>("Source");
//		scource.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSource()));
//		scource.setMaxWidth(80);
//		TableColumn<PathTable, String> Target = new TableColumn<PathTable, String>("Target");
//		Target.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTarget()));
//		Target.setMaxWidth(80);
//		TableColumn<PathTable, Double> Distance = new TableColumn<PathTable, Double>("Distance");
//		Distance.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getDistance()).asObject());

//		Distance.setMaxWidth(90);
//
//		pathText.getColumns().addAll(scource, Target, Distance);
//
//		pathText.setMaxSize(250, 200);
//		pathText.setItems(data);

		IconedTextFieled(path, new Node() {
		});
		IconedTextFieled(path, t);
		HBox h3 = new HBox(path, t);
		h3.setAlignment(Pos.CENTER);

		Label distance = new Label("Distance :");
		distance.setPadding(new Insets(7));
		TextField distanceText = new TextField();

		IconedTextFieled(distance, distanceText);
		HBox h4 = new HBox(distance, distanceText);
		h4.setAlignment(Pos.CENTER);

		VBox v = new VBox(30, h, h1, h2, butBox);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(10));
		icons(v);

		VBox v1 = new VBox(30, h3, h4);
		v1.setAlignment(Pos.CENTER);
		v1.setPadding(new Insets(10));
		icons(v1);

		VBox mix = new VBox(10, v, v1);
		mix.setAlignment(Pos.CENTER);

		VBox Vmap = new VBox(pane2);
		Vmap.setAlignment(Pos.CENTER);

		HBox mainBox = new HBox(20, Vmap, mix);
		mainBox.setAlignment(Pos.CENTER);

		pane.setCenter(mainBox);

		run.setOnAction(e -> {
			Vertex vertx1 = null;
			Vertex vertx2 = null;
			String s1 = scourseText.getValue();
			System.out.println(s1);
			String s2 = targetText.getValue();
			System.out.println(s2);

			for (int i = 0; i < Colleges.size(); i++) {
				if (Colleges.get(i).getCollege().getName().equals(s1)) {
					vertx1 = Colleges.get(i);
				}
				if (Colleges.get(i).getCollege().getName().equals(s2)) {
					vertx2 = Colleges.get(i);
				}
			}

			if (vertx1 != null && vertx2 != null) {
				int i = drowLine(Dijekstra(vertx1, vertx2));
				if (i == 0)
					distanceText.setText("0");
				else if (i == 1)
					distanceText.setText(String.valueOf(vertx2.distance));
				data = FXCollections.observableArrayList(tableData);
				t.setText("" + data);

			}

		});

		reset.setOnAction(l -> {
			pane2.getChildren().clear();
			targetText.getSelectionModel().select(null);
			scourseText.getSelectionModel().select(null);
			distanceText.setText("");
			data.clear();
			tableData.clear();
			numOfPointChoice = 0;

			pane2.getChildren().add(image);
			Image Image = new Image(
					"G:\\My Drive\\UNIVERSITY\\3rd year\\2nd Semester\\COMP336\\Projects\\P3\\location-pin (1).png");
			for (Vertex cou : Colleges) {
				ImageView vi = new ImageView(Image);
				vi.setFitHeight(17);
				vi.setFitWidth(16);
				cou.getCollege().getRadioButton().setGraphic(vi);
				cou.getCollege().getRadioButton().setSelected(false);
				t.clear();
				free();

			}

			for (int i = 0; i < Colleges.size(); i++) {
				Colleges.get(i).visited = false;
				Colleges.get(i).previous = null;
			}

			addPoint();

		});

		addPoint();

		Scene scene = new Scene(pane, 1535, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		pane.setStyle("-fx-background-color: black");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private int drowLine(Vertex Destination) {
		if (Destination == null) {
			error.setContentText("No path");
			error.show();
			return 0;
		} else {
			List<Vertex> p = new ArrayList<>();
			for (Vertex v = Destination; v != null; v = v.previous) {
				System.out.print("-->" + v.college.getName() + " ");
				p.add(v);
			}
			System.out.println();
			// V
			Collections.reverse(p);
			if (p.size() >= 1) {
				for (int i = 1; i < p.size(); i++) {
					double d = Destination.getDistance();
					// double d = Distance(p.get(i - 1), p.get(i));
					tableData.add(
							new PathTable(d, p.get(i - 1).getCollege().getName(), p.get(i).getCollege().getName()));
				}

			}
			if (p.size() <= 1) {
				error.setContentText("No path");
				error.show();
			}

			for (int i = 0; i < p.size() - 1; i++) {
				Vertex u = p.get(i);
				Vertex v = p.get(i + 1);

				if (i != 0 && i != p.size() - 1) {
					ImageView vi0 = new ImageView(new Image(
							"G:\\My Drive\\UNIVERSITY\\3rd year\\2nd Semester\\COMP336\\Projects\\P3\\location-pin (4).png"));
					vi0.setFitHeight(16);
					vi0.setFitWidth(16);
					u.getCollege().getRadioButton().setGraphic(vi0);
				}

				Line line = new Line(u.college.getLatitude(), u.college.getLongitude(), v.college.getLatitude(),
						v.college.getLongitude());
				pane2.getChildren().add(line);
			}
		}
		return 1;

	}

	private void addPoint() {
		for (int i = 0; i < Colleges.size(); i++) {
			RadioButton r = Colleges.get(i).getCollege().getRadioButton();
			r.setLayoutX(Colleges.get(i).getCollege().getLatitude());
			r.setLayoutY(Colleges.get(i).getCollege().getLongitude());
			pane2.getChildren().add(r);
		}

	}

	public static void main(String[] args) {

		launch(args);
	}

	private void IconedTextFieled(javafx.scene.Node l, javafx.scene.Node t) {
		l.setStyle("-fx-border-color: #d8d9e0;" + "-fx-font-size: 14;\n" + "-fx-border-width: 1;"
				+ "-fx-border-radius: 50;" + "-fx-font-weight: Bold;\n" + "-fx-background-color:#d8d9e0;"
				+ "-fx-background-radius: 50 0 0 50");

		t.setStyle("-fx-border-radius: 0 50 50 0;\n" + "-fx-font-size: 14;\n" + "-fx-font-family: Times New Roman;\n"
				+ "-fx-font-weight: Bold;\n" + "-fx-background-color: #f6f6f6;\n" + "-fx-border-color: #d8d9e0;\n"
				+ "-fx-border-width:  3.5;" + "-fx-text-fill: #ff6800;" + "-fx-background-radius: 0 50 50 0");
	}

	private void icons(Node l) {
		l.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n" + "-fx-font-family: Times New Roman;\n"
				+ "-fx-font-weight: Bold;\n" + "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
				+ "-fx-border-width:  3.5;" + "-fx-background-color: #f6f6f6;\n"
				+ "-fx-background-radius: 25 25 25 25");
	}

	private void butoonEffect(Node b) {
		b.setOnMouseMoved(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + " -fx-text-fill: #ff6800;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});

		b.setOnMouseExited(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 14;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n"
					+ "-fx-background-color: #f6f6f6;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});
	}

	public static void lock() {
		try {
			for (int i = 0; i < Colleges.size(); i++) {
				Colleges.get(i).getCollege().getRadioButton().setDisable(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void free() {
		try {
			for (int i = 0; i < Colleges.size(); i++) {
				Colleges.get(i).getCollege().getRadioButton().setDisable(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
//	public Vertex shortestPath(Vertex Source, Vertex Destination) {
//        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(numVertices, Comparator.comparingInt(node -> node.distance));
//        int[] distances = new int[numVertices];
//        int[] previous = new int[numVertices];
//        Arrays.fill(distances, Integer.MAX_VALUE);
//        Arrays.fill(previous, -1);
//
//        distances[Source] = 0;
//        priorityQueue.offer(new Node(Source, 0));
//
//        while (!priorityQueue.isEmpty()) {
//            int vertex = priorityQueue.poll().vertex;
//
//            if (vertex == Destination) {
//                break;
//            }
//
//            for (Edge edge : adjacencyList.get(vertex)) {
//                int nextVertex = edge.destination;
//                int weight = edge.weight;
//                int newDistance = distances[vertex] + weight;
//
//                if (newDistance < distances[nextVertex]) {
//                    distances[nextVertex] = newDistance;
//                    previous[nextVertex] = vertex;
//                    priorityQueue.offer(new Node(nextVertex, newDistance));
//                }
//            }
//        }
//
//        return Destination;
//    }

	
	
//	 public void dijkstra(College from) {
//	        isDone = false;
//	        HashMap<Country, LinkedList<Node>> graph = Main.getGraph();
//
//	        fillTable(graph.keySet(), from);
//
//	        while (!queue.isEmpty()) {
//	            Table t = queue.remove();
//
//	            t.setKnown(true);
//
//	            LinkedList<Node> list = graph.get(t.getHeader());
//	            for (Node node : list) {
//	                int j = indexOf(node.country());
//	                if (table[j].notKnown()) {
//	                    if (t.getDistance() + node.cost() < table[j].getDistance()) {
//	                        table[j].setDistance(t.getDistance() + node.cost());
//	                        table[j].setPrev(indexOf(t.getHeader()));
//	                        queue.add(table[j]);
//	                    }
//	                }
//	            }
//
//	        }
//
//	        isDone = true;
//
//
//	    }
	public Vertex Dijekstra(Vertex Source, Vertex Destination) {// O(n) = (V(logV+E))
		Source.distance = 0;
		if (Source == Destination) {
			return null;
		}

		PriorityQueue<Vertex> pq = new PriorityQueue<>(new Comparator<Vertex>() { // Log V
			@Override
			public int compare(Vertex v1, Vertex v2) {
				return Double.compare(v1.distance, v2.distance);
			}
		});

		pq.add(Source);

		while (!pq.isEmpty()) { // V
			Vertex u = pq.poll(); //// Log V

			u.visited = true;
			if (u.college.getName().equals(Destination.getCollege().getName())) {
				break;
			}
			for (edges e : u.getE()) { // E
				Vertex v = e.desination;
				if (!v.visited) {
					double weight = e.weight;
					double distanceThroughU = u.distance + weight;
					if (distanceThroughU < v.distance) {
						v.distance = distanceThroughU;
						v.previous = u;
						pq.add(v);
					}
				}
			}
		}

		return Destination;
	}

	public static void readFile(File file) {
		try {
			Scanner sc = new Scanner(file);
			String[] l = sc.nextLine().split(":");
			int numCounter = Integer.parseInt(l[0]);
			int numEdge = Integer.parseInt(l[1]);
			int count = 0;
			int num = 0;

			while (count < numCounter) {
				String line = sc.nextLine();
				System.out.println(line);
				Vertex ver = new Vertex(new College(line), num++);
				Colleges.add(ver);
				count++;

			}

			count = 0;
			while (count < numEdge) {
				String tokens[] = sc.nextLine().split(":");
				System.out.println(tokens[2]);
				for (int i = 0; i < Colleges.size(); i++) {
					if (Colleges.get(i).getCollege().getName().compareToIgnoreCase(tokens[0]) == 0) {
						for (int j = 0; j < Colleges.size(); j++) {

							if (Colleges.get(j).getCollege().getName().compareToIgnoreCase(tokens[1]) == 0) {
								Colleges.get(i).e.add(
										new edges(Colleges.get(i), Colleges.get(j), Double.parseDouble(tokens[2])));
							}
						}
					}
				}
				count++;
			}
			sc.close();
		} catch (FileNotFoundException t) {
			System.out.println(t);
		}
	}
}
