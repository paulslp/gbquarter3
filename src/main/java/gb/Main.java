package gb;

public class Main {

    public static final int START_LETTER_INDEX = 0;

    public static void main(String[] args) {
        final Letter letter = new Letter(START_LETTER_INDEX);
        for (int printIndex = 0; printIndex < 3; printIndex++) {
            new Thread(new ThreadLetter(letter, printIndex)).start();
        }
    }
}
