package EllieMinibot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class SpawnAllyAction extends AbstractGameAction {
    private boolean used;
    private static final float DURATION = 0.1F;
    private AbstractMonster m;
    private boolean minion;
    private int targetSlot;
    private boolean useSmartPositioning;

    public SpawnAllyAction(AbstractMonster m, boolean isMinion) {
        this(m, isMinion, -99);
        this.useSmartPositioning = true;
    }

    public SpawnAllyAction(AbstractMonster m, boolean isMinion, int slot) {
        this.used = false;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
        this.m = m;
        this.minion = isMinion;
        this.targetSlot = slot;
        this.useSmartPositioning = false;
    }

    public void update() {
        if (!this.used) {
            for(AbstractRelic r : AbstractDungeon.player.relics) {
                r.onSpawnMonster(this.m);
            }

            this.m.init();
            this.m.applyPowers();
            if (!this.useSmartPositioning) {
                AbstractDungeon.getCurrRoom().monsters.addMonster(this.targetSlot, this.m);
            } else {
                int position = 0;

                for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (this.m.drawX > mo.drawX) {
                        ++position;
                    }
                }

                AbstractDungeon.getCurrRoom().monsters.addMonster(position, this.m);
            }

            this.m.showHealthBar();
            if (ModHelper.isModEnabled("Lethality")) {
                this.addToBot(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, 3), 3));
            }

            if (ModHelper.isModEnabled("Time Dilation")) {
                this.addToBot(new ApplyPowerAction(this.m, this.m, new SlowPower(this.m, 0)));
            }

            if (this.minion) {
                this.addToTop(new ApplyPowerAction(this.m, this.m, new MinionPower(this.m)));
            }

            this.used = true;
        }

        this.tickDuration();
    }
}
