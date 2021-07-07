/**
 * Clasa main; nimic special, doar cateva initializari si apeluri de functii
 * sugestive, care realizeaza functionalitatea de terminal
 */

public class Main {

    public static void main(String[] args) {
        CommandFactory commandFactory = new CommandFactory();
        Store store = Store.Instance();
        //initializare currency default cu Euro
        store.createCurrency("EUR", "€", 1.0);
        try {
            store.changeCurrency(
                    new Currency("EUR", "€", 1.0)
            );
        }
        catch (CurrencyNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //functionalitatea de terminal se realizeaza fix aici
        while(true) {
            Command command = commandFactory.createCommand();
            if(command == null) {
                System.out.println("Wrong command; please select another one");
                continue;
            }
            command.applyCommand();
        }
    }
}
