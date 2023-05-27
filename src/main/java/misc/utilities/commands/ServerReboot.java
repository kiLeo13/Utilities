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
import java.util.Collection;
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

        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        String reason = args.length < 2 ? null : getReason(args);

        Util.restart(time, reason);

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
}