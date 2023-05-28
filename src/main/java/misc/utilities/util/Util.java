package misc.utilities.util;

import misc.utilities.Utilities;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.*;

public class Util {
    private static boolean isRestartScheduled;
    private static Timer timer;

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
        timer = new Timer();

        long time = duration.toSeconds();
        double pitch = Utilities.getPlugin().getConfig().getDouble("restart-sound-pitch");
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        final String inputSound = Utilities.getPlugin().getConfig().getString("restart-warning-sound");
        Optional<Sound> sound = fetchSound(inputSound);
        final String broadcast = getRestartBroadcast(reason);

        isRestartScheduled = true;

        timer.scheduleAtFixedRate(new TimerTask() {
            final List<String> warnings = Utilities.getPlugin().getConfig().getStringList("warnings");
            long count = time;

            @Override
            public void run() {

                if (warnings.contains(String.valueOf(count))) {
                    for (Player p : players) {
                        if (broadcast != null) p.sendMessage(MiniMessage.miniMessage().deserialize(broadcast.replaceAll("<time>", formatTime(Duration.ofSeconds(count)))));

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

    public static String formatTime(Duration duration) {
        String template = Utilities.getPlugin().getConfig().getString("time-placeholder");
        final String templateDefault = "<red><day><gold>d<yellow>, <red><hour><gold>h<yellow>, <red><minute><gold>m<yellow>, <red><second><gold>s<yellow>";
        final HashMap<String, String> placeholders = new HashMap<>();

        int seconds = duration.toSecondsPart();
        int minutes = duration.toMinutesPart();
        int hours = duration.toHoursPart();
        int days = duration.toHoursPart();

        placeholders.put("<second>", seconds < 10 ? "0" + seconds : String.valueOf(seconds));
        placeholders.put("<minute>", minutes < 10 ? "0" + minutes : String.valueOf(minutes));
        placeholders.put("<hour>", hours < 10 ? "0" + hours : String.valueOf(hours));
        placeholders.put("<day>", days < 10 ? "0" + days : String.valueOf(days));

        if (template == null)
            template = templateDefault;

        for (String k : placeholders.keySet())
            template = template.replaceAll(k, placeholders.get(k));

        return template;
    }

    private static String getRestartBroadcast(String reason) {
        String broadcastBuilder = Utilities.getPlugin().getConfig().getString(reason == null ? "restart-warning-text" : "restart-warning-text-reason");

        // Placeholders lol
        if (broadcastBuilder != null && reason != null)
            broadcastBuilder = broadcastBuilder.replaceAll("<reason>", reason);

        return broadcastBuilder;
    }

    public static void cancelRestartSchedule(String reason) {
        String broadcast = Utilities.getPlugin().getConfig().getString(reason == null ? "restart-cancel-text" : "restart-cancel-text-reason");
        String inputSound = Utilities.getPlugin().getConfig().getString("restart-warning-cancel-sound");
        final Optional<Sound> sound = fetchSound(inputSound);
        double pitch = Utilities.getPlugin().getConfig().getDouble("restart-cancel-sound-pitch");
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        if (broadcast != null && reason != null) {
            broadcast = broadcast.replaceAll("<reason>", reason);
        }

        for (Player p : players) {
            if (broadcast != null) p.sendRichMessage(broadcast);

            sound.ifPresent(s -> p.playSound(p, s, 1, (float) pitch));
        }

        timer.cancel();
        isRestartScheduled = false;
    }

    public static boolean isIsRestartScheduled() {
        return isRestartScheduled;
    }
}