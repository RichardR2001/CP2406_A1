package com.example.demo;

import com.example.demo.rainfallanalyser.RainfallProccessData;
import com.example.demo.rainfallanalyser.FileReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class RainfallVisualiserBeta extends Application {
    String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    File selectedFile = null;
    BarChart barChart = null;
    XYChart.Series dataSeries1 = new XYChart.Series();
    XYChart.Series dataSeries2 = new XYChart.Series();
    XYChart.Series dataSeries3 = new XYChart.Series();
    RainfallProccessData csvTempFile = new RainfallProccessData();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rainfall Visualiser");
        setupBarChart();
        // File chooser.
        FileChooser fileChooser = new FileChooser();
        Button button = new Button("Select File");
        button.setOnAction(e -> {
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                barChart.setAnimated(false);
                dataSeries1.getData().clear();
                barChart.setAnimated(true);
                String filePath = selectedFile.getPath();
                loadDataFromFile(filePath);
            }
        });
        VBox vbox = new VBox(barChart, button);
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);
        primaryStage.show();
    }

    private void loadDataFromFile(String filePath) {
        FileReader.CSVReadData(csvTempFile, filePath);
        dataSeries1.setName("Total Rainfall");
        for (int i = 0; i < monthName.length; i++) {
            double monthlyTotal = csvTempFile.getMonthlyTotal(i);
            dataSeries1.getData().add(new XYChart.Data(monthName[i], monthlyTotal));
        }
        dataSeries2.setName("Min Rainfall");
        for (int i = 0; i < monthName.length; i++) {
            double dailyMin = csvTempFile.getDailyMin(i);
            dataSeries2.getData().add(new XYChart.Data(monthName[i], dailyMin));
        }
        dataSeries3.setName("Max Rainfall");
        for (int i = 0; i < monthName.length; i++) {
            double dailyMax = csvTempFile.getDailyMax(i);
            dataSeries3.getData().add(new XYChart.Data(monthName[i], dailyMax));
        }
        if (barChart.getData().size() == 0) {
            barChart.getData().add(dataSeries1);
            barChart.getData().add(dataSeries2);
            barChart.getData().add(dataSeries3);
        }
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