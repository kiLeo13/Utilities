package misc.utilities.commands;

import misc.utilities.Utilities;
import misc.utilities.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class ReloadConfig implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) return true;

        if (args.length < 1) {
            sender.sendRichMessage("<red>Too few arguments, please use <yellow>/utility reload");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            boolean isEnabledBefore = Utilities.getPlugin().getConfig().getBoolean("double-jump");
            Utilities.getPlugin().reloadConfig();
            boolean isEnabledAfter = Utilities.getPlugin().getConfig().getBoolean("double-jump");
            sender.sendRichMessage("<green>Configuration has been successfully reloaded!");

            if (!isEnabledAfter && isEnabledBefore != isEnabledBefore) {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();

                for (Player p : players)
                    if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR)
                        p.setAllowFlight(false);
            }
        } else {
            sender.sendRichMessage("<red>Unknown argument.");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return args.length == 1 ? Util.suggest(args[0], "reload") : List.of();
    }
}