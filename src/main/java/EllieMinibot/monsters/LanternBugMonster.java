package EllieMinibot.monsters;


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

import java.util.ArrayList;

import static EllieMinibot.ModFile.makeID;

public class LanternBugMonster extends AbstractMonster
{
    public static final String ID = makeID("LanternBug");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(makeID("LanternBug"));
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int swipeDmg;
    private int bigSwipeDmg;
    private int bellowBlock;
    private int chompDmg;
    private int thrashDmg;
    private int thrashBlock;
    private int bellowStr;
    private static final byte CHOMP = 1;
    private static final byte BELLOW = 2;
    private static final byte THRASH = 3;
    private boolean firstMove;
    private int lastMessageIndex = -1;


    public LanternBugMonster(float x, float y) {
        super(NAME, ID, 52, 0.0F, 0.0F, 200.0F, 220.0F, null, x, y);
        this.Initialise();
    }

    public LanternBugMonster() {
        super(NAME, ID, 52, 0.0F, 0.0F, 200.0F, 220.0F, null, 0, 0);
        this.Initialise();
    }
    private void Initialise(){
        this.type = EnemyType.NORMAL;
        this.dialogX = (-30.0F * Settings.scale);
        this.dialogY = (50.0F * Settings.scale);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(42, 46);
        } else {
            this.setHp(40, 44);
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.bellowStr = 4;
            this.bellowBlock = 9;
            this.chompDmg = 10;
            this.thrashDmg = 5;
            this.thrashBlock = 5;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.bellowStr = 3;
            this.bellowBlock = 6;
            this.chompDmg = 9;
            this.thrashDmg = 5;
            this.thrashBlock = 5;
        } else {
            this.bellowStr = 2;
            this.bellowBlock = 6;
            this.chompDmg = 8;
            this.thrashDmg = 5;
            this.thrashBlock = 5;
        }

        this.damage.add(new DamageInfo(this, this.chompDmg));
        this.damage.add(new DamageInfo(this, this.thrashDmg));

        loadAnimation("ellieminibotResources/images/monsters/LanternBug/LanternBug.atlas", "ellieminibotResources/images/monsters/LanternBug/LanternBug.json", 1.0F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);

        e.setTime(e.getEndTime() * MathUtils.random());
    }


    public void usePreBattleAction() {

    }

    public void takeTurn() {
        // 75% chance to say the next message
        if(lastMessageIndex < DIALOG.length - 1 && new Random().randomBoolean(0.75F)){
            lastMessageIndex += 1;
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[lastMessageIndex], 0.3F, 2.0F));

        }

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.2F, ScreenShake.ShakeDur.SHORT, ScreenShake.ShakeIntensity.MED));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.bellowStr), this.bellowStr));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.bellowBlock));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.thrashBlock));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        } else {
            if (num < 25) {
                if (this.lastMove((byte)1)) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.5625F)) {
                        this.setMove((byte)2, Intent.DEFEND_BUFF);
                    } else {
                        this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
                    }
                } else {
                    this.setMove(MOVES[0], (byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                }
            } else if (num < 55) {
                if (this.lastTwoMoves((byte)3)) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.357F)) {
                        this.setMove(MOVES[0], (byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                    } else {
                        this.setMove((byte)2, Intent.DEFEND_BUFF);
                    }
                } else {
                    this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
                }
            } else if (this.lastMove((byte)2)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.416F)) {
                    this.setMove(MOVES[0], (byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
                }
            } else {
                this.setMove((byte)2, Intent.DEFEND_BUFF);
            }

        }
    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_MUGGER_1A"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_MUGGER_1B"));
        }
    }



    public void die() {
        super.die();
        CardCrawlGame.sound.play("JAW_WORM_DEATH");
    }
}