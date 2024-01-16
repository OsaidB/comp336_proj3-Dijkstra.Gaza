package com.example.comp336_3;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimatedZoomOperator {
    private Timeline timeline;
    private static final double MIN_SCALE = 1;


    public AnimatedZoomOperator() {
        this.timeline = new Timeline(60);
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

    private void resetPane(Pane pane) {
        // Reset the pane to its original state
        pane.setTranslateX(0);
        pane.setTranslateY(0);
        pane.setScaleX(1.0);
        pane.setScaleY(1.0);
    }

}
