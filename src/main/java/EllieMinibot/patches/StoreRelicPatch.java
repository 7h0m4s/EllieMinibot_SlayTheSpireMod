package EllieMinibot.patches;

import EllieMinibot.relics.GamerSuppsRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class StoreRelicPatch {
    @SpirePatch(
            clz = StoreRelic.class,
            method = "purchaseRelic"
    )
    public static class StoreRelicPurchaseRelicPatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars={"shopScreen"}
        )
        public static void Insert(StoreRelic __instance, ShopScreen shopScreen) {
            if (__instance.relic.relicId.equals(GamerSuppsRelic.ID)) {
                shopScreen.applyDiscount(0.9F, true);
            }
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRelic.class, "flash");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }
}
