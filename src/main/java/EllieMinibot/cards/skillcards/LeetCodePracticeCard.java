package EllieMinibot.cards.skillcards;

import EllieMinibot.actions.CodeQuestionAction;
import EllieMinibot.cards.AbstractQuizCard;
import EllieMinibot.powers.CodeQuestionStreakPower;
import EllieMinibot.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.*;
import static EllieMinibot.util.Wiz.applyToSelf;
import static EllieMinibot.util.Wiz.atb;

public class LeetCodePracticeCard extends AbstractQuizCard {

    public final static String ID = makeID("LeetCodePractice");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public LeetCodePracticeCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = block = 10;
        if(AbstractDungeon.player != null && AbstractDungeon.player.hasPower(CodeQuestionStreakPower.POWER_ID)) UpdateStreak(AbstractDungeon.player.getPower(CodeQuestionStreakPower.POWER_ID).amount);
        isMultiDamage = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // deal damage if success, damage self if fail
        atb(new CodeQuestionAction(
                () -> // onSuccess
                {
                    atb(new SFXAction(CORRECT_SFX_KEY));
                    atb(new VFXAction(new BorderFlashEffect(Color.GREEN, true)));
                    blck();

                    // Add to streak if successful
                    applyToSelf(new CodeQuestionStreakPower(p, 1));
                },
                () -> // onFailure
                {
                    atb(new SFXAction(WRONG_SFX_KEY));
                    atb(new VFXAction(new BorderFlashEffect(Color.RED, true)));
                    atb(new GainBlockAction(Wiz.getRandomEnemy(), AbstractDungeon.player, baseBlock));

                    // Reset Streak if failed
                    AbstractPower streakPower = AbstractDungeon.player.getPower(CodeQuestionStreakPower.POWER_ID);
                    if(streakPower != null ) streakPower.stackPower(streakPower.amount * -1);

                }));
    }

    @Override
    public void upp() {
        //upgradeDamage(3);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        //tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "Arcat109")));
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Quiz")), BaseMod.getKeywordDescription(makeID("Quiz"))));
        return tooltips;
    }

    @Override
    public void UpdateStreak(int streakValue) {
        if (streakValue <= 0) {
            baseBlock = 10;
        } else {
            baseBlock = (int) (baseBlock + 10 * (streakValue * 0.1f));
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new LeetCodePracticeCard();
    }
}
