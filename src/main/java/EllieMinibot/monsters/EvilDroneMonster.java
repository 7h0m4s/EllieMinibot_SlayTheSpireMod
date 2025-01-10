package EllieMinibot.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import static EllieMinibot.ModFile.makeID;

public class EvilDroneMonster extends AbstractMonster {
    public static final String ID = makeID("EvilDroneMonster");
    private static final MonsterStrings monsterStrings;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 52;
    private static final int HP_MAX = 58;
    private static final int A_2_HP_MIN = 54;
    private static final int A_2_HP_MAX = 60;
    private static final int BEAM_DMG = 8;
    private static final int BLOCK_AMT = 12;
    private static final byte BEAM = 1;
    private static final byte SUPPORT_BEAM = 2;
    private static final byte STASIS = 3;
    private boolean usedStasis = false;
    private int count;
    private int lastMessageIndex = -1;

    public EvilDroneMonster(float x, float y, int count) {
        super(monsterStrings.NAME, ID, AbstractDungeon.monsterHpRng.random(52, 58), 0.0F, 0.0F, 160.0F, 160.0F, "ellieminibotResources/images/monsters/EvilDrone/EvilDrone.png", x, y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(54, 60);
        } else {
            this.setHp(52, 58);
        }

        this.count = count;
        this.damage.add(new DamageInfo(this, 8));

        loadAnimation("ellieminibotResources/images/monsters/EvilDrone/EvilDrone.atlas", "ellieminibotResources/images/monsters/EvilDrone/EvilDrone.json", 6F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);

        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn() {
        // 10% chance to say the next message
        if(lastMessageIndex < DIALOG.length - 1 && new Random().randomBoolean(0.1F)){
            lastMessageIndex += 1;
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[lastMessageIndex], 0.3F, 2.0F));

        }
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.RED)));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.NONE));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.getMonsters().getMonster(ID), this, 12));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(this));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void update() {
        super.update();
        if (this.count % 2 == 0) {
            this.animY = MathUtils.cosDeg((float)(System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        } else {
            this.animY = -MathUtils.cosDeg((float)(System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        }

    }

    protected void getMove(int num) {
        if (!this.usedStasis && num >= 25) {
            this.setMove((byte)3, Intent.STRONG_DEBUFF);
            this.usedStasis = true;
        } else if (num >= 70 && !this.lastTwoMoves((byte)2)) {
            this.setMove((byte)2, Intent.DEFEND);
        } else if (!this.lastTwoMoves((byte)1)) {
            this.setMove(MOVES[0],(byte)1, Intent.ATTACK, 8);
        } else {
            this.setMove((byte)2, Intent.DEFEND);
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

