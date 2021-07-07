import java.io.IOException;

/**
 * Clasa care se ocupa cu generare de fisiere, fie ele de tip csv, fie de tip
 * binar, salvand astfel datele magazinului nostru
 */
public class CSVFilesCommands extends Command {
    public CSVFilesCommands() {
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
            case "loadcsv":
                this.loadCsv(store, input);
                break;
            case "savecsv":
                this.saveCsv(store, input);
                break;
            case "savestore":
                this.saveStoreState(store, "binary.dat");
                break;
            case "loadstore":
                this.loadStoreState(store, "binary.dat");
                break;
            default:
                break;
        }
    }

    /**
     * Metoda care incarca un fisier csv in cadrul programului nostru,
     * initializandu-se astfel magazinul nostru
     * @param store magazinul pe care dorim sa il incarcam
     * @param input de aici luam numele fisierului pe care vrem sa il incarcam
     */
    public void loadCsv(Store store, String[] input) {
        store.setProducts(store.readCSV(input[1]));
    }

    /**
     * Metoda care un fisier de tip csv cu noile date ale magazinului obtinute
     * @param store magazinul din care vrem sa formam respectivul csv
     * @param input De aici se va lua numele fisierului pe care dorim sa il
     *              cream
     */
    public void saveCsv(Store store, String[] input) {
        store.saveCSV(input[1]);
    }

    /**
     *      BONUS
     * Metoda care va salva starea curenta a magazinului intr-un fisier binar;
     * Starea va fi reprezentata de Currency-ul curent, produsele din cadrul
     * magazinului (cu tot cu producatorii) si lista de discount-uri
     * @param store magazinul al carui state curent dorim sa il salvam
     * @param filename numele fisierului in care se va salva; folosim ca si
     *                 denumire principala binary.dat, intrucat vrem sa retinem
     *                 doar ultima stare curenta (ce e mult strica)
     */
    public void saveStoreState(Store store, String filename) {
        try {
            store.saveStore(filename);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *      BONUS
     * Metoda care va incarca state-ul magazinului definit mai sus
     * @param store magazinul pe care dorim sa il reactualizam
     * @param filename numele in care am salvat state-ul respectiv; in cazul
     *                 de fata, numele este implicit "binary.dat"
     */
    public void loadStoreState(Store store, String filename) {
        try {
            store.loadStore(filename);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
