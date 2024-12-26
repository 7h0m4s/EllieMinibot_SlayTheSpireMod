package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.GomiOrb;
import EllieMinibot.orbs.YourAverageBoOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class GomiCollabCard extends AbstractEasyCard {
    public final static String ID = makeID("GomiCollab");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public GomiCollabCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);

        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ChannelAction(new GomiOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }
}