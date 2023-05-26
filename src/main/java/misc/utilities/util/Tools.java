package misc.utilities.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Tools {

    public static Block getFreeBlock(Location location, Material column) {

        for (int i = 0; i < location.getWorld().getMaxHeight(); i++) {
            Block block = location.add(0, 1, 0).getBlock();

            if (block.getType() != column && block.getType() != Material.AIR)
                return null;

            if (block.getType() == Material.AIR)
                return block;
        }

        return null;
    }
}