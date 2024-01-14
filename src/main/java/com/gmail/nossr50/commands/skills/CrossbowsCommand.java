package com.gmail.nossr50.commands.skills;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.player.UserManager;
import com.gmail.nossr50.util.skills.CombatUtils;
import com.gmail.nossr50.util.skills.RankUtils;
import com.gmail.nossr50.util.text.TextComponentFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.nossr50.datatypes.skills.SubSkillType.*;

public class CrossbowsCommand extends SkillCommand {
    private boolean canSSG;
    private boolean canTrickShot;
    private boolean canPoweredShot;

    public CrossbowsCommand() {
        super(PrimarySkillType.CROSSBOWS);
    }

    @Override
    protected void dataCalculations(Player player, float skillValue) {
        // TODO: Implement data calculations
    }

    @Override
    protected void permissionsCheck(Player player) {
//        canSSG = RankUtils.hasUnlockedSubskill(player, CROSSBOWS_SUPER_SHOTGUN)
//                && Permissions.superShotgun(player);

        canTrickShot = RankUtils.hasUnlockedSubskill(player, CROSSBOWS_TRICK_SHOT)
                && Permissions.trickShot(player);

        canPoweredShot = RankUtils.hasUnlockedSubskill(player, CROSSBOWS_POWERED_SHOT)
                && Permissions.poweredShot(player);
    }

    @Override
    protected List<String> statsDisplay(Player player, float skillValue, boolean hasEndurance, boolean isLucky) {
        List<String> messages = new ArrayList<>();

        McMMOPlayer mmoPlayer = UserManager.getPlayer(player);
        if (mmoPlayer == null) {
            return messages;
        }

        messages.add(ChatColor.DARK_AQUA + "Reminder: " + ChatColor.GOLD + "This is a BETA version of mcMMO, please report any bugs you find!");
        messages.add(ChatColor.GOLD + "Crossbows is a " + ChatColor.RED + "WIP" +ChatColor.GOLD + " skill that is still being developed, please leave feedback in our discord!");

        if (canPoweredShot) {
            messages.add(getStatMessage(ARCHERY_SKILL_SHOT, percent.format(mmoPlayer.getCrossbowsManager().getDamageBonusPercent(player))));
        }

//        if (canSSG) {
//            messages.add("Super Shotgun");
//            //TODO: Implement SSG
//        }

        if (canTrickShot) {
            messages.add(getStatMessage(CROSSBOWS_TRICK_SHOT,
                    String.valueOf(mmoPlayer.getCrossbowsManager().getTrickShotMaxBounceCount())));
        }

        if(Permissions.canUseSubSkill(player, CROSSBOWS_CROSSBOWS_LIMIT_BREAK)) {
            messages.add(getStatMessage(CROSSBOWS_CROSSBOWS_LIMIT_BREAK,
                    String.valueOf(CombatUtils.getLimitBreakDamageAgainstQuality(player, CROSSBOWS_CROSSBOWS_LIMIT_BREAK, 1000))));
        }

        return messages;
    }

    @Override
    protected List<Component> getTextComponents(Player player) {
        List<Component> textComponents = new ArrayList<>();

        TextComponentFactory.getSubSkillTextComponents(player, textComponents, PrimarySkillType.CROSSBOWS);

        return textComponents;
    }
}
