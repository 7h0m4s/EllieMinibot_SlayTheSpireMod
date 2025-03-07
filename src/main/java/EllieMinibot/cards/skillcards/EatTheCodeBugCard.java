package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.specialcards.BugFactCard;
import EllieMinibot.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class EatTheCodeBugCard extends AbstractEasyCard {
    public final static String ID = makeID("EatTheCodeBug");
    private final static int upgradedMinimumBlock = 5;


    public EatTheCodeBugCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 0;
        // Prepopulate base block in case card is created during combat.
        if(AbstractDungeon.isPlayerInDungeon()){
            recalculateBlockValue();
        }
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        recalculateBlockValue();
        blck();
    }

    public void recalculateBlockValue(){
        int totalExhaustedBugFacts;
        if(AbstractDungeon.player == null || Wiz.p().exhaustPile == null || !AbstractDungeon.isPlayerInDungeon()){
            totalExhaustedBugFacts = 0;
        }
        else {
            totalExhaustedBugFacts = Math.toIntExact(Wiz.p().exhaustPile.group.stream().filter(a -> a.cardID == BugFactCard.ID).count());
        }
        recalculateBlockValue(totalExhaustedBugFacts);
    }

    public void recalculateBlockValue(int exhaustedBugFactCount){
        if(this.upgraded){
            baseBlock = exhaustedBugFactCount + upgradedMinimumBlock;
        }
        else{
            baseBlock = exhaustedBugFactCount;

        }
        this.isBlockModified = true;
        this.initializeDescription();
    }


    @Override
    public void upp() {
        recalculateBlockValue();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("block"), BaseMod.getKeywordDescription("block")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("exhaust"), BaseMod.getKeywordDescription("exhaust")));
        return tooltips;
    }

    @Override
    public void atTurnStart() {
        recalculateBlockValue();
        super.atTurnStart();
    }
}