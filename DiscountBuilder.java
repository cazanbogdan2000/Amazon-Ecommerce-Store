/**
 * Clasa ce reprezinta un builder pentru Discount; acesta nu era cerut in cadrul
 * temei (era cerut doar pentru Product), dar realizarea acestuia da un aspect
 * placut temei
 */

public class DiscountBuilder {
    private final Discount discount = new Discount();

    /**
     * Construirea discount-ului
     * @return obiectul creat
     */
    public Discount build() {
        return discount;
    }

    /**
     * Creeaza numele
     * @param name numele pe care dorim sa il aiba discountul
     * @return adresa obiectului curent
     */
    public DiscountBuilder withName(String name) {
        discount.setName(name);
        return this;
    }

    /**
     * Setam valoarea pe care dorim sa o aiba discountul
     * @param value valoarea dorita
     * @return adresa obiectului curent
     */
    public DiscountBuilder withValue(double value) {
        discount.setValue(value);
        return this;
    }
}
