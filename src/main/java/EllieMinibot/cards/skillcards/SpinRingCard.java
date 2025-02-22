package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.specialcards.DistractedCard;
import EllieMinibot.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;

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
        addToBot(new MakeTempCardInDrawPileAction(new DistractedCard(), magicNumber, true, false));
    }


    @Override
    public void upp() {
        this.upgradeMagicNumber(1);
    }
}