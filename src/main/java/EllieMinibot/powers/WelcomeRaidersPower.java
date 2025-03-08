package EllieMinibot.powers;

import EllieMinibot.actions.ChannelRandomEllieOrbAction;
import EllieMinibot.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;
import static EllieMinibot.util.Wiz.atb;


public class WelcomeRaidersPower extends AbstractEasyPower {

    public static final String POWER_ID = makeID("WelcomeRaidersPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    public static final Boolean IS_TURN_BASED = false;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/powers/WelcomeRaidersPower32.png"));

    public WelcomeRaidersPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, IS_TURN_BASED, owner, amount);
        this.img = myTex; // apply the texture
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        for (int i = 0; i < this.amount; i++) {
            atb(new ChannelRandomEllieOrbAction());
        }
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
