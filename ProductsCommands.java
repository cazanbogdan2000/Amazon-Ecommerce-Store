import java.util.List;

/**
 * Clasa destinata comenzilor care au actiune directa asupra produselor din
 * cadrul magazinului
 */

public class ProductsCommands extends Command {
    public ProductsCommands() {
    }

    /**
     * Functie suprascrisa din clasa parinte Command, care selecteaza comanda
     * primita ca si input de la tastatura
     */
    @Override
    public void applyCommand() {
        Store store = Store.Instance();
        String[] input = Command.getInputType().getInput();

        switch (input[0]) {
            case "listproducts":
                this.listProducts(store);
                break;
            case "showproduct":
                this.showProduct(store, input);
                break;
            case "listmanufacturers":
                this.listManufacturers(store);
                break;
            case "listproductsbymanufacturer":
                this.listProductsByManufacturer(store, input);
                break;
            default:
                break;
        }
    }

    /**
     * Metoda care afiseaza produsele din store; prin afisare se intelege
     * printarea fiecarui atribut al acestuia
     * @param store magazinul in care se afla produsele
     */
    public void listProducts(Store store) {
        // caz in care nu exista inca produse
        if(store.getProducts() == null) {
            System.out.println("No products yet");
            return;
        }
        for(Product product : store.getProducts()) {
            // afisam produsele cu pretul deja redus
            double price = product.getPrice();
            if(product.getDiscount() != null)
            {
                if(product.getDiscount().getDiscountType() ==
                        DiscountType.FIXED_DISCOUNT) {
                    price = product.getPrice() -
                            product.getDiscount().getValue();
                }
                else {
                    price = product.getPrice() *
                            (1 - 0.01 * product.getDiscount().getValue());
                }
            }
            // afisarea efectiva a unui produs, printr-un one liner pe mai multe
            // linii :)
            System.out.println(product.getUniqueId() + "," +
                    product.getName() + "," +
                    product.getManufacturer().getName() + "," +
                    store.getCurrentCurrency().getSymbol() +
                    price + "," +
                    product.getQuantity()
            );
        }
    }

    /**
     * Metoda care afiseaza un singur produs, cautat dupa ID-ul sau unic
     * @param store magazinul unde se va cauta respectivul produs
     * @param input de aici se va lua id-ul dupa care se va face cautarea
     */
    public void showProduct(Store store, String[] input) {
        for(Product product : store.getProducts()) {
            if(product.getUniqueId().equals(input[1])) {
                // aplicam un discount asupra produsului, daca acesta exista
                double price = product.getPrice();
                if(product.getDiscount() != null)
                {
                    if(product.getDiscount().getDiscountType() ==
                            DiscountType.FIXED_DISCOUNT) {
                        price = product.getPrice() -
                                product.getDiscount().getValue();
                    }
                    else {
                        price = product.getPrice() *
                                (1 - 0.01 * product.getDiscount().getValue());
                    }
                }
                // afisarea produsului dorit
                System.out.println(product.getUniqueId() + "," +
                        product.getName() + "," +
                        product.getManufacturer().getName() + "," +
                        store.getCurrentCurrency().getSymbol() +
                        price + "," +
                        product.getQuantity()
                );
                // urmat de oprire, ca este unic
                return;
            }
        }
        // nu avem asemenea produs in magazin
        System.out.println("There is no product with this ID");
    }

    /**
     * Metoda care afiseaza lista de producatori, in ordine lexicografica.
     * Sortarea se va face direct pe lista de producatori, prin urmare aceasta
     * modificare va ramane si dupa iesirea din functie
     * @param store din store vom lua lista de manufacturers
     */
    public void listManufacturers(Store store) {
        // sortare lexicografica a producatorilor, dupa nume
        for(int i = 0; i < store.getManufacturers().size() - 1; i++) {
            for(int j = i + 1; j < store.getManufacturers().size(); j++) {
                if(store.getManufacturers().get(i).getName().compareTo(
                        store.getManufacturers().get(j).getName()) > 0) {
                    Manufacturer temp = store.getManufacturers().remove(i);
                    store.getManufacturers().add(i,
                            store.getManufacturers().remove(j - 1));
                    store.getManufacturers().add(j, temp);
                }
            }
        }
        // afisarea efectiva a producatorilor sortati
        for(Manufacturer manufacturer : store.getManufacturers()) {
            System.out.println(manufacturer.getName());
        }
    }

    /**
     * Metoda care afiseaza toate produsele din stocul unui producator
     * @param store magazinul in care se gasesc produsele si producatorul
     * @param input Locul de unde vom lua numele producatorului dupa care se
     *              va face cautarea
     */
    public void listProductsByManufacturer(Store store, String[] input) {
        Manufacturer wantedManufacturer = null;
        StringBuilder manufacturerName = new StringBuilder();
        for(int i = 1; i < input.length - 1; i++) {
            manufacturerName.append(input[i]).append(" ");
        }
        manufacturerName.append(input[input.length - 1]);

        // cautarea manufacturer-ului dupa nume in lista de manufacturers din
        //store
        for(Manufacturer manufacturer : store.getManufacturers()) {
            if(manufacturer.getName().equals(manufacturerName.toString())) {
                wantedManufacturer = manufacturer;
                break;
            }
        }
        // nu s-a gasit producatorul cu numele respectiv
        if(wantedManufacturer == null) {
            System.out.println("There isn't such a manufacturer");
            return;
        }
        List<Product> wantedProducts =
                store.getProductsByManufacturer(wantedManufacturer);

        // cautarea + afisarea tuturor produselor cu respectivul manufacturer
        for (Product product : wantedProducts) {
            double price = product.getPrice();
            if(product.getDiscount() != null)
            {
                if(product.getDiscount().getDiscountType() ==
                        DiscountType.FIXED_DISCOUNT) {
                    price = product.getPrice() - product.getDiscount().getValue();
                }
                else {
                    price = product.getPrice() *
                            (1 - 0.01 * product.getDiscount().getValue());
                }
            }
            // afisarea produsului
            System.out.println(product.getUniqueId() + "," +
                    product.getName() + "," +
                    product.getManufacturer().getName() + "," +
                    store.getCurrentCurrency().getSymbol() +
                    price + "," +
                    product.getQuantity()
            );
        }
    }
}