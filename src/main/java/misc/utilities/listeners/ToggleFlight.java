package misc.utilities.listeners;

import misc.utilities.Utilities;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class ToggleFlight implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onToggleFlight(PlayerToggleFlightEvent event) {

        Player player = event.getPlayer();
        boolean isEnabled = Utilities.getPlugin().getConfig().getBoolean("double-jump");

        if (!isEnabled) return;
        if (!player.hasPermission("utility.doublejump") || !event.isFlying()) return;
        if (player.getGameMode() != GameMode.ADVENTURE && player.getGameMode() != GameMode.SURVIVAL) return;

        perform(player);
    }

    private void perform(Player player) {
        player.setVelocity(new Vector(0, 1, 0));
        player.setAllowFlight(false);
    }
}