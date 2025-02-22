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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class EatTheCodeBugCard extends AbstractEasyCard {
    public final static String ID = makeID("EatTheCodeBug");
    private Random randomizer = new Random();


    public EatTheCodeBugCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 5;
        tags.add(CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        List<AbstractCard> allBugFactCards = hand().group.stream().filter(a-> a.cardID.equals(BugFactCard.ID)).collect(Collectors.toList());
        for(AbstractCard card: allBugFactCards){
            atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 5));
            atb(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        }
    }


    @Override
    public void upp() {
        this.upgradeBlock(2);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("block"), BaseMod.getKeywordDescription("block")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle("exhaust"), BaseMod.getKeywordDescription("exhaust")));
        return tooltips;
    }
}