package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.orbs.MapleOrb;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class MapleCollabCard extends AbstractEasyCard {
    public final static String ID = makeID("MapleCollab");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public MapleCollabCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChannelAction(new MapleOrb()));
    }

    @Override
    public void upp() {
        this.updateCost(-1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Maple_QB")), BaseMod.getKeywordDescription(makeID("Maple_QB"))));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("dexterity"), BaseMod.getKeywordDescription("dexterity")));
        return tooltips;
    }
}