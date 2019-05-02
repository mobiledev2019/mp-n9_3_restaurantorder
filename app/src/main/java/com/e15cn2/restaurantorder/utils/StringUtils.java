package com.e15cn2.restaurantorder.utils;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isEmpty(EditText... editTexts) {
        boolean isEmpty = false;
        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                isEmpty = true;
                editText.setError("This field must not be empty!");
            }
        }
        return isEmpty;
    }

    public static boolean isLongEnough(EditText... editTexts) {
        boolean isEnough = true;
        for (EditText editText : editTexts) {
            if (editText.getText().toString().length() < 4) {
                isEnough = false;
                editText.setError("You must input at least 4 characters!");
            } else if (editText.getText().toString().length() > 50) {
                isEnough = false;
                editText.setError("You only can input maximum 50 characters!");
            }
        }
        return isEnough;
    }

    public static String dateFormat(Date date) {
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static boolean isEqual(EditText edt1, EditText edt2) {
        if (!edt1.getText().toString().equals(edt2.getText().toString())) {
            edt2.setError("Confirmed password is not correct!");
            return false;
        }
        return true;
    }

    public static boolean isPhone(EditText edtPhone) {
        boolean isPhone = false;
        if (!Pattern.matches("[a-zA-Z]+", edtPhone.getText().toString())) {
            if (edtPhone.getText().toString().length() < 6 || edtPhone.getText().toString().length() > 13) {
                isPhone = false;
                edtPhone.setError("Not valid phone number!");
            } else {
                isPhone = true;
            }
        } else {
            isPhone = false;
        }
        return isPhone;
    }

    public static boolean isEmail(EditText edtEmail) {
        boolean isEmail;
        Pattern p;
        Matcher m;
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        p = Pattern.compile(EMAIL_STRING);
        m = p.matcher(edtEmail.getText().toString());
        isEmail = m.matches();
        if (!isEmail) {
            edtEmail.setError("Not valid email!");
        }
        return isEmail;
    }

    public static String appendTimeToString(String name) {
        Date calendar = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("kkmmssddMMyy");
        String newName = dateFormat.format(calendar).concat("_" + name);
        return newName;
    }
}
