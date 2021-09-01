package lesson;

public class Book {

    public Book(String title, int length) {
        this.title = title;
        this.length = length;
    }

    private String title;

    private int length;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Box{" +
                "title='" + title + '\'' +
                ", length=" + length +
                '}';
    }
}
