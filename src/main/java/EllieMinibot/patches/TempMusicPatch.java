package EllieMinibot.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

import static EllieMinibot.ModFile.*;

public class TempMusicPatch {

    @SpirePatch(
            clz = TempMusic.class,
            method = "getSong"
    )
    public static class TempMusicGetSongPatch {
        @SpirePrefixPatch
        public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
            if (key.equals(BG_MUSIC_EVILNEURO_CRAZYROBOTBODY_KEY)) {
                return SpireReturn.Return(MainMusic.newMusic(BG_MUSIC_EVILNEURO_CRAZYROBOTBODY_OGG));
            }
            return SpireReturn.Continue();
        }
    }
}
