package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.MinikoMewOrb;
import EllieMinibot.orbs.VedalOrb;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@Monikaphobia")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("channel"), BaseMod.getKeywordDescription("channel")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Vedal987")), BaseMod.getKeywordDescription(makeID("Vedal987"))));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("upgrade"), BaseMod.getKeywordDescription("upgrade")));
        return tooltips;
    }
}