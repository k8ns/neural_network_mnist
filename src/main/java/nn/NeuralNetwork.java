package nn;

import org.ejml.simple.SimpleMatrix;

import java.util.Random;
import java.util.function.Function;

public class NeuralNetwork {


    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;
    private Double learningRate;

    private SimpleMatrix hiddenWeights;
    private SimpleMatrix outputWeights;

    private Function<Double, Double> sigmoid = (val) ->  1 / (1 + Math.exp(-val));


    public NeuralNetwork(int i, int h, int o, Double lr) {
        inputNodes = i;
        hiddenNodes = h;
        outputNodes = o;
        learningRate = lr;

        hiddenWeights = SimpleMatrix.random64(hiddenNodes, inputNodes, -0.5d, 0.5d, new Random());
        outputWeights = SimpleMatrix.random64(outputNodes, hiddenNodes, -0.5d, 0.5d, new Random());

    }






    public void train(double[] inputs, int target) throws IllegalArgumentException {
        if (!isTargetAchievable(target)) {
            throw new IllegalArgumentException("Target is not Achievable " + target);
        }

        double[] targets = new double[]{0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d};
        targets[target] = 0.99d;

        SimpleMatrix targetsMatrix = createVectorMatrixFromArray(targets);
        SimpleMatrix inputsMatrix = adaptInputMatrix(inputs);


        SimpleMatrix hiddenInputs = hiddenWeights.mult(inputsMatrix);
        SimpleMatrix hiddenOutputs = applySigmoid(hiddenInputs);


        SimpleMatrix finalInputs = outputWeights.mult(hiddenOutputs);
        SimpleMatrix finalOutputs = applySigmoid(finalInputs);


        ///Error propagate
        SimpleMatrix outputErrors = targetsMatrix.minus(finalOutputs);
        SimpleMatrix hiddenErrors = outputWeights.transpose().mult(outputErrors);


        //update weights

        outputWeights.printDimensions();

        outputWeights = outputWeights.plus(deltaError(finalOutputs, outputErrors));
        hiddenWeights = hiddenWeights.plus(deltaError(hiddenOutputs, hiddenErrors));

    }


    private SimpleMatrix deltaError(SimpleMatrix outputs, SimpleMatrix errors) {
//        System.out.println("Ewo");

        SimpleMatrix n1 = outputWeights.transpose().mult(outputs);

        n1.printDimensions();

//        n1.print();


//        System.out.println("Sigmoid");
        SimpleMatrix n2 = applyFunction(n1, sigmoid);
        n2.printDimensions();
//        n2.print();

//        System.out.println("S*(1-S)");
        SimpleMatrix n3 = applyFunction(n2, (val) ->  val * (1 - val));
        n3.printDimensions();
//        n3.print();

//        System.out.println("S*(1-S) * O");
        SimpleMatrix n4 = n3.mult(outputs.transpose()).diag();
        n4.printDimensions();
//        n4.print();

//        System.out.println("E * S*(1-S) * O");
        SimpleMatrix n5 = n4.mult(errors.transpose());
        n5.printDimensions();
//        n5.print();

        SimpleMatrix n6 = applyFunction(n5, (val) -> -learningRate * val);
        n6.printDimensions();
//        n6.print();

        return n6;
    }

//    public void print() {
//        hiddenWeights.print();
//        outputWeights.print();
//    }

//    public NeuralNetwork(int i) {
//
//
//
//        System.out.println(sigmoid.apply(2.3d) * (1 - sigmoid.apply(2.3d)));
//        System.out.println(sigmoid.apply(2.4d) * (1 - sigmoid.apply(2.4d)));
//
//
//
//        inputNodes = 2;
//        outputNodes = 2;
//        learningRate = 0.1d;
//
//        outputWeights = new SimpleMatrix(2, 2, true, 2.0, 3.0, 1.0, 4.0);
//        outputWeights.print();
//
//        SimpleMatrix inputs = new SimpleMatrix(2, 1, true, 0.4, 0.5);
//
//        SimpleMatrix outputs = new SimpleMatrix(2, 1, true, 0.4, 0.5);
//        SimpleMatrix errors = new SimpleMatrix(2, 1, true, 0.8, 0.5);
//
//
//
//
//        //SimpleMatrix delta = deltaError(inputs, outputs, errors);
//        deltaError(inputs, outputs, errors);
//
//
//
//        //delta.print();
//
//
//
//    }


//    public NeuralNetwork() {
//        inputNodes = 3;
//        hiddenNodes = 3;
//        outputNodes = 3;
//        learningRate = 0.1d;
//
//        hiddenWeights = new SimpleMatrix(hiddenNodes, inputNodes, false, 0.9, 0.3, 0.4, 0.2, 0.8, 0.2, 0.1, 0.5, 0.6);
//        hiddenWeights.print();
//
//        outputWeights = new SimpleMatrix(hiddenNodes, inputNodes, false, 0.3, 0.7, 0.5, 0.6, 0.5, 0.2, 0.8, 0.1, 0.9);
//        outputWeights.print();
//    }





    private SimpleMatrix applyFunction(SimpleMatrix matrix, Function<Double, Double> func) {
        SimpleMatrix returnMatrix = new SimpleMatrix(matrix);
        int size = returnMatrix.getNumElements();
        for( int i = 0; i < size; i++ ) {
            returnMatrix.set(i, func.apply(matrix.get(i)));
        }
        return returnMatrix;
    }

    private SimpleMatrix applySigmoid(SimpleMatrix matrix) {
        return applyFunction(matrix, sigmoid);
    }

    private double round(double val) {
        double r = (Math.round(val) * 1000d) / 1000d;
        if (r == 0) {
            r += 0.001d;
        }
        if (r == 1) {
            r -= 0.001d;
        }
        return r;
    }

    private SimpleMatrix minusElementFrom(SimpleMatrix matrix, double val) {
        SimpleMatrix returnMatrix = new SimpleMatrix(matrix);
        int size = returnMatrix.getNumElements();
        for( int i = 0; i < size; i++ ) {
            returnMatrix.set(i, val - matrix.get(i));
        }
        return returnMatrix;
    }

    private SimpleMatrix multElement(SimpleMatrix matrix, double val) {
        SimpleMatrix returnMatrix = new SimpleMatrix(matrix);
        int size = returnMatrix.getNumElements();
        for( int i = 0; i < size; i++ ) {
            returnMatrix.set(i, round(matrix.get(i) * val));
        }
        return returnMatrix;
    }

    private SimpleMatrix queryOutputs(SimpleMatrix inputs) {
        SimpleMatrix hiddenInputs = hiddenWeights.mult(inputs);
        SimpleMatrix hiddenOutputs = applySigmoid(hiddenInputs);

        SimpleMatrix finalInputs = outputWeights.mult(hiddenOutputs);
        SimpleMatrix finalOutputs = applySigmoid(finalInputs);

        return finalOutputs;
    }

    public int query(double[] inputs) {
        SimpleMatrix inputMatrix = adaptInputMatrix(inputs);
        SimpleMatrix finalOutputs = queryOutputs(inputMatrix);

        finalOutputs.transpose().print();

        int result = 0;
        double max = Double.MIN_VALUE;
        int size = finalOutputs.getNumElements();
        for( int i = 0; i < size; i++ ) {
            if (finalOutputs.get(i) > max) {
                max = finalOutputs.get(i);
                result = i;
            }
        }
        return result;
    }

    private SimpleMatrix adaptInputMatrix(double[] data) {
        return new SimpleMatrix(data.length, 1, true, data);
        //return new SimpleMatrix(28, 28, false, data);
    }

    private SimpleMatrix createVectorMatrixFromArray(double[] data) {
        return new SimpleMatrix(data.length, 1, true, data);
    }

    private boolean isTargetAchievable(int target){
        if (target >= 0 && target < outputNodes) {
            return true;
        }
        return false;
    }
}
