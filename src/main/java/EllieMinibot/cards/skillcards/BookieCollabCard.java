package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.BookieOrb;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class BookieCollabCard extends AbstractEasyCard {
    public final static String ID = makeID("BookieCollab");


    public BookieCollabCard() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChannelAction(new BookieOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Bookie")), BaseMod.getKeywordDescription(makeID("Bookie"))));
        return tooltips;
    }
}