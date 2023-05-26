package misc.utilities.commands;

import misc.utilities.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Reload implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 2) {
            sender.sendRichMessage("<red>Too few arguments, please use <yellow>/utility reload");
            return true;
        }

        Utilities.getPlugin().reloadConfig();
        sender.sendRichMessage("<green>Configuration has been successfully reloaded!");

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return args.length == 2 ? List.of("reload") : List.of();
    }
}