package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import static EllieMinibot.ModFile.makeID;

public class GamerSuppsRelic extends AbstractEasyRelic {
    public static final String ID = makeID("GamerSuppsRelic");

    public GamerSuppsRelic() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
    }

    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof ShopRoom) {
            this.flash();
            this.pulse = true;
        } else {
            this.pulse = false;
        }

    }
}
