package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.MinikoMewOrb;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class MinikoMewCollabCard extends AbstractEasyCard {
    public final static String ID = makeID("MinikoMewCollab");


    public MinikoMewCollabCard() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);

        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ChannelAction(new MinikoMewOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("MinikoMew")), BaseMod.getKeywordDescription(makeID("MinikoMew"))));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("channel"), BaseMod.getKeywordDescription("channel")));
        return tooltips;
    }
}