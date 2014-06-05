package org.apollo.game.model.skill;

import java.util.List;

import org.apollo.game.model.Graphic;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.msg.impl.OpenDialogueInterfaceMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;
import org.apollo.util.LanguageUtil;

import static java.util.Arrays.asList;

/**
 * A {@link SkillListener} which notifies the player when they have leveled up a
 * skill.
 * 
 * @author Graham
 */
public final class LevelUpSkillListener extends SkillAdapter {

    /**
     * The interface id's for a normal level up in a skill
     */
    private static final int[] LEVEL_UP_IDS = { 6247, 6253, 6206, 6216, 4443,
	    6242, 6211, 6226, 4272, 6231, 6258, 4282, 6263, 6221, 4416, 6237,
	    4277, 4261, 12122, 4887, 4267, };

    /**
     * The player.
     */
    private final Player player;

    /**
     * Creates the level up listener for the specified player.
     * 
     * @param player The player.
     */
    public LevelUpSkillListener(Player player) {
	this.player = player;
    }

    private List getChildren(int id) {
	switch (id) {
	case 4443:
	    return asList(5453, 6114);
	case 4416:
	    return asList(4417, 4438);
	case 4261:
	    return asList(4263, 4264);
	case 4887:
	    return asList(4890, 4891);
	}
	return asList(id + 1, id + 2);
    }

    @Override
    public void leveledUp(SkillSet set, int id, Skill skill) {
	String name = Skill.getName(id);
	String article = LanguageUtil.getIndefiniteArticle(name);
	int level = skill.getMaximumLevel();
	int interfaceId = LEVEL_UP_IDS[id];
	List<Integer> children = getChildren(interfaceId);
	player.send(new SetInterfaceTextMessage(children.get(0), "Congratulations! You've just advanced " + article + " " + name + " level!"));
	player.send(new SetInterfaceTextMessage(children.get(1), "You have now reached level " + level + "!"));
	player.send(new OpenDialogueInterfaceMessage(interfaceId));
	player.sendMessage("You've just advanced " + article + " " + name + " level! You have reached level " + level + ".");
	if (level == 99) {
	    player.sendMessage("Well done! You've achieved the highest possible level in this skill.");
	}
	player.playGraphic(new Graphic(199, 0, 100));
    }

}
