import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ToyStore {
    private static final String FILENAME = "prize_toys.txt";

    private List<Toy> toys;
    private List<Toy> prizeToys;
    private Random random;

    public ToyStore() {
        toys = new ArrayList<>();
        prizeToys = new ArrayList<>();
        random = new Random();
    }

    public void addToy(int id, String name, int quantity, double weight) {
        Toy toy = new Toy(id, name, quantity, weight);
        toys.add(toy);
    }

    public void updateWeight(int id, double weight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    public void play() {
        for (Toy toy : toys) {
            int count = (int) (toy.getWeight() / 100.0 * 1000);
            for (int i = 0; i < count; i++) {
                prizeToys.add(toy);
            }
        }

        while (!prizeToys.isEmpty()) {
            Toy prizeToy = prizeToys.remove(random.nextInt(prizeToys.size()));
            prizeToy.setQuantity(prizeToy.getQuantity() - 1);

            try (FileWriter writer = new FileWriter(new File(FILENAME), true)) {
                writer.write(prizeToy.toString() + "\n");
                System.out.println("Congratulations! You won a " + prizeToy.getName() + "!");
            } catch (IOException e) {
                System.err.println("Error writing to file.");
            }
        }
    }

    public static void main(String[] args) {
        ToyStore store = new ToyStore();
        store.addToy(1, "Doll", 10, 30);
        store.addToy(2, "Car", 15, 20);
        store.addToy(3, "Puzzle", 20, 50);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter toy id to update weight or 0 to play: ");
            int id = scanner.nextInt();
            if (id == 0) {
                store.play();
                break;
            }
            System.out.println("Enter new weight (0-100): ");
            double weight = scanner.nextDouble();
            store.updateWeight(id, weight);
        }

        scanner.close();
    }
}

class Toy {

    private int id;
    private String name;
    private int quantity;
    private double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + weight;
    }
}
