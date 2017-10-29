package nn;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private List<Neuron> neurons;
    private int id;

    public Layer(int id, int numberOfNeurons) {
        this.id = id;
        neurons = new ArrayList<>();

        while (numberOfNeurons > 0) {
            neurons.add(new Neuron());
            numberOfNeurons--;
        }
    }

    void connect(Layer layer) {
        neurons.forEach((n) -> layer.neurons.forEach((n2) -> n.connect(n2, neurons.size())));
    }

    public void pull() {
        neurons.forEach((n) -> n.pull());
    }

    public double[] getValues() {
        double[] output = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            output[i] = neurons.get(i).getValue();
        }
        return output;
    }

    public void setValues(double[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            neurons.get(i).setValue(inputs[i]);
        }
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void pushBackError() {
        neurons.forEach((n) -> n.pushBackError());
    }

    public void pullBackError() {
        neurons.forEach((n) -> n.pullBackError());
    }

    public void backPropagateError() {
        neurons.forEach((n) -> n.backPropagateError());
    }
}
