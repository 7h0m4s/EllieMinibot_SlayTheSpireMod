package EllieMinibot;

import EllieMinibot.cards.EasyModalChoiceCard;
import EllieMinibot.cards.democards.complex.EasyXCostDemo;
import EllieMinibot.cards.democards.complex.InlinePowerDemo;
import EllieMinibot.cards.democards.complex.MultiCardPreviewAndDrawCallback;
import EllieMinibot.cards.democards.complex.SelectCardsPlusCardMods;
import EllieMinibot.cards.democards.simple.DrawAndShiv;
import EllieMinibot.cards.democards.simple.StartupBlockCard;
import EllieMinibot.cards.democards.simple.TwoTypesOfDamage;
import EllieMinibot.cards.powercards.SongCrazyRobotBodyCard;
import EllieMinibot.events.ClintsReptilesEvent;
import EllieMinibot.monsters.EvilNeuroMonster;
import EllieMinibot.monsters.LanternBugMonster;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.cardvars.AbstractEasyDynamicVariable;
import EllieMinibot.potions.AbstractEasyPotion;
import EllieMinibot.relics.AbstractEasyRelic;
import EllieMinibot.util.ProAudio;
import java.nio.charset.StandardCharsets;

import static EllieMinibot.CharacterFile.Enums.ELLIE_MINIBOT;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class ModFile implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber {

    public static final String modID = "ellieminibot";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(82, 94, 95, 1);

    public static final String SHOULDER1 = makeCharacterPath("mainChar/shoulder.png");
    public static final String SHOULDER2 = makeCharacterPath("mainChar/shoulder2.png");
    public static final String CORPSE = makeCharacterPath("mainChar/corpse.png");
    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_L_ART = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CHARSELECT_BUTTON = makeImagePath("charSelect/charButton.png");
    private static final String CHARSELECT_PORTRAIT = makeImagePath("charSelect/charBG.png");

    //Audio
    public static final String SONG_TOGETHER_KEY = makeID("SongTogetherAudio");
    public static final String SONG_TOGETHER_OGG = makePath("audio/SongTogether.ogg");
    public static final String SONG_CRAZYROBOTBODY_KEY = makeID("SongCrazyRobotBodyAudio");
    public static final String SONG_CRAZYROBOTBODY_OGG = makePath("audio/SongCrazyRobotBody.ogg");
    public static final String BG_MUSIC_EVILNEURO_CRAZYROBOTBODY_KEY = makeID("EvilNeuro_CrazyRobotBody");
    public static final String BG_MUSIC_EVILNEURO_CRAZYROBOTBODY_OGG = makePath("audio/bg_music/EvilNeuro_CrazyRobotBody.ogg");
    public static final String CLINTS_REPTILE_ROOM_KEY = makeID("ClintsReptileRoomAudio");
    public static final String CLINTS_REPTILE_ROOM_OGG = makePath("audio/ClintsReptileRoom.ogg");

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public ModFile() {
        BaseMod.subscribe(this);

        BaseMod.addColor(CharacterFile.Enums.ELLIE_MINIBOT_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCharacterPath(String resourcePath)
    {
        return modID + "Resources/images/char/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        ModFile thismod = new ModFile();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new CharacterFile(CharacterFile.characterStrings.NAMES[1], ELLIE_MINIBOT),
            CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, ELLIE_MINIBOT);
        
        new AutoAdd(modID)
            .packageFilter(AbstractEasyPotion.class)
            .any(AbstractEasyPotion.class, (info, potion) -> {
                if (potion.pool == null)
                    BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor, potion.ID);
                else
                    BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor, potion.ID, potion.pool);
            });
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
            .packageFilter(AbstractEasyDynamicVariable.class)
            .any(DynamicVariable.class, (info, var) -> 
                BaseMod.addDynamicVariable(var));
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, modID + "Resources/localization/" + getLangString() + "/Orbstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, modID + "Resources/localization/" + getLangString() + "/Stancestrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, modID + "Resources/localization/" + getLangString() + "/MonsterStrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, modID + "Resources/localization/" + getLangString() + "/Eventstrings.json");

    }

    @Override
    public void receiveAddAudio() {

        BaseMod.addAudio(SONG_TOGETHER_KEY, SONG_TOGETHER_OGG);
        BaseMod.addAudio(SONG_CRAZYROBOTBODY_KEY, SONG_CRAZYROBOTBODY_OGG);
        BaseMod.addAudio(BG_MUSIC_EVILNEURO_CRAZYROBOTBODY_KEY, BG_MUSIC_EVILNEURO_CRAZYROBOTBODY_OGG);
        BaseMod.addAudio(CLINTS_REPTILE_ROOM_KEY, CLINTS_REPTILE_ROOM_OGG);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/" + getLangString() + "/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostInitialize()
    {
        // Add a single monster encounter
        BaseMod.addMonster(LanternBugMonster.ID, () -> new LanternBugMonster());
        // Add a multi-monster encounter
        BaseMod.addMonster("MultiLanternBug", () -> new MonsterGroup(new AbstractMonster[] {
                new LanternBugMonster(-200.0F, 15.0F),
                new LanternBugMonster(80.0F, 0.0F)
        }));

        BaseMod.addMonsterEncounter(Exordium.ID, new MonsterInfo(LanternBugMonster.ID, 5));
        BaseMod.addStrongMonsterEncounter(TheCity.ID, new MonsterInfo("MultiLanternBug", 10));



        BaseMod.addMonster(EvilNeuroMonster.ID, () -> new EvilNeuroMonster());

        BaseMod.addBoss(TheCity.ID, EvilNeuroMonster.ID,
                "ellieminibotResources/images/ui/map/boss/EvilNeuro.png",
                "ellieminibotResources/images/ui/map/bossOutline/EvilNeuro.png");


        // Events
        BaseMod.addEvent(ClintsReptilesEvent.ID, ClintsReptilesEvent.class);
        BaseMod.addEvent(new AddEventParams.Builder(ClintsReptilesEvent.ID, ClintsReptilesEvent.class).playerClass(ELLIE_MINIBOT).create());

    }

}
