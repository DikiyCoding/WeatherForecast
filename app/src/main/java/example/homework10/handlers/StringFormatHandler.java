package example.homework10.handlers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StringFormatHandler {

    private static String directions[] = {"north", "northeast", "east", "southeast", "south", "southwest", "west", "northwest"};

    public static String fromUNIXtoReadable(int unixSeconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);       // выбераем, какой формат нужен
        return dateFormat.format(unixSeconds * 1000L);
    }

    public static String getDirection(int degrees) {
        return directions[ Math.round(((degrees % 360) / 45)) % 8];
    }
}
