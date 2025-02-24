package EllieMinibot.powers;

import EllieMinibot.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;
import static EllieMinibot.util.Wiz.atb;


public class WaterproofingPower extends AbstractEasyPower {

    public static final String POWER_ID = makeID("WaterproofingPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    public static final Boolean IS_TURN_BASED = false;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/powers/WaterproofingPower32.png"));

    public WaterproofingPower( AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, IS_TURN_BASED, owner, amount);
        this.img = myTex; // apply the texture
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(damageType == DamageInfo.DamageType.THORNS) return damage;
        float total = damage - amount;
        if (total < 0) return 0;
        return total;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type != DamageInfo.DamageType.THORNS) return damageAmount;
        else{
            int total = damageAmount - amount;
            return Math.max(total, 0);
        }
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
