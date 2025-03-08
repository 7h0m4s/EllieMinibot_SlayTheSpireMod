package EllieMinibot.powers;

import EllieMinibot.cards.AbstractQuizCard;
import EllieMinibot.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;


public class CodeQuestionStreakPower extends AbstractEasyPower {

    public static final String POWER_ID = makeID("CodeQuestionStreakPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    public static final Boolean IS_TURN_BASED = false;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/powers/CodeQuestionStreakPower32.png"));

    public CodeQuestionStreakPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, IS_TURN_BASED, owner, amount);
        this.img = myTex; // apply the texture
        updateExistingQuizCards();
    }
    
    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.updateExistingQuizCards();
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    private void updateExistingQuizCards() {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractQuizCard) {
                ((AbstractQuizCard) c).UpdateStreak(this.amount);
            }
        }

        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractQuizCard) {
                ((AbstractQuizCard) c).UpdateStreak(this.amount);
            }
        }

        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractQuizCard) {
                ((AbstractQuizCard) c).UpdateStreak(this.amount);
            }
        }

        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof AbstractQuizCard) {
                ((AbstractQuizCard) c).UpdateStreak(this.amount);
            }
        }

    }



    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (this.amount * 10) + DESCRIPTIONS[1];
    }

}
