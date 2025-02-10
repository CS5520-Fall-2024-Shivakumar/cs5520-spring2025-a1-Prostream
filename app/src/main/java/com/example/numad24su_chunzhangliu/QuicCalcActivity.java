package com.example.numad24su_chunzhangliu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuicCalcActivity extends AppCompatActivity {

    private TextView tvCalcDisplay;
    private String expression = ""; // now expression

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quic_calc);

        tvCalcDisplay = findViewById(R.id.tvCalcDisplay);

        // bind event to button

        // button 7
        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("7");
            }
        });

        // button 8
        Button btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("8");
            }
        });

        // button 9
        Button btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("9");
            }
        });

        // button +
        Button btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("+");
            }
        });

        // button 4
        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("4");
            }
        });

        // button 5
        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("5");
            }
        });

        // button 6
        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("6");
            }
        });

        // button -
        Button btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("-");
            }
        });

        // button 1
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("1");
            }
        });

        // button 2
        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("2");
            }
        });

        // button 3
        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("3");
            }
        });

        // button =
        Button btnEqual = findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int result = evaluateExpression(expression);
                    expression = String.valueOf(result);
                    tvCalcDisplay.setText(expression);
                } catch (Exception e) {
                    tvCalcDisplay.setText("Error");
                    expression = "";
                }
            }
        });

        // button 0
        Button btn0 = findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("0");
            }
        });

        // button x
        Button btnX = findViewById(R.id.btnX);
        btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() > 0) {
                    expression = expression.substring(0, expression.length() - 1);
                    if (expression.isEmpty()) {
                        tvCalcDisplay.setText("CALC");
                    } else {
                        tvCalcDisplay.setText(expression);
                    }
                }
            }
        });
    }

    /**
     * update textview
     */
    private void appendToExpression(String str) {
        expression += str;
        tvCalcDisplay.setText(expression);
    }

    /**
     * calc expression（example "1+2-3"）
     *
     */
    private int evaluateExpression(String expr) throws Exception {
        if (expr == null || expr.isEmpty()) {
            throw new Exception("Empty Expression");
        }
        int result = 0;
        int currentNumber = 0;
        char operator = '+'; // default '+'
        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (Character.isDigit(ch)) {
                currentNumber = currentNumber * 10 + (ch - '0');
            }
            //
            if (!Character.isDigit(ch) || i == expr.length() - 1) {
                if (operator == '+') {
                    result += currentNumber;
                } else if (operator == '-') {
                    result -= currentNumber;
                }
                operator = ch;  //
                currentNumber = 0;
            }
        }
        return result;
    }
}
