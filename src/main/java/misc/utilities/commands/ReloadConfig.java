package misc.utilities.commands;

import misc.utilities.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            Utilities.getPlugin().reloadConfig();
            sender.sendRichMessage("<green>Configuration has been successfully reloaded!");
        } else {
            sender.sendRichMessage("<red>Unknown argument.");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return args.length == 1 ? List.of("reload") : List.of();
    }
}