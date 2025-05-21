package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;

public class GeoGuesserFailCurseCard extends AbstractEasyCard {
    public final static String ID = makeID("GeoGuesserFailCurse");

    public GeoGuesserFailCurseCard() {
        super(ID, -2, CardType.CURSE, CardRarity.COMMON, CardTarget.NONE, CardColor.CURSE);
        tags.add(CardTags.EMPTY);
        this.isEthereal = false;
        this.exhaust = false;
    }

    public void triggerWhenDrawn() {
        Wiz.att(new DiscardSpecificCardAction(Wiz.hand().getRandomCard(true)));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upp() {

    }
}