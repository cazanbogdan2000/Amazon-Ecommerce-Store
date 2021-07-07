/**
 * Clasa builder pentru Product; design pattern foarte util pentru cod aglomerat
 *
 * Numele metodelor este foarte sugestiv, nu mai este nevoie explicita de
 * prezentarea acestora in parte
 */

public class ProductBuilder {
    private final Product product = new Product();

    public Product build() {
        return product;
    }

    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder withID(String ID) {
        product.setUniqueId(ID);
        return this;
    }

    public ProductBuilder withPrice(double price) {
        product.setPrice(price);
        return this;
    }

    public ProductBuilder withQuantity(int quantity) {
        product.setQuantity(quantity);
        return this;
    }

    public ProductBuilder setDiscount(Discount discount) {
        product.setDiscount(discount);
        return this;
    }

    public ProductBuilder setManufacturer(Manufacturer manufacturer) {
        product.setManufacturer(manufacturer);
        return this;
    }
}
