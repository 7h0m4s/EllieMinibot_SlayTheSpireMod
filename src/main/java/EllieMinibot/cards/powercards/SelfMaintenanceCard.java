package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToSelf;

public class SelfMaintenanceCard extends AbstractEasyCard {
    public final static String ID = makeID("SelfMaintenance");
    // intellij stuff power, self, uncommon

    public SelfMaintenanceCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        applyToSelf(new RepairPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }
}