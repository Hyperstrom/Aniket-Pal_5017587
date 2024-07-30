public class BuilderPatternExample {

    // Product Class
    public static class Computer {
        // Attributes of the Computer
        private String CPU;
        private String RAM;
        private String storage;
        private String GPU;
        private String motherboard;

        // Private constructor to prevent direct instantiation
        private Computer(Builder builder) {
            this.CPU = builder.CPU;
            this.RAM = builder.RAM;
            this.storage = builder.storage;
            this.GPU = builder.GPU;
            this.motherboard = builder.motherboard;
        }

        // Static nested Builder class
        public static class Builder {
            private String CPU;
            private String RAM;
            private String storage;
            private String GPU;
            private String motherboard;

            // Methods to set each attribute
            public Builder setCPU(String CPU) {
                this.CPU = CPU;
                return this;
            }

            public Builder setRAM(String RAM) {
                this.RAM = RAM;
                return this;
            }

            public Builder setStorage(String storage) {
                this.storage = storage;
                return this;
            }

            public Builder setGPU(String GPU) {
                this.GPU = GPU;
                return this;
            }

            public Builder setMotherboard(String motherboard) {
                this.motherboard = motherboard;
                return this;
            }

            // Build method to create an instance of Computer
            public Computer build() {
                return new Computer(this);
            }
        }

        @Override
        public String toString() {
            return "Computer{" +
                    "CPU='" + CPU + '\n' +
                    "RAM='" + RAM + '\n' +
                    "storage='" + storage + '\n' +
                    "GPU='" + GPU + '\n' +
                    "motherboard='" + motherboard + '\n' +
                    '}';
        }
    }

    // Test class to demonstrate the Builder Pattern
    public static void main(String[] args) {
        // Creating different configurations of Computer using the Builder pattern

        Computer gamingComputer = new Computer.Builder()
                .setCPU("Intel i9")
                .setRAM("32GB")
                .setStorage("1TB SSD")
                .setGPU("NVIDIA RTX 3080")
                .setMotherboard("ASUS ROG")
                .build();

        Computer officeComputer = new Computer.Builder()
                .setCPU("Intel i5")
                .setRAM("16GB")
                .setStorage("512GB SSD")
                .setGPU("Integrated Graphics")
                .setMotherboard("MSI B450")
                .build();

        Computer budgetComputer = new Computer.Builder()
                .setCPU("AMD Ryzen 3")
                .setRAM("8GB")
                .setStorage("256GB SSD")
                .build();

        System.out.println("Gaming Computer: \n" + gamingComputer);
        System.out.println("Office Computer: \n" + officeComputer);
        System.out.println("Budget Computer: \n" + budgetComputer);
    }
}
