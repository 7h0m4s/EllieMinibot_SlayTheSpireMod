package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

public class DistractedCard extends AbstractEasyCard {
    public final static String ID = makeID("Distracted");

    public DistractedCard() {
        super(ID, -2, CardType.STATUS, CardRarity.COMMON, CardTarget.NONE, CardColor.COLORLESS);
        tags.add(CardTags.EMPTY);
        this.isEthereal = true;
        this.exhaust = true;
    }

    public void triggerWhenDrawn() {
        this.addToBot(new LoseEnergyAction(1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upp() {

    }
}