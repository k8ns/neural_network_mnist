package nn;

import java.util.ArrayList;
import java.util.List;

public class Network {

    private List<Layer> layers;

    public Network() {
        layers = new ArrayList<>();
    }

    public void addLayer(int numberOfNeurons) {
        layers.add(new Layer(layers.size() + 1, numberOfNeurons));

        if (layers.size() > 1) {
            layers.get(layers.size() - 2).connect(layers.get(layers.size() - 1));
        }
    }

    public void train(double[] inputs, double[] targets) {

        double[] values = query(inputs);

        for(int i = 0; i < targets.length; i++) {
            getOutputLayer().getNeurons().get(i).setError(targets[i] - values[i]);
        }

        for(int i = layers.size() - 2; i >= 0; i--) {
            layers.get(i+1).pushBackError();
            layers.get(i).pullBackError();
        }

        for(int i = layers.size() - 1; i > 0; i--) {
            layers.get(i).backPropagateError();
        }
    }

    public double[] query(double[] inputs) {
        getInputLayer().setValues(inputs);
        layers.forEach((l) -> l.pull());
        return getOutputLayer().getValues();
    }

    public Layer getInputLayer() {
        return layers.get(0);
    }

    public Layer getOutputLayer() {
        return layers.get(layers.size() - 1);
    }
}


