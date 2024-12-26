package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.YourAverageBoOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class YourAverageBoCollabCard   extends AbstractEasyCard {
    public final static String ID = makeID("YourAverageBoCollab");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public YourAverageBoCollabCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);

        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ChannelAction(new YourAverageBoOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }
}