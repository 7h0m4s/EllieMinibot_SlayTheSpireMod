package EllieMinibot.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import static EllieMinibot.util.Wiz.atb;

public class CurlUpPowerPatch {
    @SpirePatch(
            clz = CurlUpPower.class,
            method = "onAttacked"
    )
    public static class CurlUpPowerOnAttackedPatch {
        @SpirePrefixPatch
        public static SpireReturn<?> Prefix(CurlUpPower __instance, DamageInfo info, int damageAmount) {
            if(__instance.owner == AbstractDungeon.player) {
                if (damageAmount < __instance.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL) {
                    __instance.flash();
                    atb(new GainBlockAction(__instance.owner, __instance.owner, __instance.amount));
                    atb(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, CurlUpPower.POWER_ID));
                }
                return SpireReturn.Return(damageAmount);
            }
            return SpireReturn.Continue();
        }
    }
}
