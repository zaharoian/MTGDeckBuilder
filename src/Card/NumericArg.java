package Card;

public class NumericArg {
    private int val;
    private Operator op;

    public NumericArg() {
    }

    public NumericArg(int val, Operator op) {
        this.val = val;
        this.op = op;
    }

    public int getVal() {
        return val;
    }

    public Operator getOp() {
        return op;
    }
}
