package utils;

public class Session {
    private static String explanation;

    public static void setExplanation(String text) {
        explanation = text;
        System.out.println("qetu");
        System.out.println(explanation);

    }

    public static String getExplanation() {
        return explanation;
    }
}