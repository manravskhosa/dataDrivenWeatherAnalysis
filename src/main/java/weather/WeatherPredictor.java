package weather;
/*
 * Manrav Singh Khosa
 * Personal Project
 * Last Updated: Apr 2023
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeatherPredictor {

    private static final String DATA_FILE = "vancouver_weather_data.csv";
    private List<Double> temperatures;

    public WeatherPredictor() throws IOException {
        temperatures = loadData();
    }

    public double predictTemperature(String inputDate) {
        LocalDate futureDate = LocalDate.parse(inputDate);
        return predictTemperature(temperatures, futureDate);
    }

    private List<Double> loadData() throws IOException {
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

    private double predictTemperature(List<Double> temperatures, LocalDate futureDate) {
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
