package nn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Neuron {

    private List<Connection> inputConnections;
    private List<Connection> outputConnections;
    private double value;
    private double error;

    private Function<Double, Double> sigmoid = (val) ->  1 / (1 + Math.exp(-val));


    public Neuron() {
        inputConnections = new ArrayList<>();
        outputConnections = new ArrayList<>();
        error = 0;
    }

    public void connect(Neuron neuron, int numberConnInGroup) {
        Connection connection = new Connection(this, neuron, numberConnInGroup);
        neuron.inputConnections.add(connection);
        outputConnections.add(connection);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        if (value <= 0.0 || value >= 1.0) {
            throw new RuntimeException("set unacceptable neuron value: " + value);
        }

        this.value = value;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public void pull() {
        if (inputConnections.size() == 0) {
            return;
        }
        double sum = 0;
        for (Connection connection : inputConnections) {
            sum += connection.pull();
        }
        double val = sigmoid.apply(sum);
        if (val == 1) {
            val = 0.99;
        }
        if (val == 0) {
            val = 0.01;
        }
        setValue(val);
    }

    public void pushBackError() {
        double inputConnectionsWeightSum = 0;

        for (Connection conn : inputConnections) {
            inputConnectionsWeightSum += Math.abs(conn.getWeight());
        }

        for (Connection conn : inputConnections) {
            conn.setError(conn.getWeight() * getError() / inputConnectionsWeightSum);
        }
    }

    public void pullBackError() {
        double errSum = 0;
        for (Connection conn : outputConnections) {
            errSum += conn.getError();
        }
        setError(errSum);
    }

    public void backPropagateError() {
        for (Connection conn : inputConnections) {
            double delta = 0.1 * getError() * value * (1 - value) * conn.getFrom().getValue();
            conn.update(delta);
        }
    }
}
