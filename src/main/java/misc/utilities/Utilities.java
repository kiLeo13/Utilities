package misc.utilities;

import misc.utilities.commands.Reload;
import misc.utilities.listeners.BlockCactus;
import misc.utilities.listeners.BlockSugarCane;
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
        System.out.println(PREFIX + ChatColor.GREEN +  " Hello! I have been successfully enabled.");

        // Registering the plugin commands
        registerCommands();

        // Registering the plugin listeners
        registerEvents();
    }

    @Override
    public void onDisable() {
        System.out.println(PREFIX + ChatColor.GREEN + " Bye bye!");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private void registerCommands() {
        PluginCommand utility = this.getCommand("utility");
        if (utility != null) utility.setExecutor(new Reload());
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new BlockSugarCane(), this);
        this.getServer().getPluginManager().registerEvents(new BlockCactus(), this);
    }
}
