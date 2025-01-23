package EllieMinibot.ui.campfire;

import EllieMinibot.relics.NeuroDogRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;

import static EllieMinibot.util.Wiz.atb;

public class NeuroDogCampfireOption extends AbstractCampfireOption {


    public NeuroDogCampfireOption() {
        // Set button icon and description
        this.img = new Texture("ellieminibotResources/images/campfire/NeuroDogOption.png");
        this.label = "Work On Neuro Dog";
        this.description = "Progress Neuro Dog another step towards completion.";
        // artist @PaleoDavidRU
    }

    @Override
    public void useOption() {
        // Define what happens when the button is clicked
        AbstractDungeon.effectList.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        NeuroDogRelic neuroDogRelic = (NeuroDogRelic)AbstractDungeon.player.relics.stream().filter(a-> a.getClass() == NeuroDogRelic.class).findFirst().orElse(null);
        if (neuroDogRelic == null) return;

        if(neuroDogRelic.counter < neuroDogRelic.maxNeuroDogCounter) {
            neuroDogRelic.setCounter(neuroDogRelic.counter + 1);
            neuroDogRelic.flash();
        }

        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
        atb(new SFXAction("BELL"));
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

    }
}