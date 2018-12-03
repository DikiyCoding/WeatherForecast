package example.homework10.handlers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UnixHandler {

    public static String fromUNIXtoReadable(int unixSeconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);       // выбераем, какой формат нужен
        return dateFormat.format(unixSeconds * 1000L);
    }
}