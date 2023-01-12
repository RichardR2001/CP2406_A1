package rainfallanalyser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RainfallSaveFile {
    public void saveFile(RainfallProccessData csv, String fileName, String[] monthName) throws IOException {
        // Create new file.
        File myObj = new File(fileName);
        System.out.println("Saving File...");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName() + "\n");
            // Write to file.
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write("month,total,min,max\n");
            for (int i = 0; i < 12; i++) {
                double monthlyTotal = csv.getMonthlyTotal(i);
                double dailyMin = csv.getDailyMin(i);
                double dailyMax = csv.getDailyMax(i);
                myWriter.write(monthName[i] + "," + String.format("%.2f", monthlyTotal) + "," + String.format("%.2f", dailyMin) + "," + String.format("%.2f", dailyMax));
                myWriter.write("\n");
            }
            myWriter.close();
        } else {
            System.out.println("File already exists.\n");
        }
    }
}