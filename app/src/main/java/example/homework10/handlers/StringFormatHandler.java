package example.homework10.handlers;

import java.text.SimpleDateFormat;
import java.util.Locale;

import example.homework10.application.App;

public class StringFormatHandler {

    private static String directions[];

    static {
        switch (App.getInstance().getLocale().toString()) {
            case "en_US":
                directions = new String[]{"north", "northeast", "east", "southeast", "south", "southwest", "west", "northwest"};
                break;
            case "ru_RU":
                directions = new String[]{"северный", "северо-восточный", "восточный", "южно-восточный", "южный", "южно-западный", "западный", "северо-западный"};
                break;
        }
    }

    public static String fromUNIXtoReadable(int unixSeconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);       // выбераем, какой формат нужен
        return dateFormat.format(unixSeconds * 1000L);
    }

    public static String getDirection(int degrees) {
        return directions[ Math.round(((degrees % 360) / 45)) % 8];
    }
}
