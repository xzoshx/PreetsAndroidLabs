package com.cst2335.Kaur0918;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/** This function asks the user to input a password and
 * checks the password's complexity to meet all the requirements.
 * If the password contains any missing requirement,
 * it displays the respective message to the user.
 * @author Sukhmanpreet Kaur
 * @version 1.0 **/
public class MainActivity extends AppCompatActivity {

    /** This variable holds the text at the centre of the screen **/
    private TextView textView = null;

    /** This variable holds user-entered password **/
    private EditText editText = null;

    /** This is the login button and on clicking this button, password's complexity will be checked
     * to meet all the requirements **/
    private Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.passwordTextView);
        editText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(clk -> {
            String password = editText.getText().toString();
            boolean isComplex = checkPasswordComplexity(this, password);
            if (isComplex) {
                textView.setText("Your password meets the requirements");
            } else {
                textView.setText("You shall not pass!");
            }

        });
    }

    /**This function checks if the entered password string has all the requirement that are
     * upper case letter,lower case, special symbol and a number.
     *
     * @param context The context for the toast message
     * @param pw The String Object
     * @return true if all the requirements are completed
     */
    boolean checkPasswordComplexity(Context context, String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);

            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }
        if (!foundUpperCase) {
            Toast.makeText(context, "Uppercase letter is missing!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(context, "Lowercase letter is missing!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(context, "Number is missing!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(context, "Special character is missing!!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true; // All requirements met
        }

    }

    /**
     * This function defines some special characters to be used in the password.
     * If any of these is found, it returns true
     * otherwise, false.
     *
     * @param c The special characters required to be used in the password
     * @return false if the value will not match with at least one of the the special characters
     */
    public boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}