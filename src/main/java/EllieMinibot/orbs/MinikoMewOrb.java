package EllieMinibot.orbs;

import EllieMinibot.util.TexLoader;
import EllieMinibot.util.Wiz;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.scene.ShinySparkleEffect;

import java.util.ArrayList;
import java.util.Collection;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;
import static EllieMinibot.util.Wiz.applyToEnemyTop;
import static EllieMinibot.util.Wiz.att;

public class MinikoMewOrb extends AbstractOrb {
    private static final OrbStrings orbString;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/orbs/MinikoMewOrb.png"));

    public MinikoMewOrb() {
        this.ID = makeID("MinikoMewOrb");
        this.img = myTex;
        this.name = orbString.NAME;
        this.basePassiveAmount = 1;
        this.passiveAmount = this.basePassiveAmount;
        this.baseEvokeAmount = this.evokeAmount = this.basePassiveAmount * 2;
        this.updateDescription();
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
        scale = 1.0F;
        this.showEvokeValue = false;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = String.format(orbString.DESCRIPTION[0] + orbString.DESCRIPTION[1] + orbString.DESCRIPTION[2], this.evokeAmount, this.evokeAmount);
    }

    public void onEvoke() {
        ArrayList<AbstractCard> playerHand = (ArrayList<AbstractCard>) Wiz.hand().group.clone();
        java.util.Collections.shuffle(playerHand);// randomise order
        for (int i = playerHand.size() - 1; i >= 0; i--) {
            AbstractCard card = playerHand.get(i);
            if (card.costForTurn > 0 && ((card.color != AbstractCard.CardColor.CURSE || card.cardID.equals("Pride")) && (card.type != AbstractCard.CardType.STATUS || card.cardID.equals("Slimed")))){

                card.setCostForTurn(card.cost - this.passiveAmount);
                break;
            }
        }
        att(new VFXAction(new DarkOrbActivateEffect(hb.cX, hb.cY), 0.1F));
        att(new SFXAction("MONSTER_SLIME_ATTACK", 0.5F));

    }

    public void onStartOfTurn() {
        boolean hasModifiedCard = false;
        ArrayList<AbstractCard> playerDrawPile = (ArrayList<AbstractCard>) Wiz.drawPile().group.clone();
        for (int i = playerDrawPile.size() - 1; i >= 0; i--) {
            AbstractCard card = playerDrawPile.get(i);
            if (card.costForTurn > 0 && ((card.color != AbstractCard.CardColor.CURSE || card.cardID.equals("Pride")) && (card.type != AbstractCard.CardType.STATUS || card.cardID.equals("Slimed")))){

                card.setCostForTurn(card.cost - this.passiveAmount);
                break;
            }
        }
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
        return new MinikoMewOrb();
    }


    @Override
    public void render(SpriteBatch sb) {

        this.shineColor.a = this.c.a / 2.0F;
        sb.setColor(this.shineColor);
        //sb.setBlendFunction(770, 1);
        //sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
        //sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
        //sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        renderText(sb);
        hb.render(sb);
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString("MinikoMewOrb");
    }
}
