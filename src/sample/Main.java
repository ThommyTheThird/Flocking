package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Group root;

    private int STAGE_WIDTH = 800;
    private int STAGE_HEIGHT = 600;

    private int BIRDS = 20;
    private int BIRD_RADIUS = 100;

    private List<Bird> birds = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Group();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, STAGE_WIDTH, STAGE_HEIGHT));
        primaryStage.show();

        for(int i = 0; i < BIRDS; i++){
            Bird b = new Bird(BIRD_RADIUS);
            birds.add(b);
            b.x = Math.random()*STAGE_WIDTH;
            b.y = Math.random()*STAGE_HEIGHT;
            b.setLayoutX(b.x);
            b.setLayoutY(b.y);
            root.getChildren().add(b);
        }
        for(int b = 0; b < birds.size(); b++){
            birds.get(b).setOtherBirds(new ArrayList<>(birds));
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(Bird b : birds){
                    b.update();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}