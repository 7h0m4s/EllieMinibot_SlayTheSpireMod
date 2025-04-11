package EllieMinibot.friendlymonster;

import EllieMinibot.ModFile;
import EllieMinibot.actions.ChannelRandomEllieOrbAction;
import EllieMinibot.cards.specialcards.BugFactCard;
import EllieMinibot.relics.NerfTurretRelic;
import EllieMinibot.util.Wiz;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import hlysine.friendlymonsters.monsters.AbstractFriendlyMonster;
import hlysine.friendlymonsters.monsters.MinionMove;

import java.util.ArrayList;
import java.util.Random;

import static EllieMinibot.ModFile.makeID;
import static EllieMinibot.util.Wiz.*;

public class NeuroDogFriendlyMonster  extends AbstractFriendlyMonster {
    public static final String NAME = "Neuro Dog";
    public static final String ID = makeID("NeuroDogFriendlyMonster");
    public static final int MAX_HP = 25;
    public static final String IMG = "ellieminibotResources/images/friendlymonsters/cutoutneurodog_withface_scaled.png";
    private final Random rand = new Random();



    public NeuroDogFriendlyMonster(float drawX, float drawY) {
        super(NAME, ID, MAX_HP,
                0, 0, 200, 200, // hitbox x,y,w,h
                IMG, drawX, drawY);

        this.loadAnimation(new SpineAnimation("ellieminibotResources/images/friendlymonsters/neurodog_animation/NeuroDogAnimation.atlas", "ellieminibotResources/images/friendlymonsters/neurodog_animation/NeuroDogAnimation.json", 1.0F));

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        byte nextMove = 1;
        this.setMove(nextMove, Intent.ATTACK, -1);
        refreshMoveOptions();


    }


    enum  animationType {
         ATTACK,
         FRONTFLIP,
         SPIN,
         GROWL,
         BUGTHROW,
         IDLE,
         DEATH
     }

    private void playAnimation(animationType type) {playAnimation(type, 1f);}
    private void playAnimation(animationType type, float timeScale){
        String animationName = "";

        switch(type){
            case ATTACK:
                animationName = "attack";
                break;
            case FRONTFLIP:
                animationName = "frontflip";
                break;
            case SPIN:
                animationName = "spin";
                break;
            case GROWL:
                animationName = "growl";
                break;
            case BUGTHROW:
                animationName = "bugthrow";
                break;
        }

        AnimationState.TrackEntry trackEntry = this.state.setAnimation(0, animationName, false);
        this.state.addAnimation(0, "idle", true, 0.0F);
        trackEntry.setTimeScale(timeScale);
    }

    @Override
    public void deathReact() {
        super.deathReact();
        AnimationState.TrackEntry e = this.state.setAnimation(0, "death", false);
    }

    @Override
    public void die(boolean triggerPowers) {
        AnimationState.TrackEntry e = this.state.setAnimation(0, "death", false);
        if (!this.isDying) {
            this.isDying = true;
            if (this.currentHealth <= 0 && triggerPowers) {
                for (AbstractPower p : this.powers) {
                    p.onDeath();
                }
            }

            if (this.currentHealth < 0) {
                this.currentHealth = 0;
            }


            this.deathTimer = 4.0f;

        }
    }


    @Override
    public void applyEndOfTurnTriggers() {
        // Override so that turn taken is reset at start of turn by NeuroDogRelic
        // to prevent buttons from re-showing up before next turn
        super.applyEndOfTurnTriggers();
        this.clearMoves();
    }

    public void refreshMoveOptions() {
        this.clearMoves();
        while (this.moves.getMoves().size() < 2) {
            ArrayList<MinionMove> moveList = createMoves();
            MinionMove moveOption = moveList.get(rand.nextInt(moveList.size()));
            if (this.moves.hasMove(moveOption.getID())){
                continue;
            }
            else{
                this.addMove(moveOption);
            }
        }
    }

    private ArrayList<MinionMove> createMoves(){
        ArrayList<MinionMove> moveMasterList = new ArrayList<>();

        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("TACKLE").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/attack/attack_intent_1.png"),
                ModFile.NeuroDogMoveSet.get("TACKLE").DESCRIPTION[0]+ (Wiz.p().hasRelic(NerfTurretRelic.ID) ? 10 : 5) +ModFile.NeuroDogMoveSet.get("TACKLE").DESCRIPTION[1],
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    DamageInfo info = new DamageInfo(this, Wiz.p().hasRelic(NerfTurretRelic.ID) ? 10 : 5 , DamageInfo.DamageType.NORMAL);
                    info.applyPowers(this, target); // <--- This lets powers effect minions attacks
                    atb(new DamageAction(target, info));
                    atb(new SFXAction("SLIME_ATTACK"));
                    playAnimation(animationType.ATTACK);
                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("BODYSLAM").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/attack/attack_intent_2.png"),
                ModFile.NeuroDogMoveSet.get("BODYSLAM").DESCRIPTION[0]+ (Wiz.p().hasRelic(NerfTurretRelic.ID) ? 20 : 10) +ModFile.NeuroDogMoveSet.get("BODYSLAM").DESCRIPTION[1],
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    DamageInfo info = new DamageInfo(this, Wiz.p().hasRelic(NerfTurretRelic.ID) ? 20 : 10, DamageInfo.DamageType.NORMAL);
                    info.applyPowers(this, target); // <--- This lets powers effect minions attacks
                    atb(new DamageAction(target, info));
                    atb(new SFXAction("BLUNT_HEAVY"));
                    playAnimation(animationType.ATTACK);
                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("DEFEND").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/defendL.png"),
                ModFile.NeuroDogMoveSet.get("DEFEND").DESCRIPTION[0],
                () -> {
                    atb(new GainBlockAction(this, this, 15));
                    atb(new SFXAction("BLOCK_GAIN_1"));
                    playAnimation(animationType.FRONTFLIP);
                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("GROWL").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/debuff2L.png"),
                ModFile.NeuroDogMoveSet.get("DEFEND").DESCRIPTION[0],
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    atb(new ApplyPowerAction(target, this, new WeakPower(target, 2, false), 2));
                    atb(new SFXAction("DEBUFF_1"));
                    playAnimation(animationType.GROWL);

                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("TAILWHIP").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/debuff1L.png"),
                ModFile.NeuroDogMoveSet.get("TAILWHIP").DESCRIPTION[0],
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    atb(new ApplyPowerAction(target, this, new VulnerablePower(target, 2, false), 2));
                    atb(new SFXAction("DEBUFF_2"));
                    playAnimation(animationType.SPIN);

                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("HYPE").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/buff1L.png"),
                ModFile.NeuroDogMoveSet.get("HYPE").DESCRIPTION[0],
                () -> {
                    AbstractPlayer p = AbstractDungeon.player;

                    this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2), 2));
                    this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 2), 2));
                    atb(new SFXAction("BUFF_1"));
                    playAnimation(animationType.FRONTFLIP);
                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("COMFORT").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/magicL.png"),
                ModFile.NeuroDogMoveSet.get("COMFORT").DESCRIPTION[0],
                () -> {
                    AbstractPlayer p = AbstractDungeon.player;

                    this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2), 2));
                    this.addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 2), 2));
                    atb(new SFXAction("BUFF_2"));
                    playAnimation(animationType.FRONTFLIP);
                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("CODEBUG").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/codebug.png"),
                ModFile.NeuroDogMoveSet.get("CODEBUG").DESCRIPTION[0],
                () -> {
                    atb(new SFXAction("RELIC_DROP_MAGICAL"));
                    playAnimation(animationType.BUGTHROW);
                    atb(new MakeTempCardInHandAction(new BugFactCard(), 2));
                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("PLAY").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/drawpile.png"),
                ModFile.NeuroDogMoveSet.get("PLAY").DESCRIPTION[0],
                () -> {
                    atb(new SFXAction("ATTACK_MAGIC_FAST_3"));
                    playAnimation(animationType.SPIN);
                    atb(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), false));
                }
        ));
        moveMasterList.add(new MinionMove(ModFile.NeuroDogMoveSet.get("PONDERORB").NAME, this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/empty1.png"),
                ModFile.NeuroDogMoveSet.get("PONDERORB").DESCRIPTION[0],
                () -> {
                    atb(new SFXAction("ATTACK_MAGIC_FAST_2"));
                    playAnimation(animationType.FRONTFLIP);
                    atb(new ChannelRandomEllieOrbAction());
                }
        ));
        return moveMasterList;

    }

}