/**
 * Clasa in care am folosit design pattern-ul suplimentar Factory: creeam un
 * obiect de tipul Command, care poate fi specific anumitor operatii; spre\
 * exemplu, operatii pe produse, operatii pe fisiere(binare sau de tipul csv),
 * comenzi aplicate Currency-ului sau Discount-ului, etc.
 */

public class CommandFactory {

    /**
     * Functia care creeaza un mostenitor al clasei Command, in functie de
     * comanda primita de la stdin
     * @return un obiect ce extinde Clasa abstracta Command, in functie de codul
     * din InputType
     */
    public Command createCommand() {
        InputType inputType = null;
        try {
            Command.generateCommand();
            inputType = Command.getInputType();
        }
        catch (UndefinedInputException e) {
            System.out.println(e.getMessage());
        }
        if(inputType == null) {
            return null;
        }
        switch (inputType.getInputCode()) {
            case 1:
                return new CurrenciesCommands();
            case 2:
                return new CSVFilesCommands();
            case 3:
                return new ProductsCommands();
            case 4:
                return new DiscountsCommands();
            case 5:
                return new ComputationalCommands();
            case 6:
                return new CloseProgramCommands();
            default:
                return null;
        }
    }
}
