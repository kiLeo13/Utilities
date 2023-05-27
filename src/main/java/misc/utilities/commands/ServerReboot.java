package misc.utilities.commands;

import misc.utilities.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class ServerReboot implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) return true;

        Duration time;
        String reason = args.length < 2 ? null : getReason(args);

        try {
            long input = args.length < 1 ? 10 : Long.parseLong(args[0]);
            time = Duration.ofSeconds(input);
        } catch (NumberFormatException e) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("cancel")) {
                Util.cancelRestartSchedule(args.length >= 2 ? reason : null);

                sender.sendRichMessage("<yellow><i>Cancelling restart...");
                return true;
            }

            sender.sendRichMessage("<red>Invalid time format! Please provide a number (in seconds).");
            return true;
        }

        Util.restart(time, reason);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 1 -> {
                return List.of("<time>", "cancel");
            }

            case 2 -> {
                return List.of("<reason>");
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
}