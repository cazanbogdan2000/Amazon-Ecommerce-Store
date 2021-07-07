/**
 * Clasa care executa comenzile de inchidere ale programului
 */
public class CloseProgramCommands extends Command {

    public CloseProgramCommands() {
    }

    /**
     * Functie suprascrisa din clasa parinte Command, care selecteaza comanda
     * primita ca si input de la tastatura
     */
    @Override
    public void applyCommand() {
        String[] input = Command.getInputType().getInput();
        switch (input[0]) {
            case "exit":
            case "quit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
