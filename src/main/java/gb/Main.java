package gb;

public class Main {

    public static void main(String[] args) {

        final String[] letters = {"A", "B", "C"};

        final int START_LETTER_INDEX = 0;

        final Letter letter = new Letter(letters, START_LETTER_INDEX);
        for (int printIndex = 0; printIndex < letters.length; printIndex++) {
            new Thread(new ThreadLetter(letter, printIndex)).start();
        }
    }
}
