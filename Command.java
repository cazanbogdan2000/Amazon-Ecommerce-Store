import java.util.Scanner;

/**
 * Clasa abstracta care primeste un tip generic de comanda;
 * Este folosita cu scopul de a realiza Design pattern-ul suplimentar Factory
 */
public abstract class Command {
    /**
     * obiectul pentru citire
     */

    private static final Scanner scanner = new Scanner(System.in);
    /**
     * inputul, care reprezinta linia curenta citita de la stdin
     */
    private static InputType inputType;

    public static InputType getInputType() {
        return inputType;
    }

    public static void setInputType(InputType inputType) {
        Command.inputType = inputType;
    }

    /**
     * functie abstracta care aplica o anumita comanda pe store
     */
    public abstract void applyCommand();

    /**
     * Functie care creeaza InputType-ul curent
     * @throws UndefinedInputException daca apare o comanda necunoscuta, sau
     * scrisa gresit
     */
    public static void generateCommand()
            throws UndefinedInputException {
        String[] input = scanner.nextLine().split(" ");
        int inputCode = 0;

        while(inputCode == 0) {
            switch (input[0]) {
                case "listcurrencies":
                    inputCode = 1;
                    break;
                case "getstorecurrency":
                    inputCode = 1;
                    break;
                case "addcurrency":
                    inputCode = 1;
                    break;
                case "setstorecurrency":
                    inputCode = 1;
                    break;
                case "updateparity":
                    inputCode = 1;
                    break;
                case "loadcsv":
                    inputCode = 2;
                    break;
                case "savecsv":
                    inputCode = 2;
                    break;
                case "savestore":
                    inputCode = 2;
                    break;
                case "loadstore":
                    inputCode = 2;
                    break;
                case "listproducts":
                    inputCode = 3;
                    break;
                case "showproduct":
                    inputCode = 3;
                    break;
                case "listproductsbymanufacturer":
                    inputCode = 3;
                    break;
                case "listmanufacturers":
                    inputCode = 3;
                    break;
                case "listdiscounts":
                    inputCode = 4;
                    break;
                case "addiscount":
                    inputCode = 4;
                    break;
                case "applydiscount":
                    inputCode = 4;
                    break;
                case "calculatetotal":
                    inputCode = 5;
                    break;
                case "exit":
                    inputCode = 6;
                    break;
                case "quit":
                    inputCode = 6;
                    break;
                default:
                    inputCode = -1;
            }

            if(inputCode == -1) {
                throw new UndefinedInputException("No such command");
            }
        }
        Command.inputType = new InputType(inputCode, input);
    }
}
