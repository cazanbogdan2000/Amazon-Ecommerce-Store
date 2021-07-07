import java.io.Serializable;

/**
 * Clasa care prezinta un obiect de tipul Product
 * Un obiect de tipul Product este caracterizat prin:
 * -> un ID uniq, uniqueId
 * -> un nume, name
 * -> un producator, manufacturer
 * -> un discount ce i se poate aplica, discount
 * -> un pret (ce produs e acela care nu are pret), price
 * -> cantitatea, care poate fi deopotriva 0, quantity
 */

public class Product implements Serializable {
    private String uniqueId;
    private String name;
    private Manufacturer manufacturer;
    private double price;
    private int quantity;
    Discount discount;

    public Product() {
    }

    public Product(String uniqued, String name, Manufacturer man,
                   double price, int quantity, Discount discount) {
         this.uniqueId = uniqued;
        this.name = name;
        manufacturer = man;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    /*
     *      In continuare, avem getteri si setteri sugestivi pentru fiecare
     * atribut al produsului
     */

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqued) {
        this.uniqueId = uniqued;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer man) {
        manufacturer = man;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
