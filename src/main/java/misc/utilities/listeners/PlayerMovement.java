package misc.utilities.listeners;

import misc.utilities.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerMovement implements Listener {
    private static final HashMap<UUID, Boolean> lastGround = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        boolean isDoubleJumpEnabled = Utilities.getPlugin().getConfig().getBoolean("double-jump");
        if (!isDoubleJumpEnabled) return;

        Player player = event.getPlayer();
        boolean wasOnGround = lastGround.containsKey(player.getUniqueId()) && lastGround.get(player.getUniqueId());

        if (player.isOnGround() && !wasOnGround) {
            player.setAllowFlight(true);
            lastGround.put(player.getUniqueId(), true);
        } else
            lastGround.put(player.getUniqueId(), player.isOnGround());
    }
}