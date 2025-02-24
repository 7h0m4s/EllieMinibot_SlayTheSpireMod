package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.specialcards.BugFactCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

public class OvenDriedBugsCard extends AbstractEasyCard {
    public final static String ID = makeID("OvenDriedBugs");


    public OvenDriedBugsCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 1;
        this.baseBlock = this.block = 6;

        this.cardsToPreview = new BugFactCard();
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new MakeTempCardInHandAction(new BugFactCard(), this.magicNumber));
    }

    @Override
    public void upp() {

        this.upgradeBlock(2);
        this.upgradeMagicNumber(1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("block"), BaseMod.getKeywordDescription("block")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("BugFact")), BaseMod.getKeywordDescription(makeID("BugFact"))));
        return tooltips;
    }
}