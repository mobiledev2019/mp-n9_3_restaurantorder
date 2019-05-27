package com.e15cn2.restaurantorder.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
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

    public static String timeFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH : mm");
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

    public static int compareDate(String picked, String today) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date datePicked = null;
        Date dateToday = null;
        try {
            datePicked = sdf.parse(picked);
            dateToday = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (datePicked.getTime() >= dateToday.getTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String formatFacebookDate(String date) {
        String dd = date.substring(3, 6);
        String MM = date.substring(0, 3);
        String yyyy = date.substring(6, 10);
        return dd + MM + yyyy;
    }

    public static String currencyFormat(double price) {
        NumberFormat format =
                new DecimalFormat("#,##0.00");// #,##0.00 ¤ (¤:// Currency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale

        String currency = String.valueOf(price);
        currency = (!TextUtils.isEmpty(currency)) ? currency : "0";
        currency = currency.trim();
        currency = format.format(Double.parseDouble(currency));
        currency = currency.replaceAll(",", "\\.");

        if (currency.endsWith(".00")) {
            int centsIndex = currency.lastIndexOf(".00");
            if (centsIndex != -1) {
                currency = currency.substring(0, centsIndex);
            }
        }
        currency = String.format("%s đ", currency);
        return currency;
    }
}
