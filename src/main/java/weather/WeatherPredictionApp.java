package weather;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WeatherPredictionApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField dateInput = new TextField();
        Label predictionLabel = new Label("Prediction will appear here");
        Button submitButton = new Button("Predict");

        WeatherPredictor weatherPredictor = new WeatherPredictor();

        submitButton.setOnAction(e -> {
            String inputDate = dateInput.getText();
            double prediction = weatherPredictor.predictTemperature(inputDate);
            predictionLabel.setText("Predicted weather: " + String.format("%.2f°C", prediction));
        });

        VBox root = new VBox(10, new Label("Enter a date (yyyy-mm-dd):"), dateInput, submitButton, predictionLabel);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Weather Prediction");
        primaryStage.show();
    }
}

