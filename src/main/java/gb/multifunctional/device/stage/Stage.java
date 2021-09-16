package gb.multifunctional.device.stage;

import gb.multifunctional.device.Document;

public abstract class Stage {

    protected String description;

    protected int speed;

    public Stage(int speed) {
        this.speed = speed;
    }

    public void go(Document document, String taskName) {
        try {

            System.out.println(document.getName() + " начал этап: " + description
                    + " задания " + taskName);
            Thread.sleep(document.getListCount() * speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(document.getName() + " закончил этап: " + description
                    + " задания " + taskName);

        }
    }
}
