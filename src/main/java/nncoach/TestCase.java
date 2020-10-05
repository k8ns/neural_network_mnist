package nncoach;

public class TestCase {
    private int expectation;
    private double[] inputs;

    public TestCase(int e, double[] i) {
        expectation = e;
        inputs = i;
    }

    public int getExpectation() {
        return expectation;
    }

    public void setExpectation(int expectation) {
        this.expectation = expectation;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }
}
