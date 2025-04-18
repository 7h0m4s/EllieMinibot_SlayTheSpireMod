package EllieMinibot.powers;

import EllieMinibot.cards.specialcards.BugFactCard;
import EllieMinibot.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;


public class UmActuallyPower extends AbstractEasyPower {

    public static final String POWER_ID = makeID("UmActuallyPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    public static final Boolean IS_TURN_BASED = false;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/powers/UmActuallyPower32.png"));

    public UmActuallyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, IS_TURN_BASED, owner, amount);
        this.img = myTex; // apply the texture
    }
    
    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.updateExistingBugFactCards();
    }

    private void updateExistingBugFactCards() {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof BugFactCard) {
                if (!c.upgraded) {
                    c.baseDamage = 4 + this.amount;
                } else {
                    c.baseDamage = 6 + this.amount;
                }
            }
        }

        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof BugFactCard) {
                if (!c.upgraded) {
                    c.baseDamage = 4 + this.amount;
                } else {
                    c.baseDamage = 6 + this.amount;
                }
            }
        }

        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof BugFactCard) {
                if (!c.upgraded) {
                    c.baseDamage = 4 + this.amount;
                } else {
                    c.baseDamage = 6 + this.amount;
                }
            }
        }

        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof BugFactCard) {
                if (!c.upgraded) {
                    c.baseDamage = 4 + this.amount;
                } else {
                    c.baseDamage = 6 + this.amount;
                }
            }
        }

    }



    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
