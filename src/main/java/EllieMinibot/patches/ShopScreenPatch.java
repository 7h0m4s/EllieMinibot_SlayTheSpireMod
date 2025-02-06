package EllieMinibot.patches;

import EllieMinibot.relics.GamerSuppsRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.ShopScreen;

import java.util.ArrayList;


public class ShopScreenPatch {
    @SpirePatch(
            clz = ShopScreen.class,
            method = "init"
    )
    public static class ShopScreenInitPatch {
        @SpirePostfixPatch
        public static void Postfix(ShopScreen __instance, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {
            if (AbstractDungeon.player.hasRelic(GamerSuppsRelic.ID)) {
                __instance.applyDiscount(0.1F, true);
            }
        }
    }
}
