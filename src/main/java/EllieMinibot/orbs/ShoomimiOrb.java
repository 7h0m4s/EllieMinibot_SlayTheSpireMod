package EllieMinibot.orbs;

import EllieMinibot.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;
import static EllieMinibot.util.Wiz.att;

public class ShoomimiOrb extends AbstractOrb {
    private static final OrbStrings orbString;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/orbs/ShoomimiOrb.png"));

    public ShoomimiOrb() {
        this.ID = makeID("ShoomimiOrb");
        this.img = myTex;
        this.name = orbString.NAME;
        this.basePassiveAmount = 1;
        this.passiveAmount = this.basePassiveAmount;
        this.baseEvokeAmount = this.evokeAmount = this.basePassiveAmount * 1;
        this.updateDescription();
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
        scale = 1.0F;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = String.format(orbString.DESCRIPTION[0]  + orbString.DESCRIPTION[1] + orbString.DESCRIPTION[2],this.evokeAmount,this.evokeAmount);
    }

    public void onEvoke() {
        att(new DrawCardAction(this.evokeAmount));
        att(new VFXAction(new DarkOrbActivateEffect(hb.cX, hb.cY), 0.1F));
        att(new SFXAction("MONSTER_SLIME_ATTACK", 0.5F));

    }

    public void onStartOfTurn() {
        att(new DrawCardAction(this.evokeAmount));
        att(new VFXAction(new DarkOrbActivateEffect(hb.cX, hb.cY), 0.1F));
        att(new SFXAction("MONSTER_SLIME_ATTACK", 0.5F));

    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.3F);
    }

    public AbstractOrb makeCopy() {
        return new ShoomimiOrb();
    }


    @Override
    public void render(SpriteBatch sb) {

        this.shineColor.a = this.c.a / 2.0F;
        sb.setColor(this.shineColor);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        renderText(sb);
        hb.render(sb);
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString("ShoomimiOrb");
    }
}
