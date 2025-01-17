package EllieMinibot.orbs;

import EllieMinibot.cards.specialcards.BugFactCard;
import EllieMinibot.util.TexLoader;
import EllieMinibot.util.Wiz;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.ModFile.makeImagePath;
import static EllieMinibot.util.Wiz.att;

public class BookieOrb extends AbstractOrb {
    private static final OrbStrings orbString;
    private static final Texture myTex = TexLoader.getTexture(makeImagePath("/orbs/BookieOrb.png"));

    public BookieOrb() {
        this.ID = makeID("BookieOrb");
        this.img = myTex;
        this.name = orbString.NAME;
        this.basePassiveAmount = 1;
        this.passiveAmount = this.basePassiveAmount;
        this.baseEvokeAmount = this.evokeAmount = this.basePassiveAmount;
        this.updateDescription();
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
        scale = 1.0F;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = String.format(orbString.DESCRIPTION[0] + orbString.DESCRIPTION[1],this.passiveAmount,this.passiveAmount, this.evokeAmount, this.evokeAmount);;
    }

    public void onEvoke() {
        att(new VFXAction(new DarkOrbActivateEffect(hb.cX, hb.cY), 0.1F));
        att(new SFXAction("MONSTER_SLIME_ATTACK", 0.5F));

        for (int i = 0; i < evokeAmount; i++) {
            orbAction();
        }
    }

    public void onStartOfTurn() {
        att(new VFXAction(new DarkOrbActivateEffect(hb.cX, hb.cY), 0.1F));
        att(new SFXAction("MONSTER_SLIME_ATTACK", 0.5F));

        for (int i = 0; i < passiveAmount; i++) {
            orbAction();
        }
    }

    private void orbAction(){
        AbstractMonster monster = Wiz.getRandomEnemy();
        List<AbstractPower> playerDebuffPowers = AbstractDungeon.player.powers.stream().filter(a -> a.type == AbstractPower.PowerType.DEBUFF).collect(Collectors.toList());
        List<AbstractPower> monsterBuffPowers = monster.powers.stream().filter(a -> a.type == AbstractPower.PowerType.BUFF).collect(Collectors.toList());
        if(playerDebuffPowers.isEmpty() && monsterBuffPowers.isEmpty()) return;

        Random random = new Random();
        boolean choosePlayer = random.nextBoolean();
        if((!playerDebuffPowers.isEmpty() && choosePlayer) || monsterBuffPowers.isEmpty()){
            // Player -> Monster
            AbstractPower powerToTransfer = playerDebuffPowers.get(random.nextInt(playerDebuffPowers.size()));
            Wiz.transferPower(powerToTransfer, AbstractDungeon.player, Wiz.getRandomEnemy());
        }
        else {
            // Monster -> Player
            AbstractPower powerToTransfer = monsterBuffPowers.get(random.nextInt(monsterBuffPowers.size()));
            Wiz.transferPower(powerToTransfer,monster,AbstractDungeon.player);
        }
    }



    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.3F);
    }

    public AbstractOrb makeCopy() {
        return new BookieOrb();
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
        orbString = CardCrawlGame.languagePack.getOrbString("BookieOrb");
    }
}
