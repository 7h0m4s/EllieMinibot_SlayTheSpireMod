package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.specialcards.DistractedCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;

public class SpinRingCard extends AbstractEasyCard {
    public final static String ID = makeID("SpinRing");


    public SpinRingCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.EMPTY);
        this.cardsToPreview = new DistractedCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        p.gainEnergy(magicNumber);
        addToBot(new MakeTempCardInDrawPileAction(new DistractedCard(), 1, true, false));
    }


    @Override
    public void upp() {
        this.upgradeMagicNumber(1);
    }
}