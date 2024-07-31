 
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private List<Double> values;
    private List<Double> growthRates;

    public DataStorage() {
        this.values = new ArrayList<>();
        this.growthRates = new ArrayList<>();
    }

    public void addData(double value, double growthRate) {
        values.add(value);
        growthRates.add(growthRate);
        System.out.println("Data added successfully.");
    }

    public void displayData() {
        if (values.isEmpty()) {
            System.out.println("No data stored.");
            return;
        }
        System.out.println("Stored data:");
        displayDataRecursive(0);
    }

    private void displayDataRecursive(int index) {
        if (index >= values.size()) {
            return;
        }
        System.out.printf("Entry %d: Value = %.2f, Growth Rate = %.2f%n", 
                          index + 1, values.get(index), growthRates.get(index));
        displayDataRecursive(index + 1);
    }

    public boolean hasData() {
        return !values.isEmpty();
    }

    public double getLatestValue() {
        return values.get(values.size() - 1);
    }

    public double getLatestGrowthRate() {
        return growthRates.get(growthRates.size() - 1);
    }
}