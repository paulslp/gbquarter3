package gb;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы
 * с наборами методов с аннотациями @Test.
 * Для этого у него должен быть статический метод start(), которому в качестве параметра передается
 * или объект типа Class, или имя класса. Из «класса-теста» вначале должен быть запущен метод с
 * аннотацией @BeforeSuite, если такой имеется, далее запущены методы с аннотациями @Test,
 * а по завершению всех тестов – метод с аннотацией @AfterSuite.
 * К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии
 * с которыми будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок
 * не имеет значения. Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать
 * в единственном экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».
 */
public class Main {

    private static final String PATH_TO_BASE_FOLDER = "src/main/java";
    private static final String BASE_PACKAGE_NAME = "gb";
    private static final String LIB_PACKAGE_NAME = "lib";
    private static final String BUILD_PACKAGE_NAME = "build";
    private static final String PATH_SEPARATOR = "/";
    private static final String PATH_TO_MAIN_PACKAGE_FOLDER = PATH_TO_BASE_FOLDER
            + PATH_SEPARATOR + BASE_PACKAGE_NAME;
    private static final String PATH_TO_TEST_LIB_JAVA_CLASSES_FOLDER = PATH_TO_MAIN_PACKAGE_FOLDER
            + PATH_SEPARATOR + LIB_PACKAGE_NAME;
    private static final String PATH_TO_BUILD_FOLDER = PATH_TO_MAIN_PACKAGE_FOLDER
            + PATH_SEPARATOR + BUILD_PACKAGE_NAME;
    private static final String PACKAGE_NAME_FOR_CLASS_LOADING = "gb.lib";
    private static final String JAVA_EXTENTION = ".java";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        URL[] rootFolderUrls = getRootFolderUrls();

        //компилируем и загружаем вспомогательные классы из пакета lib
        compileAndLoadLibClasses(rootFolderUrls);

        //компилируем и загружаем сам тестовый класс
        Class<?> testClass = compileAndLoadJavaClass(rootFolderUrls,
                "CalcTest", PATH_TO_MAIN_PACKAGE_FOLDER, BASE_PACKAGE_NAME);

        //выполняем тест
        TestExecutor.start(testClass);
    }

    private static void compileAndLoadLibClasses(URL[] rootFolderUrls) throws IOException, ClassNotFoundException {
        File file = new File(PATH_TO_TEST_LIB_JAVA_CLASSES_FOLDER);
        List<String> fileNames = getFileNames(file);
        if (!fileNames.isEmpty()) {
            for (String fileName : fileNames) {
                compileAndLoadJavaClass(rootFolderUrls, fileName,
                        PATH_TO_TEST_LIB_JAVA_CLASSES_FOLDER,
                        PACKAGE_NAME_FOR_CLASS_LOADING);
            }
        }
    }

    private static Class<?> compileAndLoadJavaClass(URL[] rootFolderUrls,
                                                    String className,
                                                    String compilePackage,
                                                    String loadPackage
    ) throws IOException, ClassNotFoundException {
        compileJavaFile(className, compilePackage);
        return loadClassAndGet(loadPackage + "." + className, rootFolderUrls);
    }

    private static Class<?> loadClassAndGet(String fileName, URL[] rootFolderUrls) throws IOException, ClassNotFoundException {
        try (URLClassLoader urlClassLoader = URLClassLoader.newInstance(rootFolderUrls)) {
            return urlClassLoader.loadClass(fileName);
        }
    }

    private static URL[] getRootFolderUrls() throws MalformedURLException {
        return new URL[]{new File(PATH_TO_BUILD_FOLDER).toURL()};
    }

    private static void compileJavaFile(String fileName, String pathToFolder) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null,
                "-d", PATH_TO_BUILD_FOLDER,
                pathToFolder.concat("/").concat(fileName.concat(JAVA_EXTENTION)));
    }

    private static List<String> getFileNames(File file) {
        return Arrays.stream(Objects.requireNonNull(file.list()))
                .filter(fileName -> fileName.endsWith(JAVA_EXTENTION))
                .map(fileName -> fileName.replace(JAVA_EXTENTION, ""))
                .collect(Collectors.toList());
    }
}
