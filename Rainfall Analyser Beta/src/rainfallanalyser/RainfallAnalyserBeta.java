package rainfallanalyser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.util.Objects;

public class RainfallAnalyserBeta {
    public static void main(String[] args) throws IOException {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        // File DataCopperlodeDam (to be exported as DataCopperlodeDam.txt).
        CompileData csvTempFile = new CompileData();
        CSVReadData(csvTempFile, "Rainfall Analyser Beta/src/rainfallanalyser/DataCopperlodeDam.csv");
        CSVPrintData(csvTempFile, monthName);
        SaveDataToFile csv = new SaveDataToFile();
        csv.saveFile(csvTempFile, "DataCopperlodeDam.txt", monthName);
        // File DataKurandaRailway (to be exported as DataKurandaRailway.txt).
        CompileData csvTempFile2 = new CompileData();
        CSVReadData(csvTempFile2, "Rainfall Analyser Beta/src/rainfallanalyser/DataKurandaRailway.csv");
        CSVPrintData(csvTempFile2, monthName);
        csv.saveFile(csvTempFile2, "DataKurandaRailway.txt", monthName);
        // File DataMountSheridanStationCNS (to be exported as DataMountSheridanStationCNS.txt).
        CompileData csvTempFile3 = new CompileData();
        CSVReadData(csvTempFile3, "Rainfall Analyser Beta/src/rainfallanalyser/DataMountSheridanStationCNS.csv");
        CSVPrintData(csvTempFile3, monthName);
        csv.saveFile(csvTempFile3, "DataMountSheridanStationCNS.txt", monthName);
    }

    private static void CSVReadData(CompileData csvTempFile, String fileName) {
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
            // Read data.
            for (CSVRecord record : records) {
                String month = record.get("Month");
                String rainfall = record.get("Rainfall amount (millimetres)");
                if (Objects.equals(rainfall, "")) {
                } else {
                    // Parse and process data.
                    csvTempFile.calcMonthlyTotal(rainfall, month);
                    csvTempFile.calcDailyMin(rainfall, month);
                    csvTempFile.calcDailyMax(rainfall, month);
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
    }
    // Print and write to file.
    private static void CSVPrintData(CompileData csvTempFile, String[] month) {
        System.out.println("Welcome to RainfallAnalyser Alpha-Release.");
        System.out.println("Compiling data from CSV file...");
        System.out.println("*****************************************************************************************");
        for (int i = 0; i < 12; i++) {
            double monthlyTotal = csvTempFile.getMonthlyTotal(i);
            double dailyMin = csvTempFile.getDailyMin(i);
            double dailyMax = csvTempFile.getDailyMax(i);
            System.out.printf(month[i] + ": Total rainfall = " + "%.2f", monthlyTotal);
            System.out.printf(", Min rainfall = " + "%.2f", dailyMin);
            System.out.printf(", Max rainfall = " + "%.2f", dailyMax);
            System.out.println();
        }
        System.out.println("*****************************************************************************************");
        System.out.println("Congratulations, compiling has been completed!");
    }
}