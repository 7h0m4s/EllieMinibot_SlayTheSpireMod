package EllieMinibot.cards.attackcards;

import EllieMinibot.actions.CodeQuestionAction;
import EllieMinibot.cards.AbstractEasyCard;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.ModFile.*;
import static EllieMinibot.util.Wiz.*;

public class JobInterviewCard extends AbstractEasyCard {

    public final static String ID = makeID("JobInterview");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public JobInterviewCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;

        isMultiDamage = false;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // deal damage if success, damage self if fail
        atb(new CodeQuestionAction(
                () -> // onSuccess
                {
                    atb(new SFXAction(CORRECT_SFX_KEY));
                    atb(new VFXAction(new BorderFlashEffect(Color.GREEN, true)));
                    dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
                },
                () -> // onFailure
                {
                    atb(new SFXAction(WRONG_SFX_KEY));
                    atb(new VFXAction(new BorderFlashEffect(Color.RED, true)));
                    atb(new DamageAction(p, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }));

    }

    @Override
    public void upp() {

        upgradeDamage(3);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(BaseMod.getKeywordTitle(makeID("Artist")), String.format(BaseMod.getKeywordDescription(makeID("Artist")), "Arcat109")));
        return tooltips;
    }
}
