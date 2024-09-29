package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultField;
    private EditText numberField;
    private TextView operationField;

    private Double operand = null;
    private String lastOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString("OPERATION", lastOperation);
        if(operand != null)
        {
            outState.putDouble("OPERAND", operand);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState)
    {
        super.onRestoreInstanceState(saveInstanceState);
        lastOperation = saveInstanceState.getString("OPERATION");
        operand = saveInstanceState.getDouble("OPERAND");
        if (saveInstanceState.containsKey("OPERAND")) {
            operand = saveInstanceState.getDouble("OPERAND");
        } else {
            operand = null;
        }

        if (operand != null) {
            resultField.setText(operand.toString());
        } else {
            resultField.setText("");
        }
        operationField.setText(lastOperation);
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        numberField.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {

            operand = null;
        }
    }

    public void onOperationClick(View view) {
        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {
        if (operand == null) {
            operand = number;
        }
            if (operation.equals("C")) {
                numberField.setText("");
                resultField.setText("");
                operationField.setText("");
                operand = null;
                lastOperation = "=";
            }

            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "Pow":
                    operand = Math.pow(number, 2);
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
                case "*":
                    operand *= number;
                    break;
                case "/":
                    if (number != 0) {
                        operand /= number;
                    } else {
                        // Handle division by zero
                        resultField.setText("Error");
                        return;
                    }
                    break;
        }
        resultField.setText(operand.toString().replace(',', '.'));
        numberField.setText("");
    }
}
