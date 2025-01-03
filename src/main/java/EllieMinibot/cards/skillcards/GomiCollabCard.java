package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.GomiOrb;
import EllieMinibot.orbs.YourAverageBoOrb;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Gomi")), BaseMod.getKeywordDescription(makeID("Gomi"))));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("poison"), BaseMod.getKeywordDescription("poison")));
        return tooltips;
    }
}