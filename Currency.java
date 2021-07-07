import java.io.Serializable;

/**
 * Clasa pentru currency, stocheaza numele moenedei, simbolul acesteia si
 * paritatea acesteia la euro; destul de clar ce face fiecare metoda, trecem
 * mai departe
 */
public class Currency implements Serializable {
    private String name;
    private String symbol;
    private double parityToEur;

    public Currency() {
    }

    public Currency(String name, String symbol, double parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = parityToEur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getParityToEur() {
        return parityToEur;
    }

    public void updateParity(double parityToEUR) {
        this.parityToEur = parityToEUR;
    }
}
