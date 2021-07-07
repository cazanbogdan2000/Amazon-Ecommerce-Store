/**
 * Clasa destinata comenzilor care au actiune directa asupra Currency-ului din
 * cadrul magazinului
 */

public class CurrenciesCommands extends Command {
    public CurrenciesCommands() {

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
            case "listcurrencies":
                this.listCurrencies(store);
                break;
            case "getstorecurrency":
                this.getStoreCurrency(store);
                break;
            case "addcurrency":
                this.addCurrency(store, input);
                break;
            case "setstorecurrency":
                try {
                    this.setStoreCurrency(store, input);
                } catch (CurrencyNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "updateparity":
                try {
                    this.updateParity(store, input);
                }
                catch (CurrencyNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            default:
                break;
        }
    }

    /**
     * Metoda care afiseaza Currency-urile prezente in store la momentul actual
     * @param store magazinul de unde se iau Currency-urile
     */
    public void listCurrencies(Store store) {
        for(Currency currency : store.getCurrencies()) {
            System.out.println(currency.getName()
                    + " " + currency.getParityToEur());
        }
    }

    /**
     * Ia Currency-ul curent din store
     * @param store magazinul
     */
    public void getStoreCurrency(Store store) {
        System.out.println(store.getCurrentCurrency().getName());
    }

    /**
     * Adauga o noua moneda in cadrul magazinului
     * @param store locul unde ar trebui sa ajunga
     * @param input De aici se va forma noul Currency
     */
    public void addCurrency(Store store, String[] input) {
        store.createCurrency(input[1], input[2],
                Double.parseDouble(input[3]));
    }

    /**
     * Seteaza o noua moneda pentru magazin
     * @param store magazinul in care trebuie setata noua moneda
     * @param input contine moneda ce vrem sa o setam
     * @throws CurrencyNotFoundException daca nu se gaseste currency-ul dat
     */
    public void setStoreCurrency(Store store, String[] input)
        throws CurrencyNotFoundException {
        Currency currToChange = null;
        for(Currency currency : store.getCurrencies()) {
            if(currency.getName().equals(input[1])) {
                currToChange = currency;
                break;
            }
        }
        if(currToChange == null) {
            throw new CurrencyNotFoundException("Currency not found");
        }
        else {
            store.changeCurrency(currToChange);
        }
    }

    /**
     * Metoda care actualizeaza paritatea unei monede la Euro
     * @param store magazinul de unde se ia Currency-ul dorit
     * @param input Paritatea pe care dorim sa o setam + numele monedei
     * @throws CurrencyNotFoundException Moneda pe care dorim sa o actualizam nu
     * figureaza in lista de currency-uri din cadrul magazinului
     */
    public void updateParity(Store store, String[] input)
            throws CurrencyNotFoundException {
        Currency currToUpdate = null;
        for (Currency currency : store.getCurrencies()) {
            if (currency.getName().equals(input[1])) {
                currToUpdate = currency;
                break;
            }
        }
        if (currToUpdate == null) {
            throw new CurrencyNotFoundException("Currency not found");
        } else {
            if(currToUpdate.getName().
                    equals(store.getCurrentCurrency().getName())
            ) {
                for(Product product : store.getProducts()) {
                    product.setPrice(product.getPrice() *
                            (Double.parseDouble(input[2]) /
                                    currToUpdate.getParityToEur()));
                }
            }
            currToUpdate.updateParity(Double.parseDouble(input[2]));
        }
    }

}
