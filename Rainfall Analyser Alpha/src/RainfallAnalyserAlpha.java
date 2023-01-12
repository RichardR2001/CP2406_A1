import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.util.Objects;

// Set class RainfallAnalyserAlpha as public so that other classes can also access the field and method
public class RainfallAnalyserAlpha {
    public static void main(String[] args) throws IOException {
        // all variables are set to double as they may contain decimals.
        double[] monthlyTotal = new double[12];
        double[] dailyMin = new double[12];
        double[] dailyMax = new double[12];
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        try {
            // Read the file DataCopperlodeDam.csv for trial run
            Reader in = new FileReader("Rainfall Analyser Alpha/src/DataCopperlodeDam.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
            for (CSVRecord record : records) {
                String month = record.get("Month");
                String rainfall = record.get("Rainfall amount (millimetres)");
                if (Objects.equals(rainfall, "")) {
                } else {
                    // Parse and process data.
                    double rainfallNum = Double.parseDouble(rainfall);
                    int monthNum = Integer.parseInt(month);
                    monthlyTotal[monthNum - 1] += rainfallNum;
                    if (rainfallNum < dailyMin[monthNum - 1]) dailyMin[monthNum - 1] = rainfallNum;
                    if (rainfallNum > dailyMax[monthNum - 1]) dailyMax[monthNum - 1] = rainfallNum;
                }
            }
        }
        // list down possible errors and the print the error
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        catch (NumberFormatException e) {
            System.out.println("Incorrect Data Format.");
        }
        catch (Exception e) {
            System.out.println("Unknown Error.");
        }
        // Print and write to file.
        FileWriter myWriter = new FileWriter("data.txt");
        System.out.println("Welcome to RainfallAnalyser Alpha-Release.");
        System.out.println("Analysing Data...");
        System.out.println("-------------------------------------------------------------------------------");
        myWriter.write("month,total,min,max\n");
        for (int i = 0; i < 12; i++) {
            System.out.printf(monthName[i] + ": Total rainfall = " + "%.2f", monthlyTotal[i]);
            System.out.printf(", Min rainfall = " + "%.2f", dailyMin[i]);
            System.out.printf(", Max rainfall = " + "%.2f", dailyMax[i]);
            System.out.println();
            myWriter.write(monthName[i] + "," + String.format("%.2f", monthlyTotal[i]) + "," + String.format("%.2f", dailyMin[i]) + "," + String.format("%.2f", dailyMax[i]) + "\n");
        }
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Analysing Complete.");
        myWriter.close();
    }
}
