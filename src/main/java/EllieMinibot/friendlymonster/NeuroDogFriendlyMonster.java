package EllieMinibot.friendlymonster;

import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import hlysine.friendlymonsters.characters.AbstractPlayerWithMinions;
import hlysine.friendlymonsters.monsters.AbstractFriendlyMonster;
import hlysine.friendlymonsters.monsters.MinionMove;

import java.util.ArrayList;
import java.util.Random;

import static EllieMinibot.CharacterFile.BASE_MINION_ATTACK_CHANCE;
import static EllieMinibot.ModFile.makeID;

public class NeuroDogFriendlyMonster  extends AbstractFriendlyMonster {
    public static final String NAME = "Neuro Dog";
    public static final String ID = makeID("NeuroDogFriendlyMonster");
    public static final int MAX_HP = 25;
    public static final String IMG = "ellieminibotResources/images/friendlymonsters/cutoutneurodog_withface_scaled.png";
    private Random rand = new Random();

    public static ArrayList<MinionMove> moveMasterList = new ArrayList<>();


    public NeuroDogFriendlyMonster(float drawX, float drawY) {
        super(NAME, ID, MAX_HP,
                0, 0, 200, 200, // hitbox x,y,w,h
                IMG, drawX, drawY);

        this.loadAnimation(new SpineAnimation("ellieminibotResources/images/friendlymonsters/neurodog_animation/NeuroDogAnimation.atlas", "ellieminibotResources/images/friendlymonsters/neurodog_animation/NeuroDogAnimation.json", 1.0F));

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());



        byte nextMove = 1;
        this.setMove(nextMove, Intent.ATTACK, -1);


        moveMasterList.add(new MinionMove("Tackle", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/attack/attack_intent_1.png"),
                "Deal 5 damage to an enemy",
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    DamageInfo info = new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL);
                    info.applyPowers(this, target); // <--- This lets powers effect minions attacks
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
                    playAnimation(animationType.ATTACK);
                }
        ));
        moveMasterList.add(new MinionMove("Body Slam", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/attack/attack_intent_2.png"),
                "Deal 10 damage to an enemy",
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    DamageInfo info = new DamageInfo(this, 10, DamageInfo.DamageType.NORMAL);
                    info.applyPowers(this, target); // <--- This lets powers effect minions attacks
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
                    playAnimation(animationType.ATTACK);
                }
        ));
        moveMasterList.add(new MinionMove("Defend", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/defendL.png"),
                "Gain 10 block",
                () -> {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 10));
                    playAnimation(animationType.FRONTFLIP);
                }
        ));
        moveMasterList.add(new MinionMove("Growl", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/debuff2L.png"),
                "Apply 2 Weak to an enemy",
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, this, new WeakPower(target, 2, false), 2));
                    playAnimation(animationType.FRONTFLIP);

                }
        ));
        moveMasterList.add(new MinionMove("Tail Whip", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/debuff1L.png"),
                "Apply 2 Vulnerable to an enemy",
                () -> {
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, this, new VulnerablePower(target, 2, false), 2));
                    playAnimation(animationType.FRONTFLIP);

                }
        ));
        moveMasterList.add(new MinionMove("Hype", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/buff1L.png"),
                "Give player 2 temporary strength",
                () -> {
                    AbstractPlayer p = AbstractDungeon.player;

                    this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2), 2));
                    this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 2), 2));
                    playAnimation(animationType.FRONTFLIP);
                }
        ));
        moveMasterList.add(new MinionMove("Comfort", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/magicL.png"),
                "Give player 2 temporary dexterity",
                () -> {
                    AbstractPlayer p = AbstractDungeon.player;

                    this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2), 2));
                    this.addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, 2), 2));
                    playAnimation(animationType.FRONTFLIP);
                }
        ));
        moveMasterList.add(new MinionMove("Taunt", this,
                new Texture("ellieminibotResources/images/friendlymonsters/intent/defendBuffL.png"),
                "Enemies will target Neuro Dog on their next turn",
                () -> {
                    AbstractPlayerWithMinions p = (AbstractPlayerWithMinions) AbstractDungeon.player;
                    p.setBaseMinionAttackTargetChance(1);
                    playAnimation(animationType.FRONTFLIP);
                }
        ));

        // TODO MAKE A "CREATE BUG FACT" MOVE


//        moveMasterList.add(new MinionMove("Hide", this,
//                new Texture("ellieminibotResources/images/friendlymonsters/intent/escapeL.png"),
//                "Enemies will ignore Neuro Dog on their next turn",
//                () -> {
//                    AbstractPlayerWithMinions p = (AbstractPlayerWithMinions) AbstractDungeon.player;
//                    p.setBaseMinionAttackTargetChance(0);
//
//                }
//        ));
//        moveMasterList.add(new MinionMove("Sleep", this,
//                new Texture("ellieminibotResources/images/friendlymonsters/intent/sleepL.png"),
//                "Do nothing",
//                () -> {
//
//                }
//        ));

        refreshMoveOptions();

    }

     enum  animationType {
        ATTACK,
        FRONTFLIP,
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


            this.deathTimer = 5.0f;

        }
    }

    public void refreshMoveOptions() {
        this.clearMoves();
        while (this.moves.getMoves().size() < 2) {

            MinionMove moveOption = moveMasterList.get(rand.nextInt(moveMasterList.size()));
            if (this.moves.hasMove(moveOption.getID())){
                continue;
            }
            else{
                this.addMove(moveOption);
            }
        }
    }

}