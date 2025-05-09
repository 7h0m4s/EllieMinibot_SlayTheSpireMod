package EllieMinibot;

import EllieMinibot.cards.attackcards.*;
import EllieMinibot.cards.powercards.*;
import EllieMinibot.cards.skillcards.*;
import EllieMinibot.cards.specialcards.*;
import EllieMinibot.relics.EllieAntennaRelic;
import EllieMinibot.relics.NeuroDogRelic;
import basemod.abstracts.CustomEnergyOrb;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import EllieMinibot.cards.Defend;
import EllieMinibot.cards.Strike;
import hlysine.friendlymonsters.characters.AbstractPlayerWithMinions;

import java.util.ArrayList;
import java.util.List;

import static EllieMinibot.CharacterFile.Enums.ELLIE_MINIBOT_COLOR;
import static EllieMinibot.ModFile.*;

public class CharacterFile extends AbstractPlayerWithMinions {

    static final String ID = makeID("ModdedCharacter");
    static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;
    public static final int BASE_MINION_COUNT = 1;
    public static final float BASE_MINION_POWER_CHANCE = 0.25f;
    public static final float BASE_MINION_ATTACK_CHANCE = 0.25f;


    public CharacterFile(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, makeCharacterPath("mainChar/orb/vfx.png"), null), new SpineAnimation("ellieminibotResources/images/char/mainChar/Idle/Ellie.atlas", "ellieminibotResources/images/char/mainChar/Idle/Ellie.json", 1.0F));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));

        loadAnimation("ellieminibotResources/images/char/mainChar/Idle/Ellie.atlas", "ellieminibotResources/images/char/mainChar/Idle/Ellie.json", 1.0F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);

        e.setTime(e.getEndTime() * MathUtils.random());

        setBaseMinionCount(BASE_MINION_COUNT);
        setBaseMinionPowerChance(BASE_MINION_POWER_CHANCE);
        setBaseMinionAttackTargetChance(BASE_MINION_ATTACK_CHANCE);

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                80, 80, 3, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("ellieminibotResources/images/scenes/ellieBg.png");
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<CutscenePanel>();
        panels.add(new CutscenePanel("ellieminibotResources/images/scenes/ellie1.png", "BELL"));
        panels.add(new CutscenePanel("ellieminibotResources/images/scenes/ellie2.png"));
        panels.add(new CutscenePanel("ellieminibotResources/images/scenes/ellie3.png"));
        return panels;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(YourAverageBoCollabCard.ID);
//        // --- Attack Cards ---
//        retVal.add(HireathonCard.ID);
//        retVal.add(JobInterviewCard.ID);
//        retVal.add(LeadedSolderCard.ID);
//        retVal.add(RawSpaghettiCard.ID);
//        retVal.add(StrongAnklesCard.ID);
//        retVal.add(TeethModelCard.ID);
//        retVal.add(ThatCompanyCard.ID);
//
//        // --- Power Cards ---
//        retVal.add(AboveAverageBoCard.ID);
//        retVal.add(OtamatoneCard.ID);
//        retVal.add(SelfMaintenanceCard.ID);
//        retVal.add(SongCrazyRobotBodyCard.ID);
//        retVal.add(SongTogetherCard.ID);
//        retVal.add(UmActuallyCard.ID);
//        retVal.add(WaterproofingCard.ID);
//        retVal.add(WelcomeRaidersCard.ID);
//
//        // --- Skill Cards ---
//        retVal.add(BeatSabreCard.ID);
//        retVal.add(BookieCollabCard.ID);
//        retVal.add(CerberCollabCard.ID);
//        retVal.add(DrinkingStreamCard.ID);
//        retVal.add(EatTheCodeBugCard.ID);
//        retVal.add(EllieCollabCard.ID);
//        retVal.add(FakeBoyfriendCard.ID);
//        retVal.add(FeatureNotABugCard.ID);
//        retVal.add(FeedGaleCard.ID);
//        retVal.add(GomiCollabCard.ID);
//        retVal.add(IceCubeChallengeCard.ID);
//        retVal.add(LeetCodePracticeCard.ID);
//        retVal.add(LoudAsCicadaCard.ID);
//        retVal.add(MapleCollabCard.ID);
//        retVal.add(MinikoMewCollabCard.ID);
//        retVal.add(NerdyGirlRizzCard.ID);
//        retVal.add(OvenDriedBugsCard.ID);
//        retVal.add(ReadyToRaidCard.ID);
//        retVal.add(ShoomimiCollabCard.ID);
//        retVal.add(SpinRingCard.ID);
//        retVal.add(VedalCollabCard.ID);
//        retVal.add(YourAverageBoCollabCard.ID);
//        retVal.add(SecretIdentityCard.ID);
//
//        // --- Special Cards ---
//        retVal.add(BugFactCard.ID);
//        retVal.add(DistractedCard.ID);
//        retVal.add(UkuleleCurseCard.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //retVal.add(TodoItem.ID);
        retVal.add(EllieAntennaRelic.ID);
        retVal.add(NeuroDogRelic.ID);
        return retVal;
    }



    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
        //TODO: Put ellie sound effect here?
    }

    private static final String[] orbTextures = {
            makeCharacterPath("mainChar/orb/layer1.png"),
            makeCharacterPath("mainChar/orb/layer2.png"),
            makeCharacterPath("mainChar/orb/layer3.png"),
            makeCharacterPath("mainChar/orb/layer4.png"),
            makeCharacterPath("mainChar/orb/layer4.png"),
            makeCharacterPath("mainChar/orb/layer6.png"),
            makeCharacterPath("mainChar/orb/layer1d.png"),
            makeCharacterPath("mainChar/orb/layer2d.png"),
            makeCharacterPath("mainChar/orb/layer3d.png"),
            makeCharacterPath("mainChar/orb/layer4d.png"),
            makeCharacterPath("mainChar/orb/layer5d.png"),
    };

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return ELLIE_MINIBOT_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new RawSpaghettiCard();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new CharacterFile(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        //TODO: Change these.
        @SpireEnum
        public static AbstractPlayer.PlayerClass ELLIE_MINIBOT;
        @SpireEnum(name = "ELLIE_MINIBOT_COLOR")
        public static AbstractCard.CardColor ELLIE_MINIBOT_COLOR;
        @SpireEnum(name = "ELLIE_MINIBOT_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
