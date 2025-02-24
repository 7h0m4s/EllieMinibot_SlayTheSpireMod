package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.attackcards.ThatCompanyCard;
import EllieMinibot.cards.specialcards.BugFactCard;
import EllieMinibot.orbs.GomiOrb;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class LoudAsCicadaCard extends AbstractEasyCard {
    public final static String ID = makeID("LoudAsCicada");


    public LoudAsCicadaCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 3;

        this.cardsToPreview = new BugFactCard();
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new MakeTempCardInHandAction(new BugFactCard(), this.magicNumber));
    }

    @Override
    public void upp() {

        this.upgradeMagicNumber(1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@Boedromion")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("BugFact")), BaseMod.getKeywordDescription(makeID("BugFact"))));
        return tooltips;
    }
}