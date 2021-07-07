/**
 * Clasa destinata comenzilor care au actiune directa asupra Discount-urilor din
 * cadrul magazinului
 */

public class DiscountsCommands extends Command {
    public DiscountsCommands() {
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
            case "listdiscounts":
                this.listDiscounts(store);
                break;
            case "addiscount":
                this.addDiscount(store, input);
                break;
            case "applydiscount":
                this.applyDiscount(store, input);
                break;
            default:
                break;
        }
    }

    /**
     * Metoda care afiseaza toate discount-urile intalnite pana acum in cadrul
     * magazinului
     * @param store magazinul care contine respectivele discount-uri
     */
    public void listDiscounts(Store store) {
        if(store.getDiscounts() == null) {
            System.out.println("No discounts found");
            return;
        }
        for(Discount discount : store.getDiscounts()) {
            System.out.println(discount.getDiscountType() + " " +
                    discount.getValue() + " " +
                    discount.getName() + " " +
                    discount.getLastDateApplied()
            );
        }
    }

    /**
     * Metoda care adauga un nou discount in cadrul magazinului
     * @param store magazinul in care trebuie sa se adauge noul discount
     * @param input De aici se va lua discount-ul pe care vrem sa il adaugam
     */
    public void addDiscount(Store store, String[] input) {
        DiscountType discountType;
        double value = Double.parseDouble(input[2]);
        StringBuilder name = new StringBuilder();

        if(input[1].equals("PERCENTAGE")) {
            discountType = DiscountType.PERCENTAGE_DISCOUNT;
        }
        else {
            discountType = DiscountType.FIXED_DISCOUNT;
        }
        // aici se formeaza numele complet pentru discount
        for(int i = 3; i < input.length; i++) {
            name.append(input[i]).append(" ");
        }

        store.createDiscount(discountType, name.toString(), value);
    }

    /**
     * Metoda care aplica un anumit discount
     * @param store locul in care se afla (sau nu) discount-ul pe care vrem sa\
     *              il aplicam
     * @param input De aici se va forma discount-ul pe care dorim sa il aplicam
     */
    public void applyDiscount(Store store, String[] input) {
        DiscountType discountType;
        double value = Double.parseDouble(input[2]);

        if(input[1].equals("PERCENTAGE")) {
            discountType = DiscountType.PERCENTAGE_DISCOUNT;
        }
        else {
            discountType = DiscountType.FIXED_DISCOUNT;
        }
        // verificam daca discount-ul se afla in cadrul listei de discounturi
        // din cadrul store-ului
        try {
            store.applyDiscount(new Discount("", discountType, value));
        }
        catch (DiscountNotFoundException | NegativePriceException e) {
            System.out.println(e.getMessage());
        }
    }
}
