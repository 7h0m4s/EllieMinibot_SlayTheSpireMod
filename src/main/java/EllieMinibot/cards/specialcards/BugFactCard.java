package EllieMinibot.cards.specialcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.skillcards.EatTheCodeBugCard;
import EllieMinibot.config.ConfigPanel;
import EllieMinibot.powers.UmActuallyPower;
import EllieMinibot.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.p;

public class BugFactCard  extends AbstractEasyCard {
    public final static String ID = makeID("BugFact");
    public static String[] bugFactStrings;
    public static int totalBugFacts;
    public int bugFactIndex = 0;

    private static final int[] spiderFactIndexes = {38,39,40,41,42,43,44,45,46,47};
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public BugFactCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        tags.add(CardTags.EMPTY);

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(UmActuallyPower.POWER_ID)) {
            this.baseDamage = 4 + AbstractDungeon.player.getPower(UmActuallyPower.POWER_ID).amount;
        } else {
            this.baseDamage = 4;
        }
        this.exhaust = true;
        totalBugFacts = cardStrings.EXTENDED_DESCRIPTION.length;
        applyBugFact();

    }

    private void applyBugFact() {
        Random random = new Random();

        // If Enable_Spider_Bug_Facts is disabled. Keep re-rolling bug facts till a non spider item is found
        do {
            bugFactIndex = random.nextInt(totalBugFacts);
        } while (!ConfigPanel.Enable_Spider_Bug_Facts && ArrayUtils.contains(spiderFactIndexes, bugFactIndex));

        this.rawDescription = String.format("%s NL %s", this.rawDescription, cardStrings.EXTENDED_DESCRIPTION[bugFactIndex]);

        loadCardImage(String.format("ellieminibotResources/images/cards/bugfacts/BugFact_%03d.png",bugFactIndex));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // Update EatTheCodeBug Cards
        int totalExhaustedBugFacts =Math.toIntExact(p()
                                .exhaustPile
                                .group
                                .stream()
                                .filter(a -> a.cardID.equals(BugFactCard.ID))
                                .count()) + 1;
        AbstractList<AbstractCard> allEatBugFactsCards =
                (AbstractList<AbstractCard>) Wiz
                .getAllCardsInCardGroups(true, true)
                .stream()
                .filter(a-> a.cardID.equals(EatTheCodeBugCard.ID))
                .collect(Collectors.toList());
        for(AbstractCard c :  allEatBugFactsCards){
            ((EatTheCodeBugCard) c).recalculateBlockValue(totalExhaustedBugFacts);
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("exhaust"), BaseMod.getKeywordDescription("exhaust")));
        return tooltips;
    }
}