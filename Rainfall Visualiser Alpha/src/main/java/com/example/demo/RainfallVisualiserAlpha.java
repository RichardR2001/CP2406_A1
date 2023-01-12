package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import textio.TextIO;
import java.util.ArrayList;

public class RainfallVisualiserAlpha extends Application {
    String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    BarChart barChart = null;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rainfall Visualiser");
        // Setup barchart.
        setupBarChart();
        // Load data from file.
        var path = "/Users/richardreynard/IdeaProjects/CP2406_A1_14170539_RichardReynard/data.txt";
        TextIO.readFile(path);
        String[] rainfallData;
        String[] header = TextIO.getln().split(",");
        ArrayList<String> monthlyTotal = new ArrayList<>();
        ArrayList<String> monthlyMin = new ArrayList<>();
        ArrayList<String> monthlyMax = new ArrayList<>();
        // Data series 1.
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Total Rainfall");
        for (int i = 0; i < 12; i++) {
            rainfallData = TextIO.getln().split(",");
            String total = rainfallData[1];
            monthlyTotal.add(total);
            String min = rainfallData[2];
            monthlyMin.add(min);
            String max = rainfallData[3];
            monthlyMax.add(max);
            dataSeries1.getData().add(new XYChart.Data(monthName[i], Double.parseDouble(total)));;
        }
        barChart.getData().add(dataSeries1);
        // Data series 2.
        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Min Rainfall");
        for (int i = 0; i < 12; i++) {
            String min = monthlyMin.get(i);
            dataSeries2.getData().add(new XYChart.Data(monthName[i], Double.parseDouble(min)));;
        }
        barChart.getData().add(dataSeries2);
        // Data series 3.
        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.setName("Max Rainfall");
        for (int i = 0; i < 12; i++) {
            String max = monthlyMax.get(i);
            dataSeries3.getData().add(new XYChart.Data(monthName[i], Double.parseDouble(max)));;
        }
        barChart.getData().add(dataSeries3);
        VBox vbox = new VBox(barChart);
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);
        primaryStage.show();
    }

    private void setupBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Rainfall Amount");
        barChart = new BarChart(xAxis, yAxis);
    }

    public static void main(String[] args) {
        Application.launch(args);
        }
    }