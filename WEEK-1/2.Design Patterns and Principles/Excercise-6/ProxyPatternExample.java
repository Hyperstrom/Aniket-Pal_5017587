import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

// Interface: Image.java
interface Image {
    void display();
}

// Real Subject Class: RealImage.java
class RealImage implements Image {
    private String filePath;
    private java.awt.Image image;

    public RealImage(String filePath) {
        this.filePath = filePath;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading " + filePath + " from disk...");
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void display() {
        if (image != null) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle(filePath);
            frame.setSize(800, 600);
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);
            frame.setVisible(true);
        } else {
            System.err.println("Cannot display image: Image not loaded.");
        }
    }
}

// Proxy Class: ProxyImage.java
class ProxyImage implements Image {
    private String filePath;
    private RealImage realImage;

    public ProxyImage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filePath);
        }
        realImage.display();
    }
}

// Test Class: ProxyPatternExample.java
public class ProxyPatternExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the path of the first image:");
        String imagePath1 = scanner.nextLine();

        System.out.println("Enter the path of the second image:");
        String imagePath2 = scanner.nextLine();

        Image image1 = new ProxyImage(imagePath1);
        Image image2 = new ProxyImage(imagePath2);

        // Image will be loaded from disk and displayed
        image1.display();

        // Image will be displayed directly from cache
        image1.display();

        // Image will be loaded from disk and displayed
        image2.display();

        // Image will be displayed directly from cache
        image2.display();
        
        scanner.close();
    }
}
