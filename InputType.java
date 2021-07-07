/**
 * Clasa pentru a retine comanda curenta, cu tot cu codul respectiv acesteia,
 * precum si intreaga comanda pe linie
 */

public class InputType {
    // codul comenzii, folosit la CommandFactory in general
    private int inputCode;
    // inputul efectiv al comenzii
    private String[] input;

    public InputType() {
    }

    public InputType(int inputCode, String[] input) {
        this.inputCode = inputCode;
        this.input = input;
    }

    public int getInputCode() {
        return inputCode;
    }

    public void setInputCode(int inputCode) {
        this.inputCode = inputCode;
    }

    public String[] getInput() {
        return input;
    }

    public void setInput(String[] input) {
        this.input = input;
    }
}
