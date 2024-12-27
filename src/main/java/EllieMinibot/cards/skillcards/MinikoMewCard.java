package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.GomiOrb;
import EllieMinibot.orbs.MinikoMewOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class MinikoMewCard extends AbstractEasyCard {
    public final static String ID = makeID("MinikoMewCollab");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public MinikoMewCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ChannelAction(new MinikoMewOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }
}