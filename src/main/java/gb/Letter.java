package gb;

public class Letter {

    private final String[] letters;

    private int index;

    public Letter(String[] letters, int index) {
        this.letters = letters;
        this.index = index;
    }

    public String getCurrentLetter() {
        return letters[index];
    }

    public int getIndex() {
        return index;
    }

    public void refreshIndex() {
        index = (index < letters.length - 1) ? index + 1 : 0;
    }
}
