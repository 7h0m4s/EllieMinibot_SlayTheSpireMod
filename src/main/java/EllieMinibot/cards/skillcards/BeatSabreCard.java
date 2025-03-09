package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class BeatSabreCard extends AbstractEasyCard {
    public final static String ID = makeID("BeatSabre");


    public BeatSabreCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2; // This turn draw count
        baseSecondMagic = secondMagic = 1; // next turn draw count
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new DrawCardAction(magicNumber));
        applyToSelf(new DrawCardNextTurnPower(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}