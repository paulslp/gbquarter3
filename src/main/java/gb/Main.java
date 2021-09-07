package gb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * 1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
 * 2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт). Может пригодиться следующая конструкция:
 * ArrayList<InputStream> al = new ArrayList<>(); ... Enumeration<InputStream> e = Collections.enumeration(al);
 * 3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
 * Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль.
 * Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.
 */
public class Main {

    public static final int PAGE_SIZE = 1800;

    public static void main(String[] args) throws IOException {
//1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
        printContentFile("files/ex1.txt");
//2.  Последовательно сшить 5 файлов в один (файлы примерно 100 байт)
        ArrayList<InputStream> inputStreams = createInputStreams();

        createFile("files/ex2/ex2.txt");

        printDirInfo("files/ex2");

        writeInputStreamsContentToFile(inputStreams, "files/ex2/ex2.txt");

        printContentFile("files/ex2/ex2.txt");

//3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb)
        int pageNumber = getPageNumber(new Scanner(System.in));
        if (pageNumber > 0) {
            createEx3PageFile("files/ex3.txt", pageNumber);
        }
    }

    private static void printContentFile(String filePath) {
        System.out.println("Читаем файл " + filePath + " и выводим его содержимое в консоль...\n");
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            int x;
            while ((x = isr.read()) > -1) {
                System.out.print((char) x);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<InputStream> createInputStreams() throws FileNotFoundException {
        System.out.println("\n2. Последовательно добавляем потоки файлов ex21-ex25.txt в список потоков...");
        ArrayList<InputStream> files = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            files.add(new FileInputStream("files/ex2/ex2" + i + ".txt"));
        }
        return files;
    }

    private static void createFile(String filePath) throws IOException {
        System.out.println("\n Создаем файл " + filePath);
        File newFile = new File(filePath);
        if (!newFile.createNewFile()) {
            newFile.delete();
            newFile.createNewFile();
        }
    }

    private static void printDirInfo(String dirPath) {
        System.out.println("Список файлов директории " + dirPath);
        File dir = new File(dirPath);
        String[] dirElements = dir.list();
        if (dirElements != null && dirElements.length > 0) {
            Arrays.stream(dir.list()).forEach(System.out::println);
        } else {
            System.out.println("\nПапка " + dirPath + " не содержит элементов");
        }
    }

    private static void writeInputStreamsContentToFile(ArrayList<InputStream> inputStreamsContent,
                                                       String filePath) throws IOException {
        System.out.println("\n2.2. Записываем данные потоков в общий файл " + filePath);
        try (SequenceInputStream is = new SequenceInputStream(Collections.enumeration(inputStreamsContent))) {
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                int x;
                while ((x = is.read()) != -1) {
                    out.write(x);
                }
            }
        }
    }

    private static int getPageNumber(Scanner scanner) {
        System.out.println("Введите номер страницы: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Значение " + scanner.next() + " не является числом.");
            return 0;
        }
        return scanner.nextInt();
    }

    private static void createEx3PageFile(String filePath, int pageNumber) throws IOException {
        long startTimeStamp = System.currentTimeMillis();
        long fileSize = new File(filePath).length();
        byte[] buffer = new byte[PAGE_SIZE];
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            int startIndex = PAGE_SIZE * (pageNumber - 1);
            if (startIndex < fileSize) {
                raf.seek(startIndex);
                raf.read(buffer, 0, PAGE_SIZE);
                System.out.println(new String(buffer, StandardCharsets.UTF_8));
            } else {
                System.out.println("Файл " + filePath + " имеет меньший размер");
            }
            System.out.println("\n Общее время чтения файла " + filePath + ": " +
                    (System.currentTimeMillis() - startTimeStamp) + " мс");
        }
    }
}
