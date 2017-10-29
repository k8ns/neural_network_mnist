package nn;

import java.util.Random;

public class Connection {

    private Neuron from;
    private Neuron to;
    private double weight;
    private double error;

    private double maxWeight;

    static Random rand;

    static {
        rand = new Random();
    }

    public Connection(Neuron from, Neuron to, int numberConnInGroup) {
        this.from = from;
        this.to = to;
        maxWeight = 1 / Math.sqrt(numberConnInGroup);
        setWeight((rand.nextDouble() - 0.5) * maxWeight);
    }

    public void update(double delta) {
        setWeight(weight + delta);
    }

    public double pull() {
        return from.getValue() * weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Neuron getTo() {
        return to;
    }

    public Neuron getFrom() {
        return from;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}
