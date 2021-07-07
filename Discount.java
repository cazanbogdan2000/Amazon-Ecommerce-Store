import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clasa pentru a defini un discount, ce se poate aplica pe un produs;
 * Contine un nume specific, tipul de discount (fie fixat, sub forma de numar,
 * fie sub forma de procentaj), o valoare care se aplica pe pretul unui produs,
 * si de asemenea data in care acesta apare in cadrul magazinului
 *
 * La fel ca si la alte clase, numele metodelor sunt mai mult decat sugestive
 */

public class Discount implements Serializable {
    private String name;
    private DiscountType discountType;
    private double value;
    LocalDateTime lastDateApplied = null;

    public Discount() {
    }

    public Discount(String name, double value, LocalDateTime lastDateApplied) {
        this.name = name;
        this.value = value;
        this.lastDateApplied = lastDateApplied;
    }

    public Discount(String name, DiscountType discountType, double value) {
        this.name = name;
        this.discountType = discountType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getLastDateApplied() {
        return lastDateApplied;
    }

    /**
     * seteaza data aplicarii pentru un anumit produs
     */
    public void setAsAppliedNow() {
        lastDateApplied = LocalDateTime.now();
    }
}
