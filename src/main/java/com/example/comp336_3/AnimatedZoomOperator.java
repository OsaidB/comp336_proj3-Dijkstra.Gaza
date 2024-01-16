package com.example.comp336_3;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimatedZoomOperator {
    private Timeline timeline;
    private double lastX, lastY;
    private static final double MIN_SCALE = 1;

    public AnimatedZoomOperator() {
        this.timeline = new Timeline(60);
    }

    public void zoomOrg(Node node, double factor, double x, double y) {
        // determine scale
        double oldScale = node.getScaleX();
        double scale = oldScale * factor;
        double f = (scale / oldScale) - 1;

        // determine offset that we will have to move the node
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));

        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f * dx)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f * dy)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
        );
        timeline.play();
    }
    public void zoom(Pane pane, double factor, double x, double y) {
        // Method to perform animated zoom on the specified Node

        // determine the current scale of the node
        double oldScale = pane.getScaleX();

        // determine the new scale based on the provided factor
        double scale = oldScale * factor;

        // Limit the minimum scale to avoid zooming out too much
        if (scale < MIN_SCALE) {
            scale = MIN_SCALE;
            resetPane(pane);
        }

        // calculate the offset that we will have to move the node
        Bounds bounds = pane.localToScene(pane.getBoundsInLocal());
        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));

        // configure the timeline with KeyFrames for translating and scaling the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(pane.translateXProperty(), pane.getTranslateX() - (scale / oldScale - 1) * dx)),
                new KeyFrame(Duration.millis(200), new KeyValue(pane.translateYProperty(), pane.getTranslateY() - (scale / oldScale - 1) * dy)),
                new KeyFrame(Duration.millis(200), new KeyValue(pane.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(200), new KeyValue(pane.scaleYProperty(), scale))
        );

        // play the timeline for animation
        timeline.play();
    }

//    public void zoom(Pane pane, double factor, double x, double y) {
//        // Determine the current scale of the pane
//        double oldScaleX = pane.getScaleX();
//        double oldScaleY = pane.getScaleY();
//
//        // Determine the new scale based on the provided factor
//        double scaleX = oldScaleX * factor;
//        double scaleY = oldScaleY * factor;
//
//        // Limit the minimum scale to avoid zooming out too much
//        if (scaleX < MIN_SCALE || scaleY < MIN_SCALE) {
//            scaleX = Math.max(scaleX, MIN_SCALE);
//            scaleY = Math.max(scaleY, MIN_SCALE);
//        }
//
//        // Calculate the offset that we will have to move the pane
//        Bounds bounds = pane.localToScene(pane.getBoundsInLocal());
//        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
//        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));
//
//        // Configure the timeline with KeyFrames for translating and scaling the pane
//        timeline.getKeyFrames().clear();
//        timeline.getKeyFrames().addAll(
//                new KeyFrame(Duration.millis(200), new KeyValue(pane.translateXProperty(), pane.getTranslateX() - (scaleX / oldScaleX - 1) * dx)),
//                new KeyFrame(Duration.millis(200), new KeyValue(pane.translateYProperty(), pane.getTranslateY() - (scaleY / oldScaleY - 1) * dy)),
//                new KeyFrame(Duration.millis(200), new KeyValue(pane.scaleXProperty(), scaleX)),
//                new KeyFrame(Duration.millis(200), new KeyValue(pane.scaleYProperty(), scaleY))
//        );
//
//        // Play the timeline for animation
//        timeline.play();
//    }

    private void resetPane(Pane pane) {
        // Reset the pane to its original state
        pane.setTranslateX(0);
        pane.setTranslateY(0);
        pane.setScaleX(1.0);
        pane.setScaleY(1.0);
    }

    public void handleMousePressed(MouseEvent event) {
        // Record the initial mouse coordinates when pressed
        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }

    public void handleMouseDragged(MouseEvent event, Pane pane) {
        // Calculate the delta between current and last mouse coordinates
        double deltaX = event.getSceneX() - lastX;
        double deltaY = event.getSceneY() - lastY;

        // Adjust the pane's position based on the drag
        pane.setLayoutX(pane.getLayoutX() + deltaX);
        pane.setLayoutY(pane.getLayoutY() + deltaY);

        // Update the last mouse coordinates
        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }
}
