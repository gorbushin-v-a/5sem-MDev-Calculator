package com.example.calculatorapp;

import android.view.View;
import android.widget.Button;

import java.math.BigDecimal;

public class Calculation {

    public static void onNumberClickCalc(View view){

        Button button = (Button)view;
        if(button.getText().equals("AC")){
            MainActivity.operand = null;
            MainActivity.lastOperation = "=";;
            MainActivity.numberField.setText("");
            MainActivity.operationField.setText("");
            MainActivity.resultField.setText("");
        }else if(button.getText().equals("⌫")) {
            if (MainActivity.numberField.getText() != null){
                String str = MainActivity.numberField.getText().toString();
                if(str.length()!=0) {
                    MainActivity.numberField.setText(str.substring(0, str.length() - 1));
                }
            }
        }else{
            if (MainActivity.numberField.getText().toString().contains(",")) {
                if (!button.getText().equals(",")) {
                    if (MainActivity.numberField.getText().toString().length() - MainActivity.numberField.getText().toString().indexOf(",") < 5) {
                        MainActivity.numberField.append(button.getText());
                    } else {
                        MainActivity.toast = "Слишком много цифр";
                    }
                }
            } else {
                if (MainActivity.numberField.getText().length() < 9) {
                    MainActivity.numberField.append(button.getText());
                } else {
                    if (button.getText().equals(",")) {
                        MainActivity.numberField.append(button.getText());
                    } else {
                        MainActivity.toast = "Слишком много цифр";
                    }
                }
            }
        }

        if(MainActivity.lastOperation.equals("=") && MainActivity.operand!=null){
            MainActivity.operand = null;
        }
    }

    public static void onOperationClickCalc(View view){

        Button button = (Button)view;
        String op = button.getText().toString();
        String number = MainActivity.numberField.getText().toString();
        if(number.length()>0){
            number = number.replace(',', '.');
            try{
                performUnaryOperation(op);
            }catch (NumberFormatException ex){
                MainActivity.numberField.setText("");
            }
            try{
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                MainActivity.numberField.setText("");
            }
        }else {
            try{
                performUnaryOperation(op);
            }catch (Exception ex){
                MainActivity.numberField.setText("");
            }
        }
        MainActivity.lastOperation = op;
        MainActivity.operationField.setText(MainActivity.lastOperation);
    }

    private static void performUnaryOperation(String op) {
        if(MainActivity.operand !=null) {
            if (op.equals("-/+")) {
                MainActivity.operand *= -1;
            }
            updateResult();
        }
    }

    private static void performOperation(Double number, String operation){

        if(MainActivity.operand ==null){
            MainActivity.operand = number;
        }
        else{
            if(MainActivity.lastOperation.equals("=")){
                MainActivity.lastOperation = operation;
            }
            switch(MainActivity.lastOperation){
                case "=":
                    MainActivity.operand =number;
                    break;
                case "/":
                    if(number==0){
                        MainActivity.toast = "На ноль делить нельзя";
                    }
                    else{
                        MainActivity.operand /=number;
                    }
                    break;
                case "*":
                    MainActivity.operand *= number;
                    break;
                case "+":
                    MainActivity.operand += number;
                    break;
                case "-":
                    MainActivity.operand -= number;
                    break;
                case "%":
                    if(number <= 100 && number >=0) {
                        MainActivity.operand = MainActivity.operand / 100 * number;
                    }else{
                        MainActivity.toast = "Процент может быть равен от 0 до 100";
                    }
                    break;
            }
        }
        updateResult();
    }

    private static void updateResult() {
        BigDecimal result = BigDecimal.valueOf(MainActivity.operand).setScale(4, BigDecimal.ROUND_HALF_UP);
        Double result1 = result.doubleValue();
        MainActivity.resultField.setText(result1.toString().replace('.', ','));
        MainActivity.numberField.setText("");
    }
}
