package EllieMinibot.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

import static EllieMinibot.ModFile.makePath;

public class TempMusicPatch {

    @SpirePatch(
            clz = TempMusic.class,
            method = "getSong"
    )
    public static class TempMusicGetSongPatch {
        @SpirePrefixPatch
        public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
            if(key == "EVIL"){
                return SpireReturn.Return(MainMusic.newMusic(makePath("audio/bg_music/EvilNeuro_CrazyRobotBody.ogg")));
            }
            return SpireReturn.Continue();
        }


    }


}
