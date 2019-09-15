package org.mcmmo.mcmmotowny.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.palmergames.bukkit.towny.TownyAPI;
import org.mcmmo.mcmmotowny.config.Config;

public class ExperienceListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerXpGainEvent(McMMOPlayerXpGainEvent event) {
        Player player = event.getPlayer();
        float experienceGained = event.getRawXpGained();
        String skillName = event.getSkill().toString();
        String xpGainReason = event.getXpGainReason().toString();
        String townName = TownyAPI.getInstance().getTownName(player.getLocation());

        if (townName == null || !isAffectedSkill(skillName) || !isAffectedReason(xpGainReason)) {
            return;
        }

        experienceGained *= Config.getInstance().getExperienceModifierGlobal();
        experienceGained *= Config.getInstance().getExperienceModifierTown(townName);

        event.setRawXpGained(experienceGained);
    }

    private boolean isAffectedSkill(String skillName) {
        return Config.getInstance().getAffectedSkills().contains(skillName);
    }

    private boolean isAffectedReason(String xpGainReason) {
        return Config.getInstance().getAffectedXpGainReasons().contains(xpGainReason);
    }
}
