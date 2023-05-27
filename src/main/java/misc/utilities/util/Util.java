package misc.utilities.util;

import misc.utilities.Utilities;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.*;

public class Util {
    private static final Timer timer = new Timer();

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

    public static void restart(Duration duration, String reason) {
        long time = duration.toSeconds();
        final double pitch = Utilities.getPlugin().getConfig().getDouble("pitch");
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        final String inputSound = Utilities.getPlugin().getConfig().getString("restart-warning-sound");
        final Optional<Sound> sound = fetchSound(inputSound);
        final String broadcast = reason == null
                ? Utilities.getPlugin().getConfig().getString("restart-warning-text")
                : Utilities.getPlugin().getConfig().getString("restart-warning-text-reason");



        players.forEach(p -> {
            if (broadcast != null) p.sendRichMessage(broadcast);

            sound.ifPresent(s -> p.playSound(p, s, SoundCategory.MASTER, 1, (float) pitch));
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            final List<String> warnings = Utilities.getPlugin().getConfig().getStringList("warnings");
            long count = time;

            @Override
            public void run() {

                if (warnings.contains(String.valueOf(count))) {
                    for (Player p : players) {
                        if (broadcast != null) p.sendMessage(MiniMessage.miniMessage().deserialize(broadcast, Placeholder.parsed("<time>", count < 10 ? "0" + count : String.valueOf(count))));

                        sound.ifPresent(s -> p.playSound(p, s, SoundCategory.MASTER, 1, (float) pitch));
                    }
                }

                if (count < 0) {
                    Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<yellow><i>Shutting server down..."));
                    Bukkit.getServer().shutdown();
                    this.cancel();
                }

                count--;
            }
        }, 0L, 1000L);
    }

    private static Optional<Sound> fetchSound(String str) {
        str = str.toUpperCase();

        try {
            return Optional.of(Sound.valueOf(str));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static void log(String x) {
        Bukkit.getLogger().info(Utilities.PREFIX + " " + x);
    }

    private String formatTime(Duration duration) {
        final StringBuilder builder = new StringBuilder();

        int seconds = duration.toSecondsPart();
        int minutes = duration.toMinutesPart();
        int hours = duration.toHoursPart();
        int days = duration.toHoursPart();

        if (days != 0) builder.append(String.format("<red>%s<yellow>d, ", days < 10 ? "0" + days : days));
        if (hours != 0) builder.append(String.format("<red>%s<yellow>h, ", hours < 10 ? "0" + hours : hours));
        if (minutes != 0) builder.append(String.format("<red>%s<yellow>m, ", minutes < 10 ? "0" + minutes : minutes));
        if (seconds != 0) builder.append(String.format("<red>%s<yellow>s, ", seconds < 10 ? "0" + seconds : seconds));

        return builder.substring(0, builder.length() - 2).stripTrailing();
    }
}