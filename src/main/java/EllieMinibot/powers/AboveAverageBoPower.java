package EllieMinibot.powers;

import EllieMinibot.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;


public class AboveAverageBoPower extends AbstractEasyPower {

    public static final String POWER_ID = makeID("AboveAverageBoPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    public static final Boolean IS_TURN_BASED = false;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/powers/AboveAverageBoPower32.png"));

    public AboveAverageBoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, IS_TURN_BASED, owner, amount);
        this.img = myTex; // apply the texture
    }



    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
