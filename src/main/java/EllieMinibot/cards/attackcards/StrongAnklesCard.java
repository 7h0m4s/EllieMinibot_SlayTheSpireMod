package EllieMinibot.cards.attackcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.specialcards.BugFactCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.makeID;

public class StrongAnklesCard extends AbstractEasyCard {

    public final static String ID = makeID("StrongAnkles");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public StrongAnklesCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 1;
        isMultiDamage = false;
        tags.add(CardTags.STRIKE);
        this.cardsToPreview = new BugFactCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        addToBot(new MakeTempCardInHandAction(new BugFactCard(), magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(2);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "@zieb_s")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("BugFact")), BaseMod.getKeywordDescription(makeID("BugFact"))));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("CardSuggestedBy")), String.format(BaseMod.getKeywordDescription(makeID("CardSuggestedBy")), "Maple #bPrime")));
        return tooltips;
    }
}
