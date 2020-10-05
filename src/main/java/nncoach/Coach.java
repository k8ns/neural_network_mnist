package nncoach;

import nn.Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Coach {

    private String trainPath;
    private String testPath;
    private int inputSize;
    private double scale;

    public Coach(String trainPath, String testPath, int inputSize, double scale) {
        this.trainPath = trainPath;
        this.testPath = testPath;
        this.inputSize = inputSize;
        this.scale = scale;
    }

    public void train(Network n) {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.trainPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] input = line.split(",");

                int target = Integer.parseInt(input[0]);
                double[] targets = new double[]{0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d, 0.01d};
                targets[target] = 0.99d;


                double[] inputs = new double[this.inputSize];
                for (int i = 1; i <= inputs.length; i++) {
                    inputs[i-1] = getInputValue(input[i]);
                }

                TrainCase trainCase = new TrainCase(target, inputs);

                n.train(inputs, targets);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void test(Network n) {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.testPath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                TestCase testCase = readTestRowFromLine(line);
                TestResult res = makeTest(n, testCase);
                ColorPrinter.print(res);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private TestResult makeTest(Network n, TestCase testCase) {
        double[] results = n.query(testCase.getInputs());
        return new TestResult(testCase.getExpectation(), results);
    }

    private TestCase readTestRowFromLine(String line) {
        String[] input = line.split(",");

        int expectation = Integer.parseInt(input[0]);
        double[] inputs = new double[inputSize];

        for (int i = 1; i <= inputs.length; i++) {
            inputs[i-1] = getInputValue(input[i]);
        }

        return new TestCase(expectation, inputs);
    }

    private double getInputValue(String val) {
        double value = Double.parseDouble(val) / scale * 0.99d;
        if (value > 0) {
            return value;
        }
        return 0.01d;
    }
}
