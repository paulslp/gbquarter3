package gb;

import java.lang.reflect.Method;
import java.util.List;

public class AnalyseTestResult {
    private List<Method> testMethods;
    private int beforeSuiteMethodIndex;
    private int afterSuiteMethodIndex;

    public AnalyseTestResult(List<Method> testMethods, int beforeSuiteMethodIndex, int afterSuiteMethodIndex) {
        this.testMethods = testMethods;
        this.beforeSuiteMethodIndex = beforeSuiteMethodIndex;
        this.afterSuiteMethodIndex = afterSuiteMethodIndex;
    }

    public List<Method> getTestMethods() {
        return testMethods;
    }

    public int getBeforeSuiteMethodIndex() {
        return beforeSuiteMethodIndex;
    }

    public int getAfterSuiteMethodIndex() {
        return afterSuiteMethodIndex;
    }
}
