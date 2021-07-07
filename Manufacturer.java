import java.io.Serializable;

/**
 * Clasa pentru definirea unui producator;
 * Un producator este reprezentat prin nume, in special, si prin numarul de
 * produse pe care acesta le detine
 */
public class Manufacturer implements Serializable {
    private String name;
    private int countProducts = 0;

    public Manufacturer() {

    }

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountProducts() {
        return countProducts;
    }

    public void setCountProducts() {
        this.countProducts++;
    }
}
