package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import EllieMinibot.config.ConfigPanel;
import EllieMinibot.friendlymonster.NeuroDogFriendlyMonster;
import EllieMinibot.ui.campfire.NeuroDogCampfireOption;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import hlysine.friendlymonsters.characters.AbstractPlayerWithMinions;
import hlysine.friendlymonsters.patches.PlayerAddSavableFieldsPatch;

import java.util.ArrayList;

import static EllieMinibot.CharacterFile.BASE_MINION_ATTACK_CHANCE;
import static EllieMinibot.ModFile.makeID;

public class NeuroDogRelic extends AbstractEasyRelic {
    public static final String ID = makeID("NeuroDogRelic");
    public int maxNeuroDogCounter = 3;
    public String minionID;
    private NeuroDogFriendlyMonster minion;

    public NeuroDogRelic() {

        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
        this.counter = 3;
    }

    public void refreshDescription(){
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        maxNeuroDogCounter = 3;
        if(this.counter == -1) counter = 0;
        if (counter >= maxNeuroDogCounter) {
            return String.format(DESCRIPTIONS[counter], (int) BASE_MINION_ATTACK_CHANCE * 100);
        }
        return String.format(DESCRIPTIONS[counter], maxNeuroDogCounter - counter);

    }

    @Override
    public void atBattleStart() {
        if(counter >= maxNeuroDogCounter) {
            if (AbstractDungeon.player instanceof AbstractPlayerWithMinions) {
                this.flash();
                AbstractPlayerWithMinions p = (AbstractPlayerWithMinions) AbstractDungeon.player;
                if(!ConfigPanel.Make_NeuroDog_Appear_Higher_In_The_Air) {
                    minion = new NeuroDogFriendlyMonster(-700, 50);
                }
                    else{
                    minion = new NeuroDogFriendlyMonster(-700, 450);
                    }
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
        AbstractPlayerWithMinions p = (AbstractPlayerWithMinions) AbstractDungeon.player;
        if(((MonsterGroup) PlayerAddSavableFieldsPatch.PlayerAddFieldsPatch.fm_minions.get(AbstractDungeon.player)).monsters.size() > 0){


        p.setBaseMinionAttackTargetChance(BASE_MINION_ATTACK_CHANCE);

        minion.refreshMoveOptions();
        minion.setTakenTurn(false);
        }

    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        if(((MonsterGroup) PlayerAddSavableFieldsPatch.PlayerAddFieldsPatch.fm_minions.get(AbstractDungeon.player)).monsters.size() > 0) {
            minion.setTakenTurn(false);
        }
    }
}
