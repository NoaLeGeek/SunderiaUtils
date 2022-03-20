package fr.sunderia.sunderiautils.utils;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {
    
    private static final Map<String, Cooldown> cooldowns = new HashMap<>();
    private long start;
    private final int timeInSeconds;
    private final UUID id;
    private final String cooldownName;

    public Cooldown(UUID id, String cooldownName, int timeInSeconds) {
        this.id = id;
        this.cooldownName = cooldownName;
        this.timeInSeconds = timeInSeconds;
    }

    public static boolean isInCooldown(UUID id, String cooldownName) {
        if(getTimeLeft(id, cooldownName) >= 1) {
            return true;
        } else {
            stop(id, cooldownName);
            return false;
        }
    }

    private static void stop(UUID id, String cooldownName) {
        Cooldown.cooldowns.remove(id + cooldownName);
    }

    private static Cooldown getCooldown(UUID id, String cooldownName) {
        return cooldowns.get(id + cooldownName);
    }

    public static int getTimeLeft(UUID id, String cooldownName) {
        Cooldown cooldown = getCooldown(id, cooldownName);
        int f = -1;
        if(cooldown != null) {
            long now = System.currentTimeMillis();
            int r = (int) (now - cooldown.start) / 1000;
            f = (r - cooldown.timeInSeconds) * (-1);
        }
        return f;
    }

    public void start() {
        this.start = System.currentTimeMillis();
        cooldowns.put(this.id.toString() + this.cooldownName, this);
    }

    public static String cooldownMessage(UUID uuid, String type) {
        return ChatColor.RED + "Please wait " + ChatColor.GOLD + getTimeLeft(uuid, type) + ChatColor.RED + " before doing that !";
    }
}
