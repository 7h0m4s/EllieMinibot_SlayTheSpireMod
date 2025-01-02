package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.MinikoMewOrb;
import EllieMinibot.orbs.VedalOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class VedalCollabCard extends AbstractEasyCard {
    public final static String ID = makeID("VedalCollab");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public VedalCollabCard() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);

        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ChannelAction(new VedalOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }
}