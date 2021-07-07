import org.apache.commons.csv.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;
import java.io.IOException;

/**
 * Clasa Store; de departe cea mai importanta parte din intreaga tema
 * Atribute reprezentative (toate sunt):
 * -> Singleton pentru store, uniqueInstance
 * -> numele magazinului, nu stiu de ce exista, ca nu l-am folosit nicaieri
 * -> moneda curenta care se afla pe store, currentCurrency
 * -> lista de produse, products
 * -> lista de producatori existenti, manufacturers
 * -> lista de Currency-uri disponibile in cadrul magazinului, currencies
 * -> lista de discounturi active, discounts
 */

public class Store {
    private static Store uniqueInstance;
    private String name;
    private Currency currentCurrency;
    private List<Product> products;
    private List<Manufacturer> manufacturers;
    private List<Currency> currencies;
    private List<Discount> discounts;

    /**
     * Constructor privat; avem nevoie de el pentru Singleton
     */
    private Store() {
    }

    /**
     * Metoda care creeaza un obiect unic de tip Store
     * @return instanta unica a magazinului
     */
    public static Store Instance() {
        if (Store.uniqueInstance == null) {
            Store.uniqueInstance = new Store();
        }
        return Store.uniqueInstance;
    }

    /**
     * Metoda care creeaza un pret prin eliminarea virgulelor neimportante
     * @param price pretul initial, un array de stringuri, cu elementele numere
     *              cuprinse intre virgulele buclucase
     * @return returneaza un pret "clean"
     */
    public String createPrice(String[] price) {
        StringBuilder result = new StringBuilder();
        for (String element : price) {
            result.append(element);
        }
        return result.toString();
    }

    /**
     * Metoda care citeste un fisier de tip CSV si il prelucreaza, actualizand
     * magazinul curent
     * @param filename fisierul din care se iau informatiile dorite
     * @return lista de produse din cadrul magazinului
     */
    public List<Product> readCSV(String filename) {
        List<Product> products = new ArrayList<>();
        manufacturers = new ArrayList<>();
        //deschiderea si citirea fisierului CSV
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            // parsarea fiecarei linii din fisierul CSV
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.get(3).equals("") ||
                        csvRecord.get(3).equals("price")) {
                    continue;
                }
                String price = createPrice(csvRecord.get(3).substring(1)
                        .split("-")[0].split(","));
                // crearea unui produs pe baza prelucrarii recordului din CSV
                Product product = new ProductBuilder()
                        .withID(csvRecord.get(0))
                        .withName(csvRecord.get(1))
                        .withPrice(Double.parseDouble(price))
                        .withQuantity(Integer.parseInt(csvRecord.get(4)
                                .equals("") ? "0" : csvRecord.get(4)
                                .replaceAll("(\\d+).+", "$1")))
                        .build();
                products.add(product);

                // crearea de asemenea si a producatorului
                Manufacturer manufacturer = new ManufacturerBuilder()
                        .withName(csvRecord.get(2))
                        .build();
                if(manufacturer.getName().equals("")) {
                    manufacturer.setName("Not Available");
                }
                product.setManufacturer(manufacturer);
                // verificarea duplicatelor pentru producatori
                try {
                    this.addManufacturer(manufacturer);
                } catch (DuplicateManufacturerException duplicate) {
                    System.out.println(duplicate.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    /**
     * Metoda care creeaza un fisier CSV cu datele din cadrul lui store
     * @param filename numele fisierului in care trebuie scrise produsele
     */
    public void saveCSV(String filename) {
        // deschiderea si scrierea fisierului CSV
        try (
                BufferedWriter writer = Files.
                        newBufferedWriter(Paths.get(filename));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("uniq_id", "product_name", "manufacturer",
                                "price", "number_available_in_stock"))
        ) {
            // parcurgerea produselor si introducerea lor in fisier
            for (Product product : products) {
                csvPrinter.printRecord(product.getUniqueId(),
                        product.getName(), product.getManufacturer().getName(),
                        currentCurrency.getSymbol() + product.getPrice(),
                        product.getQuantity() + "\u0020" + "NEW");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda care adauga un produs in cadrul lui store
     * @param product produsul care se doreste a fi inserat
     * @throws DuplicateProductException exceptie pentru un doua produse cu
     * acelasi ID
     */
    public void addProduct(Product product) throws DuplicateProductException {
        for (Product newProduct : products) {
            if (newProduct.getName().equals(product.getName())) {
                throw new DuplicateProductException("This product already" +
                        " exists in store");
            }
        }
        products.add(product);
    }

    /**
     * Metoda care adauga un producator in lista de producatori a magazinului
     * @param manufacturer noul producator ce se doreste a fi inserat
     * @throws DuplicateManufacturerException exceptie pentru un doi producatori
     * cu acelasi nume
     */
    public void addManufacturer(Manufacturer manufacturer)
            throws DuplicateManufacturerException {
        if (manufacturers == null) {
            manufacturers = new ArrayList<>();
        }
        for (Manufacturer newManufacturer : manufacturers) {
            if (newManufacturer.getName().equals(manufacturer.getName())) {
                throw new DuplicateManufacturerException("This manufacturer " +
                        "already exists");
            }
        }
        manufacturers.add(manufacturer);
    }

    /**
     * Metoda care creeaza un nou currency si il adauga in cadrul magazinului
     * @param name numele monedei
     * @param symbol simbolul aferent acesteia
     * @param parityToEur paritatea acesteia in raport cu Euro
     */
    public void createCurrency(String name, String symbol, double parityToEur) {
        if (currencies == null) {
            currencies = new ArrayList<>();
        }
        currencies.add(new Currency(name, symbol, parityToEur));
    }

    /**
     * Metoda care schimba moneda actuala a magazinului
     * @param currency noul currency care va fi in cadrul magazinului
     * @throws CurrencyNotFoundException exceptie care apare in cazul in care
     * noul currency nu se gasete in lista de currency-uri din cadrul lui store
     */
    public void changeCurrency(Currency currency)
            throws CurrencyNotFoundException {
        // verificare existenta a currency-ului dat
        int ok = 0;
        for (Currency curr : currencies) {
            if (curr.getName().equals(currency.getName())) {
                ok = 1;
                break;
            }
        }
        if (ok == 0) {
            throw new CurrencyNotFoundException("Couldn't find this currency");
        }
        //daca currency-ul exista, putem face actualizarea
        Currency oldCurrency = this.currentCurrency;
        this.currentCurrency = currency;

        if (products == null) {
            return;
        }
        // aplicam noua moneda pentru fiecare produs in parte
        for (Product product : products) {
            product.setPrice(product.getPrice() *
                    (oldCurrency.getParityToEur() /
                            currentCurrency.getParityToEur()));
        }
    }

    /**
     * Metoda care creeaza un nou discount si il adauga in lista existenta de
     * discount-uri
     * @param discountType tipul de discount
     * @param name numele/descrierea discountului
     * @param value valoarea pe care o va avea discountul
     * @return discount-ul creat
     */
    public Discount createDiscount(DiscountType discountType,
                                   String name, double value) {
        if (discounts == null) {
            discounts = new ArrayList<>();
        }
        discounts.add(new Discount(name, discountType, value));
        discounts.get(discounts.size() - 1).setAsAppliedNow();
        return discounts.get(discounts.toArray().length - 1);
    }

    /**
     * Metoda care aplica un discount pe intregul magazin; daca acesta se va
     * schimba din nou, pretul unui produs va reveni la normal, si dupa se va
     * aplica noul discount
     * @param discount discount-ul care se doreste sa se aplice
     * @throws DiscountNotFoundException nu s-a gasit discount-ul
     * @throws NegativePriceException discount-ul nu se poate aplica, preturi
     *                              negative pe unele produse
     */
    public void applyDiscount(Discount discount)
            throws DiscountNotFoundException, NegativePriceException {

        // verificare daca exista discount
        int ok = 0;
        for (Discount disc : discounts) {
            if (disc.getDiscountType() == discount.getDiscountType() &&
                    disc.getValue() == discount.getValue()
            ) {
                ok = 1;
                break;
            }
        }
        if (ok == 0) {
            throw new DiscountNotFoundException("Discount couldn't be found");
        }

        // aplicare discount, in cazul in care se poate fara pret negativ
        for (Product product : products) {
            if (discount.getDiscountType() == DiscountType.FIXED_DISCOUNT) {
                if (product.getPrice() - discount.getValue() < 0) {
                    throw new NegativePriceException("Couldn't apply discount");
                }
            }
        }
        for(Product product : products) {
            product.setDiscount(discount);
        }
    }

    /**
     * Metoda care creeaza o lista cu produsele ce au un anumit manufacturer
     * @param manufacturer producatorul dupa care se face cautarea produselor
     * @return lista de produse gasite
     */
    public ArrayList<Product> getProductsByManufacturer(
            Manufacturer manufacturer) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getManufacturer().getName().
                    equals(manufacturer.getName())) {
                result.add(product);
            }
        }
        return result;
    }

    /**
     * Metoda care calculeaza pretul total al unor produse; se foloseste si
     * cantitatea produselor in acest caz, pentru ca daca nu exista un produs in
     * stoc, nu ar avea sens sa fie luat in calcul produsul respectiv
     * @param product lista de produse pentru care vrem sa aflam costul total
     * @return returneaza pretul calculat
     */
    public double calculateTotal(ArrayList<Product> product) {
        double result = 0;
        for (Product prod : product) {
            double price = prod.getPrice();
            if(prod.getDiscount() != null)
            {
                // obtinerea pretului cu reducere, in cazul in care aceasta
                // exista
                if(prod.getDiscount().getDiscountType() ==
                        DiscountType.FIXED_DISCOUNT) {
                    price = prod.getPrice() - prod.getDiscount().getValue();
                }
                else {
                    price = prod.getPrice() *
                            (1 - 0.01 * prod.getDiscount().getValue());
                }
            }
           result += price * prod.getQuantity();
        }
        return result;
    }

    /**
     * BONUS: Metoda care creeaza un fisier binar in care se afla state-ul
     * curent al magazinului dat: moneda curenta, lista de produse (cu tot cu
     * producatori) si lista de discount-uri disponibile
     * @param filename numele fisierului binar in care vrem sa salvam datele
     *                 noastre
     * @throws IOException exceptie cazul in care nu se poate deschide fisierul
     */
    public void saveStore(String filename) throws IOException{
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(filename)
            );
            // daca lista de discount-uri sau de produse nu exista, atunci nu
            // putem crea state-ul dorit
            if(discounts == null || products == null) {
                throw new IOException("Couldn't create store state;" +
                        " You don't have a list of discounts or products yet");
            }
            // scriere currency curent
            out.writeObject(this.currentCurrency);

            // scrirere produse
            for(Product product : products) {
                out.writeObject(product);
            }
            // acest produs reprezinta un delimitator intre produsele din binar
            // si discounturi
            Product endProduct = new ProductBuilder()
                    .withName("NEWLINE")
                    .build();
            out.writeObject(endProduct);

            // scrierea discount-urilor
            for(Discount discount : discounts) {
                out.writeObject(discount);
            }
            // acest discount reprezinta un delimitator intre discount-urile din
            // binar si EOF
            Discount endDiscount = new DiscountBuilder()
                    .withName("NEWLINE")
                    .withValue(0)
                    .build();
            out.writeObject(endDiscount);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * BONUS: Metoda care incarca un state mai vechi al magazinului, dintr-un
     * fisier binar
     * @param filename numele fisierului din care se face citirea
     * @throws IOException exceptie aruncata daca fisierul nu a fost gasit sau
     * nu s-a putut deschide
     */
    public void loadStore(String filename) throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(filename)
            );
            // cream delimitatorii
            Product endProduct = new ProductBuilder()
                    .withName("NEWLINE")
                    .build();
            Discount endDiscount = new DiscountBuilder()
                    .withName("NEWLINE")
                    .withValue(0)
                    .build();
            // citim currency-ul curent
            this.currentCurrency = (Currency) in.readObject();
            this.discounts = new ArrayList<>();
            this.products = new ArrayList<>();

            Product product = (Product) in.readObject();

            // cat timp nu ajungem la delimitator, citim produsele
            while(!product.getName().equals(endProduct.getName())) {
                this.products.add(product);
                product = (Product) in.readObject();
            }

            Discount discount = (Discount) in.readObject();
            // cat timp nu ajungem la delimitaor, citim discount-urile
            while(!discount.getName().equals(endDiscount.getName())) {
                this.discounts.add(discount);
                discount = (Discount) in.readObject();
            }
            in.close();
            this.changeCurrency(currentCurrency);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /*
    Getteri si Setteri utili
     */

    public Currency getCurrentCurrency() {
        return currentCurrency;
    }

    public void setCurrentCurrency(Currency currentCurrency) {
        this.currentCurrency = currentCurrency;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(Currency currency) {
        this.currentCurrency = currency;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }
}
