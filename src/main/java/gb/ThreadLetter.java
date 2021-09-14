package gb;

class ThreadLetter implements Runnable {

    private final int printIndex;

    private final Letter letter;

    ThreadLetter(Letter letter, int printIndex) {
        this.letter = letter;
        this.printIndex = printIndex;
    }

    public void run() {
        synchronized (letter) {
            for (int i = 1; i <= 5; i++) {
                while (letter.getIndex() != printIndex) {
                    try {
                        letter.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(letter.getCurrentLetter());
                letter.refreshIndex();
                letter.notifyAll();
            }
        }
    }
}
