package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;

import static EllieMinibot.ModFile.makeID;

public class SpinRingCard extends AbstractEasyCard {
    public final static String ID = makeID("SpinRing");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SpinRingCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        p.gainEnergy(2);
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, false));
    }


    @Override
    public void upp() {
        this.upgradeMagicNumber(1);
    }
}