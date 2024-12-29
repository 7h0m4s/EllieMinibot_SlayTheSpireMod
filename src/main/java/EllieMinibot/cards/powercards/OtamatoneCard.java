package EllieMinibot.cards.powercards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.powers.WaterproofingPower;
import com.esotericsoftware.spine.Slot;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.applyToSelf;

public class OtamatoneCard extends AbstractEasyCard {
    public final static String ID = makeID("Otamatone");
    // intellij stuff power, self, uncommon

    public OtamatoneCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       p.increaseMaxOrbSlots(magicNumber, true);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}