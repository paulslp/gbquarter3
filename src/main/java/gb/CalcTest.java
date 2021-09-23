package gb;

import gb.lib.AfterSuite;
import gb.lib.BeforeSuite;
import gb.lib.Calculator;
import gb.lib.Test;

public class CalcTest {

    static Calculator calculator;

    @BeforeSuite
    public static void initCalc() {
        calculator = new Calculator();
        System.out.println("CalcTest start");
    }

    @Test(priority = 4)
    public static void checkSum() {
        System.out.println("checkSum result: " + (calculator.plus(3, 5) == 8));
    }

    @Test
    public static void checkDelta() {
        System.out.println("checkDelta result: " + (calculator.minus(3, 5) == -2));
    }

    @Test
    public static void checkMultiply() {
        System.out.println("checkMultiply result: " + (calculator.multiply(3, 5) == 10));
    }

    @Test(priority = 2)
    public static void checkDivision() {
        System.out.println("checkDivision result: " + (calculator.division(9, 3) == 3));
    }

    @AfterSuite
    public static void afterCalc() {
        System.out.println("CalcTest finish");
    }

}