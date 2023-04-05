import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WeatherPredictionApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField dateInput = new TextField();
        Label predictionLabel = new Label("Your weather prediction will appear here.");
        Button submitButton = new Button("Get Prediction");

        WeatherPredictor weatherPredictor = new WeatherPredictor();

        submitButton.setOnAction(e -> {
            String inputDate = dateInput.getText();
            double prediction = weatherPredictor.predictTemperature(inputDate);
            String[] phrases = {"Wear a jacket!", "Go to the beach!", "Bring an umbrella."};
            int index = (int) (Math.random() * phrases.length);
            predictionLabel.setText(phrases[index]);
        });

        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundImage(new Image("background.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        root.setPadding(new Insets(15));

        Label heading = new Label("Manrav's Future Weather Predictor. Trained For: Vancouver");
        heading.setTextFill(Color.WHITE);
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        root.getChildren().add(heading);

        VBox content = new VBox(10, new Label("Enter a date (dd/mm/yyyy):"), dateInput, submitButton, predictionLabel);
        content.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px;");
        root.getChildren().add(content);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manrav's Future Weather Predictor Vancouver");
        primaryStage.show();
    }
}
