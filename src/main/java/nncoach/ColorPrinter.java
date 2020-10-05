package nncoach;

public class ColorPrinter {

    public static void print(TestResult result) {
        if (result.isSuccessful()) {
            System.out.print(toGreen("SUCCESS: ") + "expected " + result.getExpectation() + " Got " + result.getActual());
        } else {
            System.out.print(toRed("FAIL: ") + "expected" + result.getExpectation() + " Got " + result.getActual());
        }

        for (int i = 0; i < result.getValues().length; i++) {
            if (i == result.getExpectation() && result.isSuccessful()) {
                System.out.print(toGreen(String.format("% .2f", result.getValues()[i])));
            } else if (i == result.getExpectation()) {
                System.out.print(toYellow(String.format("% .2f", result.getValues()[i])));
            } else if(i == result.getActual()) {
                System.out.print(toRed(String.format("% .2f", result.getValues()[i])));
            } else {
                System.out.printf("% .2f", result.getValues()[i]);
            }
        }
        System.out.println();
    }

    private static String toRed(String string) {
        return "\033[31m" + string + "\033[0m";
    }

    private static String toGreen(String string) {
        return "\033[32m" + string + "\033[0m";
    }

    private static String toYellow(String string) {
        return "\033[33m" + string + "\033[0m";
    }
}
