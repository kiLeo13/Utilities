package misc.utilities.commands;

import misc.utilities.util.Util;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;

public class ServerReboot implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) return true;

        Duration time;

        try {
            long input = args.length < 1 ? 10 : Long.parseLong(args[0]);
            time = Duration.ofSeconds(input);
        } catch (NumberFormatException e) {
            sender.sendRichMessage("<red>Invalid time format! Please provide a number (in seconds).");
            return true;
        }

        String reason = args.length < 2 ? null : getReason(args);
        String formatted = formatTime(time);

        Util.restart(time.toSeconds());

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 1 -> {
                return List.of("time");
            }

            case 2 -> {
                return List.of("reason");
            }

            default -> {
                return List.of();
            }
        }
    }

    private String getReason(String[] args) {
        final StringBuilder builder = new StringBuilder();

        for (String s : args)
            builder.append(s).append(" ");

        return builder.toString().stripTrailing();
    }

    private String formatTime(Duration duration) {
        final StringBuilder builder = new StringBuilder();

        int seconds = duration.toSecondsPart();
        int minutes = duration.toMinutesPart();
        int hours = duration.toHoursPart();
        int days = duration.toHoursPart();

        if (days != 0) builder.append(String.format("<red>%s<yellow>d, ", days < 10 ? "0" + days : days));
        if (hours != 0) builder.append(String.format("<red>%s<yellow>h, ", hours < 10 ? "0" + hours : hours));
        if (minutes != 0) builder.append(String.format("<red>%s<yellow>m, ", minutes < 10 ? "0" + minutes : minutes));
        if (seconds != 0) builder.append(String.format("<red>%s<yellow>s, ", seconds < 10 ? "0" + seconds : seconds));

        return builder.substring(0, builder.length() - 2).stripTrailing();
    }
}