package gb;

public class Letter {

    private final String[] letters = {"A", "B", "C"};

    private int index;

    public Letter(int index) {
        this.index = index;
    }

    public String getCurrentLetter() {
        return letters[index];
    }

    public int getIndex() {
        return index;
    }

    public void refreshIndex() {
        index = (index < 2) ? index + 1 : 0;
    }
}
