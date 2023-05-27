package misc.utilities.util;

import misc.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Util {
    private static final Timer timer = new Timer(false);

    public static Block getFreeBlock(Location location, Material column) {
        if (location == null || column == null)
            return null;

        for (int i = 0; i < location.getWorld().getMaxHeight(); i++) {
            Block block = location.add(0, 1, 0).getBlock();

            if (block.getType() != column && block.getType() != Material.AIR)
                return null;

            if (block.getType() == Material.AIR)
                return block;
        }

        return null;
    }

    public static void restart(long time) {
        final List<String> warnings = Utilities.getPlugin().getConfig().getStringList("warnings");

        setInterval(() -> {
            long count = time;

            if (warnings.contains(String.valueOf(count))) {

            }

            count--;
        });
    }

    private static void setInterval(Runnable runnable) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public static void log(String x) {
        Bukkit.getLogger().info(Utilities.PREFIX + " " + x);
    }
}