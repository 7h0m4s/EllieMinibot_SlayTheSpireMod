package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.powers.LambdaPower;
import EllieMinibot.powers.WaterproofingPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToSelf;
import static EllieMinibot.util.Wiz.atb;

public class WaterproofingCard extends AbstractEasyCard {
    public final static String ID = makeID("Waterproofing");
    // intellij stuff power, self, uncommon

    public WaterproofingCard() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new WaterproofingPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}