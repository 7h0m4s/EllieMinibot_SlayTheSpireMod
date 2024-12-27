package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.MinikoMewOrb;
import EllieMinibot.orbs.ShoomimiOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class ShoomimiCard extends AbstractEasyCard {
    public final static String ID = makeID("ShoomimiCollab");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public ShoomimiCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ChannelAction(new ShoomimiOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }
}