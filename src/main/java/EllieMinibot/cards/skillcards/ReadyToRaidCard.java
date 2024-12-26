package EllieMinibot.cards.skillcards;
import EllieMinibot.actions.EasyXCostAction;
import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class ReadyToRaidCard  extends AbstractEasyCard {
    public final static String ID = makeID("ReadyToRaid");
    // intellij stuff power, self, uncommon

    public ReadyToRaidCard() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            applyToEnemyTop(m, new StrengthPower(m, -(effect + params[0])));
            return true;
        }, magicNumber));

    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}