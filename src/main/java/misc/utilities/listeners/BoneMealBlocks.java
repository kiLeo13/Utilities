package misc.utilities.listeners;

import misc.utilities.Utilities;
import misc.utilities.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class BoneMealBlocks implements Listener {

    @EventHandler
    public void onBoneMeal(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        ItemStack handItem = player.getEquipment().getItemInMainHand();
        World world = player.getWorld();

        if (clickedBlock == null) return;

        // We have to make this check as two events are fired (one for each hand)
        // Along with checking if the item in their hand is a bone meal
        if (event.getHand() == EquipmentSlot.OFF_HAND || handItem.getType() != Material.BONE_MEAL) return;

        if (player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SPECTATOR) return;

        switch (clickedBlock.getType()) {

            // If you bone meal a sugar cane
            case SUGAR_CANE: {
                boolean canGrow = Utilities.getPlugin().getConfig().getBoolean("bone-meal-sugar-cane");
                if (!canGrow) return;

                Block sugarCane = Util.getFreeBlock(clickedBlock.getLocation(), Material.SUGAR_CANE);
                if (sugarCane == null) return;

                sugarCane.setType(Material.SUGAR_CANE);
                break;
            }

            // If you bone meal a cactus
            case CACTUS: {
                boolean canGrow = Utilities.getPlugin().getConfig().getBoolean("bone-meal-cactus");
                if (!canGrow) return;

                Block cactus = Util.getFreeBlock(clickedBlock.getLocation(), Material.CACTUS);
                if (cactus == null) return;

                cactus.setType(Material.CACTUS);
                break;
            }

            default: return;
        }

        world.spawnParticle(Particle.VILLAGER_HAPPY, clickedBlock.getLocation().add(0.5, 0.5, 0.5), 5, 0.3, 0.3, 0.3);
        world.playSound(clickedBlock.getLocation(), Sound.ITEM_BONE_MEAL_USE, SoundCategory.AMBIENT, 1, 1);
        player.swingMainHand();

        if (player.getGameMode() == GameMode.SURVIVAL)
            player.getEquipment().getItemInMainHand().subtract(1);
    }
}