package misc.utilities;

import misc.utilities.commands.ReloadConfig;
import misc.utilities.commands.ServerReboot;
import misc.utilities.listeners.BoneMealBlocks;
import misc.utilities.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Utilities extends JavaPlugin {
    public static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.DARK_GREEN + "Utilities" + ChatColor.GOLD + "]";
    private static Plugin plugin;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        plugin = this;
        Util.log(ChatColor.GREEN +  " Hello! I have been successfully enabled.");

        // Registering the plugin commands
        registerCommands();

        // Registering the plugin listeners
        registerEvents();
    }

    @Override
    public void onDisable() {
        Util.log(ChatColor.GREEN + " Bye bye!");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private void registerCommands() {
        PluginCommand utility = this.getCommand("utility");
        if (utility != null) utility.setExecutor(new ReloadConfig());

        PluginCommand reboot = this.getCommand("reboot");
        if (reboot != null) reboot.setExecutor(new ServerReboot());
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new BoneMealBlocks(), this);
    }
}
