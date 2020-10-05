package nncoach;

public class TrainCase {
    double[] target;
    double[] inputs;

    public TrainCase(int intTarget, double[] inp) {

        target = new double[]{0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d};
        target[intTarget] = 0.99d;

        inputs = inp;
    }

    public double[] getTarget() {
        return target;
    }

    public void setTarget(double[] target) {
        this.target = target;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }
}
