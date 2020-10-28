package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static TextView resultField;
    static TextView numberField;
    static TextView operationField;
    static Double operand = null;
    static String lastOperation = "=";
    static String toast = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultField =(TextView) findViewById(R.id.resultField);
        numberField = (TextView) findViewById(R.id.numberField);
        operationField = (TextView) findViewById(R.id.operationField);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onNumberClick(View view){
        Calculation.onNumberClickCalc(view);
        if(!toast.equals("")){
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            toast="";
        }
    }

    public void onOperationClick(View view){
        Calculation.onOperationClickCalc(view);
        if(!toast.equals("")){
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            toast="";
        }
    }
}