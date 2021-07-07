import java.util.ArrayList;

/**
 * Clasa care are ca principal realizarea anumitor calcule pe produs, si nu
 * numai (de extins pe viitor cu mai multe astfel de operatii, dar asta nu
 * face momentan parte din tema nosatra :) )
 */
public class ComputationalCommands extends Command {
    public ComputationalCommands() {
    }

    /**
     * Functie suprascrisa din clasa parinte Command, care selecteaza comanda
     * primita ca si input de la tastatura
     */
    @Override
    public void applyCommand() {
        Store store = Store.Instance();
        String[] input = Command.getInputType().getInput();

        if ("calculatetotal".equals(input[0])) {
            this.calculateTotal(store, input);
        }
    }

    /**
     * Metoda care calculeaza suma totala a preturilor unor produse date ca si
     * parametru de la stdin
     * @param store magazinul unde se gasesc produsele respective
     * @param input de aici se iau produsele care se doresc
     */
    public void calculateTotal(Store store, String[] input) {
        ArrayList<Product> wantedProducts = null;
        for(int i = 1; i < input.length; i++) {
            for(Product product : store.getProducts()) {
                if(input[i].equals(product.getUniqueId())) {
                    if(wantedProducts == null) {
                        wantedProducts = new ArrayList<>();
                    }
                    wantedProducts.add(product);
                    break;
                }
            }
        }
        assert wantedProducts != null;
        System.out.println(store.getCurrentCurrency().getSymbol() +
                store.calculateTotal(wantedProducts));
    }
}
