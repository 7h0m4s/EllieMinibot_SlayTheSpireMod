package EllieMinibot.cards.skillcards;

import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.config.ConfigPanel;
import EllieMinibot.util.Wiz;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.*;
import static EllieMinibot.util.Wiz.atb;
import static EllieMinibot.util.Wiz.att;

@AutoAdd.Ignore
public class FeedGaleCard extends AbstractEasyCard {

    public final static String ID = makeID("FeedGale");

    public FeedGaleCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 1;
        this.misc = 0;

        // Match baseBlock with all other FeedGaleCards
        if(AbstractDungeon.player != null) {
            AbstractDungeon.player.masterDeck.group
                    .stream()
                    .filter(a -> a.cardID.equals(this.cardID))
                    .forEach(c-> {if(c.baseBlock > this.baseBlock) this.UpgradeGale(c.baseBlock);});
        }
        exhaust = false;
        this.initializeDescription();
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

    public void UpgradeGale(int amount){
        this.misc += amount;
        this.baseBlock = 1 + this.misc;
        this.applyPowers();
        this.applyPowersToBlock();
        this.initializeDescription();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!ConfigPanel.Disable_FeedGale_SoundEffect) {
            att(new SFXAction(GALE_ELLIE_KEY));
        }

        blck();
        atb(new SelectCardsCenteredAction(
                Wiz.p().hand.group,
                cardStrings.EXTENDED_DESCRIPTION[0],
                false,
                (c)-> (c.costForTurn >= 0 && c.uuid != this.uuid && c.type != CardType.STATUS && c.type != CardType.CURSE),
                (cards) -> {

            for (AbstractCard c2 : cards) {

                // Upgrade the FeedGale card that was used manually
                this.UpgradeGale(c2.costForTurn);

                // Update FeedGale Cards
                AbstractDungeon.player.masterDeck.group
                        .stream()
                        .filter(a -> a.cardID.equals(this.cardID))
                        .forEach(c-> ((FeedGaleCard)c).UpgradeGale(c2.costForTurn));

                Wiz.getAllCardsInCardGroups(true, true)
                        .stream()
                        .filter(a -> a.cardID.equals(this.cardID))
                        .forEach(c-> ((FeedGaleCard)c).UpgradeGale(c2.costForTurn));

                // Remove selected card from hand and master deck
                Wiz.p().hand.group
                        .stream()
                        .filter(a-> a.uuid == c2.uuid)
                        .forEach(c-> AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 2, Settings.HEIGHT / 2)));
                Wiz.p().hand.group
                        .removeIf(a-> a.uuid == c2.uuid);
                AbstractDungeon.player.masterDeck.group
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
