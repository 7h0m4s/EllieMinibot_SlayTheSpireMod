package EllieMinibot.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.text.MessageFormat;

import static EllieMinibot.ModFile.*;

public class ClintsReptilesEvent extends AbstractImageEvent {
    public static final String ID = makeID("ClintsReptilesEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    //This text should be set up through loading an event localization json file
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;

    public ClintsReptilesEvent() {
        super(
                NAME,
                MessageFormat.format("{0} NL {1} NL {2} NL {3}", DESCRIPTIONS[0], DESCRIPTIONS[1], DESCRIPTIONS[2], DESCRIPTIONS[3]),
                "ellieminibotResources/images/events/ClintsReptilesEvent.png"
        );
        this.screenNum = 0;
        //This is where you would create your dialog options
        //This adds the option to a list of options


        this.imageEventText.setDialogOption(OPTIONS[0], new PreservedInsect());
        this.imageEventText.setDialogOption(OPTIONS[1], new SnakeRing());
        this.imageEventText.setDialogOption(OPTIONS[2], new ToxicEgg2());
        this.imageEventText.setDialogOption(OPTIONS[3], new Regret());

    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play(CLINTS_REPTILE_ROOM_KEY);
        }
    }

    @Override
    protected void buttonEffect(int buttonPressed) {

        if (this.screenNum > 0) {
            this.openMap();
            return;
        }
        switch (buttonPressed) {
            case 0:
                provideRelic(new PreservedInsect(), PreservedInsect.ID);
                AbstractEvent.logMetricObtainRelic("ClintsReptiles", "Insect",new PreservedInsect());
                break;
            case 1:
                provideRelic(new SnakeRing(), SnakeRing.ID);
                AbstractEvent.logMetricObtainRelic("ClintsReptiles", "Reptile",new SnakeRing());
                break;
            case 2:
                provideRelic(new ToxicEgg2(), ToxicEgg2.ID);
                AbstractEvent.logMetricObtainRelic("ClintsReptiles", "Avian",new ToxicEgg2());
                break;
            case 3:
                AbstractCard c = new Regret();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractEvent.logMetricObtainCard("ClintsReptiles", "Run Away",new Regret());
                this.screenNum = 1;
                this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[4]);
                break;

            default:
                this.openMap();
                break;
        }
    }
    private void provideRelic(AbstractRelic relic, String relicId){
        this.screenNum = 1;
        if (!AbstractDungeon.player.hasRelic(relicId)) {
            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relic);
        } else {
            this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new Circlet());
        }
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[4]);

    }
}
