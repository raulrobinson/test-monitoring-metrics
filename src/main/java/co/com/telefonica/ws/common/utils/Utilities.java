package co.com.telefonica.ws.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * The type Utilities.
 */
public class Utilities {

    /**
     * GENERATE TIMESTAMP WITH TZ.
     *
     * @return String string
     */
    public static String clientDateStringWithTZ(
    ) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .format(new java.util.Date(System.currentTimeMillis()));
    }

    /**
     * LATENCY CALCULATOR FOR RESPONSE TIME OF SERVICE
     *
     * @param start long
     * @param end   long
     * @return String string
     */
    public static String latencyCalculator(long start, long end) {
        return (end - start) + "ms";
    }

    /**
     * Description: Método para obtener el valor del timestamp, es decir la fecha y hora de
     * 				respuesta del servicio en formato YYYY-MM-DDThh:mm:ss[Z|(+|-)hh:mm]
     *
     * @return the timestamp value
     */
    public static String getTimestampValue() {
        ZoneId zoneIdCo = ZoneId.of("America/Bogota");
        ZonedDateTime now = ZonedDateTime.now(zoneIdCo);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return now.truncatedTo(ChronoUnit.MILLIS).format(dtf);
    }

    private Utilities() {
    }
}
