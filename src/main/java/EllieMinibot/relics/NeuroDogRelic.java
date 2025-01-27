package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import EllieMinibot.friendlymonster.NeuroDogFriendlyMonster;
import EllieMinibot.monsters.EvilDroneMonster;
import EllieMinibot.orbs.*;
import EllieMinibot.ui.campfire.NeuroDogCampfireOption;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import hlysine.friendlymonsters.characters.AbstractPlayerWithMinions;
import hlysine.friendlymonsters.monsters.AbstractFriendlyMonster;
import hlysine.friendlymonsters.utils.MinionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static EllieMinibot.CharacterFile.BASE_MINION_ATTACK_CHANCE;
import static EllieMinibot.ModFile.makeID;
import static hlysine.friendlymonsters.utils.MinionUtils.setBaseMinionAttackTargetChance;

public class NeuroDogRelic extends AbstractEasyRelic {
    public static final String ID = makeID("NeuroDogRelic");
    public int maxNeuroDogCounter = 0;
    public String minionID;
    private NeuroDogFriendlyMonster minion;

    public NeuroDogRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        if(counter >= maxNeuroDogCounter) {
            if (AbstractDungeon.player instanceof AbstractPlayerWithMinions) {
                AbstractPlayerWithMinions p = (AbstractPlayerWithMinions) AbstractDungeon.player;
                minion = new NeuroDogFriendlyMonster(-700, 50);
                minionID = minion.id;
                p.addMinion(minion);
            }
        }
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        if(counter < maxNeuroDogCounter) options.add(new NeuroDogCampfireOption());
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();

        // Set attack chance to back to base chance at start of turn
        AbstractPlayerWithMinions p = (AbstractPlayerWithMinions) AbstractDungeon.player;
        p.setBaseMinionAttackTargetChance(BASE_MINION_ATTACK_CHANCE);

        minion.refreshMoveOptions();
        minion.setTakenTurn(false);

    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();

        minion.setTakenTurn(false);
    }
}
