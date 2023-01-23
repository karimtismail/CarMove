package com.example.car;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application implements Initializable {
    // ImageView image1, image2;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        final AudioClip clip = new AudioClip(this.getClass().getResource("horn.mp3").toExternalForm());

        ImageView image1 = new ImageView(new Image(getClass().getResourceAsStream("car2.png")));
        image1.setFitHeight(50);
        image1.setFitWidth(50);
        image1.setCursor(Cursor.HAND);

        ImageView image2 = new ImageView(new Image(getClass().getResourceAsStream("car.png")));
        image2.setFitHeight(50);
        image2.setFitWidth(50);
        image2.setCursor(Cursor.HAND);

        PathElement[] path = {
                new MoveTo(200, 50),
                new HLineTo(200),
                new LineTo(300, 200),
                new HLineTo(400),
                new ArcTo(50, 50, 0, 410, 50, false, false),
                new HLineTo(310),
                new LineTo(200, 200),
                new HLineTo(100),
                new ArcTo(50, 50, 0, 100, 50, false, true),
                new ClosePath()
        };

        Path road = new Path();
        road.setStroke(Color.BLACK);
        road.setStrokeWidth(50);
        road.getElements().addAll(path);

        Path divider = new Path();
        divider.setStroke(Color.WHITE);
        divider.setStrokeWidth(4);
        divider.getStrokeDashArray().addAll(10.0, 10.0);
        divider.getElements().addAll(path);

        PathTransition pathTransition1 = new PathTransition();
        pathTransition1.setDuration(Duration.millis(9000));
        pathTransition1.setPath(road);
        pathTransition1.setNode(image1);
        pathTransition1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT); // keep the car perpendicular to its path
        pathTransition1.setInterpolator(Interpolator.LINEAR); // linear interpolator - smooth animation
        pathTransition1.setCycleCount(Timeline.INDEFINITE); // Let the animation run forever
        pathTransition1.setAutoReverse(false); // Reverse direction on alternating cycles
        pathTransition1.setRate(pathTransition1.getRate() * 1);
        pathTransition1.play();

        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.millis(9000));
        pathTransition2.setPath(road);
        pathTransition2.setNode(image2);
        pathTransition2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT); // keep the car perpendicular to its path
        pathTransition2.setInterpolator(Interpolator.LINEAR); // linear interpolator - smooth animation
        pathTransition2.setCycleCount(Timeline.INDEFINITE); // Let the animation run forever
        pathTransition2.setAutoReverse(false); // Reverse direction on alternating cycles
        pathTransition2.setRate(pathTransition2.getRate() * -1);
        pathTransition2.play();

        image1.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                // pathTransition1.pause();
                pathTransition1.setRate(pathTransition1.getRate() * -1);
                clip.play();
            }
        });

        image2.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                // pathTransition2.pause();
                pathTransition2.setRate(pathTransition2.getRate() * -1);
                clip.play();
            }
        });
       
        if (image1.getBoundsInParent().intersects(image2.getBoundsInParent())) {
            System.out.println("inter");
        }

        Group root = new Group();
        root.getChildren().addAll(road, divider, image1, image2);
        root.setLayoutX(100);
        root.setLayoutY(50);

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Car Game");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}