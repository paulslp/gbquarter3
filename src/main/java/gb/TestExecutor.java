package gb;

import gb.lib.AfterSuite;
import gb.lib.BeforeSuite;
import gb.lib.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

public class TestExecutor {


    public static void start(Class<?> classWithTest) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = classWithTest.getDeclaredMethods();

        AnalyseTestResult result = checkAndFillMethods(methods);

        List<Method> testMethods = result.getTestMethods();
        sortTestMethodsByPriority(testMethods);

        methods[result.getBeforeSuiteMethodIndex()].invoke(null);

        testMethods.forEach(method -> {
            try {
                method.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        methods[result.getAfterSuiteMethodIndex()].invoke(null);
    }

    private static AnalyseTestResult checkAndFillMethods(Method[] methods) {
        int beforeSuiteCount = 0;
        int afterSuiteCount = 0;
        int beforeSuiteMethodIndex = -1;
        int afterSuiteMethodIndex = -1;
        List<Method> testMethods = new ArrayList<>();
        for (int methodIndex = 0; methodIndex < methods.length; methodIndex++) {
            if (methods[methodIndex].isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteCount++;
                if (beforeSuiteCount > 1) {
                    throw new RuntimeException("Аннотация BeforeSuite встречается более одного раза");
                } else {
                    beforeSuiteMethodIndex = methodIndex;
                }
            } else if (methods[methodIndex].isAnnotationPresent(AfterSuite.class)) {
                afterSuiteCount++;
                if (afterSuiteCount > 1) {
                    throw new RuntimeException("Аннотация AfterSuite встречается более одного раза");
                } else {
                    afterSuiteMethodIndex = methodIndex;
                }
            }
            if (methods[methodIndex].isAnnotationPresent(Test.class)) {
                testMethods.add(methods[methodIndex]);
            }
        }
        return new AnalyseTestResult(testMethods, beforeSuiteMethodIndex, afterSuiteMethodIndex);
    }

    private static void sortTestMethodsByPriority(List<Method> testMethods) {
        testMethods.sort((o1, o2) ->
                o2.getAnnotation(Test.class).priority() - o1.getAnnotation(Test.class).priority());
    }
}
