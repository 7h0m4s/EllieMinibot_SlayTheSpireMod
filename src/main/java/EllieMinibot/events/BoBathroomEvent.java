package EllieMinibot.events;

import EllieMinibot.cards.powercards.AboveAverageBoCard;
import EllieMinibot.cards.powercards.WaterproofingCard;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.text.MessageFormat;

import static EllieMinibot.ModFile.makeID;

public class BoBathroomEvent extends AbstractImageEvent {
    public static final String ID = makeID("BoBathroomEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    //This text should be set up through loading an event localization json file
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;

    public BoBathroomEvent() {
        super(
                NAME,
                MessageFormat.format("{0} NL {1} NL {2} NL {3}", DESCRIPTIONS[0], DESCRIPTIONS[1], DESCRIPTIONS[2], DESCRIPTIONS[3]),
                "ellieminibotResources/images/events/BoBathroomEvent.png"
        );
        this.screenNum = 0;
        //This is where you would create your dialog options
        //This adds the option to a list of options


        this.imageEventText.setDialogOption(OPTIONS[0], new AboveAverageBoCard());
        this.imageEventText.setDialogOption(OPTIONS[1], new WaterproofingCard());
        this.imageEventText.setDialogOption(OPTIONS[3]);

    }

    public void onEnterRoom() {
    }

    @Override
    protected void buttonEffect(int buttonPressed) {

        if (this.screenNum > 0) {
            this.openMap();
            return;
        }
        switch (buttonPressed) {
            case 0:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new AboveAverageBoCard(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractEvent.logMetricObtainCard("BoBathroom", "Convince",new AboveAverageBoCard());
                this.screenNum = 1;
                this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                break;
            case 1:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new WaterproofingCard(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractEvent.logMetricObtainCard("BoBathroom", "Clean",new WaterproofingCard());
                this.screenNum = 1;
                this.imageEventText.updateBodyText(DESCRIPTIONS[7]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                break;
            case 2:
                AbstractEvent.logMetricObtainCard("BoBathroom", "Run Away",new Regret());
                this.screenNum = 1;
                this.imageEventText.updateBodyText(DESCRIPTIONS[8]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                break;

            default:
                this.openMap();
                break;
        }
    }

}
