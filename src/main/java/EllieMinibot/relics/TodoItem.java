package EllieMinibot.relics;

import EllieMinibot.CharacterFile;
import basemod.AutoAdd;

import static EllieMinibot.ModFile.makeID;

@AutoAdd.Ignore
public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.ELLIE_MINIBOT_COLOR);
    }
}
