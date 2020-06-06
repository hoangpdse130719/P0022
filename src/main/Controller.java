/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static java.lang.Math.sqrt;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author phamduchoang
 */
public class Controller {

    Calcutator main;
    BigDecimal result = new BigDecimal("0");
    BigDecimal memoryNumber = new BigDecimal("0");
    String operator = "";
    boolean newNum = true;

    public Controller(Calcutator main) {
        this.main = main;
    }

    public String addDigit(String textDisplay, String number) {
        if (newNum) {
            if (operator.equals("=")) {
                result = new BigDecimal("0");
            }
            newNum = false;
            return fixNumber(new BigDecimal(number));
        } else if (textDisplay.equals("0")) {
            return fixNumber(new BigDecimal(number));
        } else {
            return (textDisplay + number);
        }
    }

    public String fixNumber(BigDecimal currentNum) {
        String s = currentNum.toString();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                String newString = "";
                int newLength = s.length();
                for (int j = s.length() - 1; j > 1; j--) {
                    if (s.charAt(j) == '0') {
                        newLength = j;
                    } else {
                        break;
                    }
                }
                if (s.charAt(newLength - 1) == '.') {
                    newLength--;
                }
                for (int j = 0; j < newLength; j++) {
                    newString = newString + s.charAt(j);
                }
                s = newString;
                break;
            }
        }
        return s;
    }

    public String calculate(String textDisplay, String currentOperator) {
        String output = textDisplay;

        if (!newNum) {
            BigDecimal num = new BigDecimal(textDisplay);
            switch (operator) {
                case "+":
                    result = result.add(num);
                    break;
                case "-":
                    result = result.subtract(num);
                    break;
                case "x":
                    result = result.multiply(num);
                    break;
                case "/":
                    if (num.equals(new BigDecimal("0"))) {
                        output = "Error";
                    } else {
                        result = result.divide(num, 12, RoundingMode.HALF_EVEN);
                    }
                    break;
                default:
                    result = new BigDecimal(textDisplay);
                    break;
            }
            if (!output.equals("Error")) {
                output = fixNumber(result);
            }
        }
        operator = currentOperator;
        newNum = true;
        return output;
    }

    public String addDot(String textDisplay) {
        if (!newNum) {
            newNum = false;
            for (int i = 0; i < textDisplay.length(); i++) {
                if (textDisplay.charAt(i) == '.') {
                    return textDisplay;
                }
            }
            return textDisplay + '.';
        } else {
            newNum = false;
            return "0.";
        }
    }

    public String swapBtn() {
        result = new BigDecimal("0").subtract(result);
        newNum = true;
        return fixNumber(result);
    }

    public String flipBtn() {
        if (result.equals(new BigDecimal("0"))) {
            return "Error";
        } else {
            BigDecimal one = new BigDecimal("1");
            result = one.divide(result, 12, RoundingMode.HALF_EVEN);
            newNum = true;
            return fixNumber(result);
        }
    }

    public String percentBtn() {
        result = result.divide(new BigDecimal("100"));
        newNum = true;
        return fixNumber(result);
    }

    public String clearAllBtn() {
        memoryNumber = new BigDecimal("0");
        result = new BigDecimal("0");
        operator = "";
        newNum = true;
        return "0";
    }

    public String sqrtBtn() {
        switch (result.compareTo(new BigDecimal("0"))) {
            case -1:
                return "Error";
            case 0:
                return "0";
            default:
                Double sqrtResult = sqrt(result.doubleValue());
                result = new BigDecimal(sqrtResult);
                result = result.divide(new BigDecimal("1"), 12, RoundingMode.HALF_EVEN);
                newNum = true;
                return fixNumber(result);
        }
    }
    public void MSubBtn(String textDisplay){
        if (!textDisplay.equals("Error")) {
            memoryNumber = memoryNumber.subtract(new BigDecimal(textDisplay));
            newNum = true;
        }
    }
    public void MPlusBtn(String textDisplay){
        if (!textDisplay.equals("Error")) {
            memoryNumber = memoryNumber.add(new BigDecimal(textDisplay));
            newNum = true;
        }
    }
    public void MCBtn(){
        memoryNumber = new BigDecimal("0");
        newNum = true;
    }
    public String MRBtn(){
        result = memoryNumber;
        newNum =true;
        return fixNumber(result);
    }
}
