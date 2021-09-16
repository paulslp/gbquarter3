package gb.multifunctional.device;

public class Document {

    private int listCount;
    private String name;

    public Document(int listCount, String name) {
        this.listCount = listCount;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getListCount() {
        return listCount;
    }


}
