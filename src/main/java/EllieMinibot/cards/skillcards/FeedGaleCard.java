package EllieMinibot.cards.skillcards;

import EllieMinibot.actions.EasyModalChoiceAction;
import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.util.Wiz;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.atb;

public class FeedGaleCard extends AbstractEasyCard {

    public final static String ID = makeID("FeedGale");

    public FeedGaleCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 1;

        // Match baseBlock with all other FeedGaleCards
        if(AbstractDungeon.player != null) {
            AbstractDungeon.player.masterDeck.group
                    .stream()
                    .filter(a -> a.cardID == this.cardID)
                    .forEach(c-> {if(c.baseBlock > this.baseBlock) this.baseBlock = c.baseBlock;});
        }
        exhaust = false;
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        boolean isPlayable = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.costForTurn >= 0
                    && c.uuid != this.uuid
                    && c.type != CardType.STATUS
                    && c.type != CardType.CURSE) {
                isPlayable = true;
                break;
            }
        }
        return isPlayable;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SelectCardsCenteredAction(
                Wiz.p().hand.group,
                "Choose a card to permanently destroy.",
                false,
                (c)-> (c.costForTurn >= 0 && c.uuid != this.uuid && c.type != CardType.STATUS && c.type != CardType.CURSE),
                (cards) -> {
            for (AbstractCard c2 : cards) {
                // Update FeedGale Cards
                AbstractDungeon.player.masterDeck.group
                        .stream()
                        .filter(a -> a.cardID == this.cardID)
                        .forEach(c-> c.baseBlock += c2.costForTurn);

                Wiz.getAllCardsInCardGroups(true, true)
                        .stream()
                        .filter(a -> a.cardID == this.cardID)
                        .forEach(c-> c.baseBlock += c2.costForTurn);

                // Remove selected card from hand and master deck
                Wiz.p().hand.group
                        .removeIf(a-> a.uuid == c2.uuid);
                Wiz.p().hand.group
                        .stream()
                        .filter(a-> a.uuid == c2.uuid)
                        .forEach(c-> AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 2, Settings.HEIGHT / 2)));
                Wiz.p().hand.group
                        .removeIf(a-> a.uuid == c2.uuid);
            }
        }));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        return tooltips;
    }
}
