package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

public class UkuleleCurseCard extends AbstractEasyCard {
    public final static String ID = makeID("UkuleleCurse");

    public UkuleleCurseCard() {
        super(ID, -2, CardType.CURSE, CardRarity.CURSE, CardTarget.NONE, CardColor.CURSE);
        tags.add(CardTags.EMPTY);
        this.isEthereal = false;
        this.exhaust = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upp() {

    }
}