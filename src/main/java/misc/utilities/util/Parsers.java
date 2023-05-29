package misc.utilities.util;

import org.bukkit.Sound;

import java.time.Duration;
import java.util.Optional;

public class Parsers {

    public static String time(Duration duration) {
        final StringBuilder builder = new StringBuilder();

        int seconds = duration.toSecondsPart();
        int minutes = duration.toMinutesPart();
        int hours = duration.toHoursPart();
        long days = duration.toDaysPart();

        if (days != 0) builder.append(String.format("<red>%s<gold>d<yellow>, ", days < 10 ? "0" + days : days));
        if (hours != 0) builder.append(String.format("<red>%s<gold>h<yellow>, ", hours < 10 ? "0" + hours : hours));
        if (minutes != 0) builder.append(String.format("<red>%s<gold>m<yellow>, ", minutes < 10 ? "0" + minutes : minutes));
        if (seconds != 0) builder.append(String.format("<red>%s<gold>s<yellow>, ", seconds < 10 ? "0" + seconds : seconds));

        return builder.substring(0, builder.length() - 2).stripTrailing();
    }

    public static Optional<Sound> sound(String str) {
        str = str.toUpperCase();

        try {
            return Optional.of(Sound.valueOf(str));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}