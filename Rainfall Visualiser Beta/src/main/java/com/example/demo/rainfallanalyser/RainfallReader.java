package com.example.demo.rainfallanalyser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.util.Objects;

public class RainfallReader {
    public static void main(String[] args) throws IOException {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        // File 1.
        RainfallProccessData csvTempFile = new RainfallProccessData();
        CSVReadData(csvTempFile, "Rainfall Visualiser Beta/src/main/java/com/example/demo/rainfallanalyser/DataCopperlodeDam.csv");
        CSVPrintData(csvTempFile, monthName);
        RainfallSaveFile csv = new RainfallSaveFile();
        csv.saveFile(csvTempFile, "data.txt", monthName);
        // File 2.
        RainfallProccessData csvTempFile2 = new RainfallProccessData();
        CSVReadData(csvTempFile2, "Rainfall Visualiser Beta/src/main/java/com/example/demo/rainfallanalyser/DataKurandaRailway.csv");
        CSVPrintData(csvTempFile2, monthName);
        csv.saveFile(csvTempFile2, "data2.txt", monthName);
        // File 3.
        RainfallProccessData csvTempFile3 = new RainfallProccessData();
        CSVReadData(csvTempFile3, "Rainfall Visualiser Beta/src/main/java/com/example/demo/rainfallanalyser/DataMountSheridanStationCNS.csv");
        CSVPrintData(csvTempFile3, monthName);
        csv.saveFile(csvTempFile3, "data3.txt", monthName);
    }

    public static void CSVReadData(RainfallProccessData csvTempFile, String fileName) {
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
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        catch (NumberFormatException e) {
            System.out.println("Incorrect Data Format.");
        }
        catch (Exception e) {
            System.out.println("Unknown Error.");
        }
    }

    private static void CSVPrintData(RainfallProccessData csvTempFile, String[] month) {
        System.out.println("Welcome to RainfallAnalyser Beta-Release.");
        System.out.println("Analysing Data...");
        System.out.println("-------------------------------------------------------------------------------");
        for (int i = 0; i < 12; i++) {
            double monthlyTotal = csvTempFile.getMonthlyTotal(i);
            double dailyMin = csvTempFile.getDailyMin(i);
            double dailyMax = csvTempFile.getDailyMax(i);
            System.out.printf(month[i] + ": Total rainfall = " + "%.2f", monthlyTotal);
            System.out.printf(", Min rainfall = " + "%.2f", dailyMin);
            System.out.printf(", Max rainfall = " + "%.2f", dailyMax);
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Analysing Complete.");
    }
}