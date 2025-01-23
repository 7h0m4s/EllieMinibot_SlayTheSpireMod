package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import EllieMinibot.actions.SpawnAllyAction;
import EllieMinibot.monsters.EvilDroneMonster;
import EllieMinibot.orbs.*;
import EllieMinibot.ui.campfire.NeuroDogCampfireOption;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static EllieMinibot.ModFile.makeID;

public class NeuroDogRelic extends AbstractEasyRelic {
    public static final String ID = makeID("NeuroDogRelic");
    public int maxNeuroDogCounter = 3;

    public NeuroDogRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
if(counter >= maxNeuroDogCounter) {
    AbstractDungeon.actionManager.addToBottom(new SpawnAllyAction(new EvilDroneMonster(200.0F, 130.0F, 1), true));
}
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        if(counter < maxNeuroDogCounter) options.add(new NeuroDogCampfireOption());
    }
}
