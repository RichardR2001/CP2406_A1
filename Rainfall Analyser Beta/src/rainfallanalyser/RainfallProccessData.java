package rainfallanalyser;

public class RainfallProccessData {
    double[] monthlyTotal = new double[12];
    double[] dailyMin = new double[12];
    double[] dailyMax = new double[12];

    public void calcMonthlyTotal(String rainfall, String month) {
        double rainfallNum = Double.parseDouble(rainfall);
        int monthNum = Integer.parseInt(month);
        monthlyTotal[monthNum - 1] += rainfallNum;
    }

    public void calcDailyMin(String rainfall, String month) {
        double rainfallNum = Double.parseDouble(rainfall);
        int monthNum = Integer.parseInt(month);
        if (rainfallNum < dailyMin[monthNum - 1])
            dailyMin[monthNum - 1] = rainfallNum;
    }

    public void calcDailyMax(String rainfall, String month) {
        double rainfallNum = Double.parseDouble(rainfall);
        int monthNum = Integer.parseInt(month);
        if (rainfallNum > dailyMax[monthNum - 1])
            dailyMax[monthNum - 1] = rainfallNum;
    }

    public double getMonthlyTotal(int monthNum) {
        return monthlyTotal[monthNum];
    }

    public double getDailyMin(int monthNum) {
        return dailyMin[monthNum];
    }

    public double getDailyMax(int monthNum) {
        return dailyMax[monthNum];
    }
}
