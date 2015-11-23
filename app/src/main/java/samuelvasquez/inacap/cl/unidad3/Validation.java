package samuelvasquez.inacap.cl.unidad3;


import android.widget.EditText;

import java.util.regex.Pattern;

public class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String NUMERIC_REGEX = "\\d+";
    private static final String DOUBLE_REGEX = "^[+-]?[0-9]+(\\.[0-9]+)?$";

    // Error Messages
    private static final String PHONE_MSG = "###-#######";

    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, editText.getContext().getText(R.string.validacion_email).toString(), required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    public static boolean isNumber(EditText editText, boolean required) {
        return isValid(editText, NUMERIC_REGEX, editText.getContext().getText(R.string.validacion_numerico).toString(), required);
    }

    public static boolean isDouble(EditText editText, boolean required) {
        return isValid(editText, DOUBLE_REGEX, editText.getContext().getText(R.string.validacion_decimal).toString(), required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }

    public static boolean hasText(EditText editText)
    {
        return hasText(editText, 1);
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText, int largo_minimo) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(editText.getContext().getText(R.string.validacion_requerido).toString());
            return false;
        }
        else if (text.length() < largo_minimo) {
            editText.setError(editText.getContext().getText(R.string.validacion_largo_minimo).toString()
                    + " " + String.valueOf(largo_minimo) + " "
                    + editText.getContext().getText(R.string.validacion_caracteres).toString());
            return false;
        }

        return true;
    }
}