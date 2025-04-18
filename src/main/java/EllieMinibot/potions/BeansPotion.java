package EllieMinibot.potions;

import EllieMinibot.CharacterFile;
import EllieMinibot.ModFile;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static EllieMinibot.ModFile.makeID;

public class BeansPotion extends AbstractEasyPotion {
    public static final String ID = makeID("BeansPotion");

    public BeansPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.BOTTLE, new Color(0.51f, 0.25f, 0.086f, 0.5f), new Color(0.6f, 0.8f, 1.0f, 0f),new Color(0.51f, 0.145f, 0.086f, 1f), CharacterFile.Enums.ELLIE_MINIBOT, ModFile.characterColor);
        this.isThrown = false;
        this.potency = this.getPotency();
    }

    public int getPotency(int ascensionlevel) {
        return 20;
    }

    public void use(AbstractCreature creature) {

        AbstractDungeon.player.heal(this.potency, true);
    }

    public String getDescription() {

        return strings.DESCRIPTIONS[0] + potency + strings.DESCRIPTIONS[1];
    }
}