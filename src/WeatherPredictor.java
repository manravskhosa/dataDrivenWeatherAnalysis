/**
  * This program predicts the temperature in Vancouver, B.C. on a future date using a simplified linear regression model.
  * It prompts the user to enter a future date and predicts the temperature using historical weather data from a CSV file.
  * Author: Manrav Singh Khosa, Mar 2023
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeatherPredictor {

    private static final String DATA_FILE = "vancouver_weather_data.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the future day (1-31): ");
        int day = scanner.nextInt();
        System.out.print("Enter the future month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter the future year (e.g. 2024): ");
        int year = scanner.nextInt();

        LocalDate futureDate = LocalDate.of(year, month, day);

        try {
            List<Double> temperatures = loadData();
            double predictedTemperature = predictTemperature(temperatures, futureDate);
            System.out.printf("Predicted temperature in Vancouver, B.C. on %s is %.2f°C%n", futureDate, predictedTemperature);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private static List<Double> loadData() throws IOException {
        List<Double> temperatures = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    double temperature = Double.parseDouble(parts[1]);
                    temperatures.add(temperature);
                }
            }
        }
        return temperatures;
    }

    private static double predictTemperature(List<Double> temperatures, LocalDate futureDate) {
        // Linear Regression Model
        int totalDays = temperatures.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        for (int i = 0; i < totalDays; i++) {
            double x = i;
            double y = temperatures.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }
        double slope = (totalDays * sumXY - sumX * sumY) / (totalDays * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / totalDays;

        int futureDayIndex = (int) ((futureDate.toEpochDay() - LocalDate.now().toEpochDay()) + totalDays);
        return intercept + slope * futureDayIndex;
    }
}
