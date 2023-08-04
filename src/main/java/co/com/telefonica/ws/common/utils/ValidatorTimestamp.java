package co.com.telefonica.ws.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * *********************************
 * **** NO BORRAR, NO MODIFICAR ****
 * *********************************
 * Description: Validar del Timestamp.
 *
 * @version 2.1.0
 * @autor:  COE-Arquitectura-Telefonica
 * @date:   06-06-2023
 */
public class ValidatorTimestamp implements DateValidator {
    private final String dateFormat;

    public ValidatorTimestamp(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(this.dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
