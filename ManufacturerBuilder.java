/**
 * Clasa ce reprezinta un builder pentru Manufacturer; acesta nu era cerut in
 * cadrul temei (era cerut doar pentru Product), dar realizarea acestuia da un
 * aspect placut temei
 */

public class ManufacturerBuilder {
    private final Manufacturer manufacturer = new Manufacturer();

    /**
     * Construirea lui manufacturer
     * @return returneaza obiectul creat
     */
    public Manufacturer build() {
        return manufacturer;
    }

    /**
     * Atribuie un nume producatorului
     * @param name numele producatorului
     * @return returneaza un pointer la adresa respectiva
     */
    public ManufacturerBuilder withName(String name) {
        manufacturer.setName(name);
        return this;
    }
}
