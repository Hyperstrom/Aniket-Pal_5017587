public class ForecastCalculator {
    public double calculateFutureValue(double initialValue, double growthRate, int years) {
        return calculateFutureValueRecursive(initialValue, growthRate, years);
    }

    private double calculateFutureValueRecursive(double currentValue, double growthRate, int yearsRemaining) {
        if (yearsRemaining == 0) {
            return currentValue;
        }
        double nextValue = currentValue * (1 + growthRate);
        return calculateFutureValueRecursive(nextValue, growthRate, yearsRemaining - 1);
    }

    public double calculateTotalGrowth(double initialValue, double finalValue) {
        return calculateTotalGrowthRecursive(initialValue, finalValue, 0);
    }

    private double calculateTotalGrowthRecursive(double currentValue, double targetValue, double totalGrowth) {
        if (Math.abs(currentValue - targetValue) < 0.01) {
            return totalGrowth;
        }
        return calculateTotalGrowthRecursive(currentValue * 1.01, targetValue, totalGrowth + 0.01);
    }
} 
