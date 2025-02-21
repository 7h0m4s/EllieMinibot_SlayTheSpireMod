package EllieMinibot;

import EllieMinibot.config.ConfigPanel;
import EllieMinibot.events.BoBathroomEvent;
import EllieMinibot.events.ClintsReptilesEvent;
import EllieMinibot.localization.CodeQuestion;
import EllieMinibot.localization.CodeQuestionType;
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
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import EllieMinibot.cards.AbstractEasyCard;
import EllieMinibot.cards.cardvars.AbstractEasyDynamicVariable;
import EllieMinibot.potions.AbstractEasyPotion;
import EllieMinibot.relics.AbstractEasyRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.IOUtils;
import org.scannotation.AnnotationDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import java.nio.charset.StandardCharsets;

import static EllieMinibot.CharacterFile.Enums.ELLIE_MINIBOT;
import static EllieMinibot.config.ConfigPanel.Disable_QUIZ_Card_Questions;

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
    public static final String modName = "EllieMinibot";

    public static ModInfo info;

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(82, 94, 95, 1);

    public SpireConfig ELLIEMINIBOT_CONFIG;

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
    public static final String WELCOME_RAIDERS_KEY = makeID("WelcomeRaidersAudio");
    public static final String WELCOME_RAIDERS_OGG = makePath("audio/WelcomeRaiders.ogg");
    public static final String CORRECT_SFX_KEY = makeID("CorrectSfx");
    public static final String CORRECT_SFX_OGG = makePath("audio/CorrectSfx.ogg");
    public static final String WRONG_SFX_KEY = makeID("WrongSfx");
    public static final String WRONG_SFX_OGG = makePath("audio/WrongSfx.ogg");

    public static ArrayList<CodeQuestionType> CODE_QUESTION_TYPES = new ArrayList<CodeQuestionType>();

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    public static final Logger logger = LogManager.getLogger("EllieMinibot");

    public String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    static {

        // Find and load Mod Info
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(ModFile.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }

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

        BaseMod.addRelicToCustomPool(new RunicCapacitor(),CharacterFile.Enums.ELLIE_MINIBOT_COLOR );
        BaseMod.addRelicToCustomPool(new EmotionChip(),CharacterFile.Enums.ELLIE_MINIBOT_COLOR );
        BaseMod.addRelicToCustomPool(new Inserter(),CharacterFile.Enums.ELLIE_MINIBOT_COLOR );
        BaseMod.addRelicToCustomPool(new GoldPlatedCables(),CharacterFile.Enums.ELLIE_MINIBOT_COLOR );
        BaseMod.addRelicToCustomPool(new DataDisk(),CharacterFile.Enums.ELLIE_MINIBOT_COLOR );

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
        BaseMod.addAudio(WELCOME_RAIDERS_KEY, WELCOME_RAIDERS_OGG);
        BaseMod.addAudio(CORRECT_SFX_KEY, CORRECT_SFX_OGG);
        BaseMod.addAudio(WRONG_SFX_KEY, WRONG_SFX_OGG);
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

        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = new Texture(Gdx.files.internal("ellieminibotResources/images/ellieMinibotBadge.png"));

        // Loads the config page for the mod
        BaseMod.registerModBadge(badgeTexture, info.Name, info.Authors[0], info.Description, new ConfigPanel());


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

        BaseMod.addEvent(BoBathroomEvent.ID, BoBathroomEvent.class);
        BaseMod.addEvent(new AddEventParams.Builder(BoBathroomEvent.ID, BoBathroomEvent.class).playerClass(ELLIE_MINIBOT).create());


        setupCodeQuestions();
    }

private void setupCodeQuestions(){
    String json = Gdx.files.internal(modID + "Resources/localization/" + getLangString() + "/CodeQuestionStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));

    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, List<CodeQuestion>>>() {}.getType();
    Map<String, ArrayList<CodeQuestion>> data = gson.fromJson(json, type);

    if(Disable_QUIZ_Card_Questions) return;

    for (String questionType : data.keySet()) {

        if(data.get(questionType).isEmpty()) continue; // Skip if the QuestionType contains no questions
        switch(questionType) {
            case "C":
                if(ConfigPanel.Enable_CodeQuestions_For_C) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "C++":
                if(ConfigPanel.Enable_CodeQuestions_For_CPlusPlus) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Java":
                if(ConfigPanel.Enable_CodeQuestions_For_Java) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Maven":
                if(ConfigPanel.Enable_CodeQuestions_For_Maven) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Kotlin":
                if(ConfigPanel.Enable_CodeQuestions_For_Kotlin) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Spring":
                if(ConfigPanel.Enable_CodeQuestions_For_Spring) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Python":
                if(ConfigPanel.Enable_CodeQuestions_For_Python) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Stack":
                if(ConfigPanel.Enable_CodeQuestions_For_Stack) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Queue":
                if(ConfigPanel.Enable_CodeQuestions_For_Queue) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Linked List":
                if(ConfigPanel.Enable_CodeQuestions_For_Linked_List) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "DataStructures":
                if(ConfigPanel.Enable_CodeQuestions_For_DataStructures) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "JavaScript":
                if(ConfigPanel.Enable_CodeQuestions_For_JavaScript) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Go":
                if(ConfigPanel.Enable_CodeQuestions_For_Go) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "C#":
                if(ConfigPanel.Enable_CodeQuestions_For_CSharp) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "PHP":
                if(ConfigPanel.Enable_CodeQuestions_For_PHP) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Swift":
                if(ConfigPanel.Enable_CodeQuestions_For_Swift) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Ruby":
                if(ConfigPanel.Enable_CodeQuestions_For_Ruby) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "R":
                if(ConfigPanel.Enable_CodeQuestions_For_R) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Scala":
                if(ConfigPanel.Enable_CodeQuestions_For_Scala) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "SQL":
                if(ConfigPanel.Enable_CodeQuestions_For_SQL) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "DBMC":
                if(ConfigPanel.Enable_CodeQuestions_For_DBMC) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "MySQL":
                if(ConfigPanel.Enable_CodeQuestions_For_MySQL) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "PostgreSQL":
                if(ConfigPanel.Enable_CodeQuestions_For_PostgreSQL) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "MongoDB":
                if(ConfigPanel.Enable_CodeQuestions_For_MongoDB) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Oracle":
                if(ConfigPanel.Enable_CodeQuestions_For_Oracle) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "SQL Server":
                if(ConfigPanel.Enable_CodeQuestions_For_SQL_Server) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "HTML":
                if(ConfigPanel.Enable_CodeQuestions_For_HTML) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "CSS":
                if(ConfigPanel.Enable_CodeQuestions_For_CSS) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "React":
                if(ConfigPanel.Enable_CodeQuestions_For_React) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Angular":
                if(ConfigPanel.Enable_CodeQuestions_For_Angular) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Node JS":
                if(ConfigPanel.Enable_CodeQuestions_For_Node_JS) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Express.js":
                if(ConfigPanel.Enable_CodeQuestions_For_Expressjs) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "jQuery":
                if(ConfigPanel.Enable_CodeQuestions_For_jQuery) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Vue.js":
                if(ConfigPanel.Enable_CodeQuestions_For_Vuejs) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "TypeScript":
                if(ConfigPanel.Enable_CodeQuestions_For_TypeScript) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "JSON":
                if(ConfigPanel.Enable_CodeQuestions_For_JSON) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "XML":
                if(ConfigPanel.Enable_CodeQuestions_For_XML) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Software Engineering":
                if(ConfigPanel.Enable_CodeQuestions_For_Software_Engineering) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Operating System":
                if(ConfigPanel.Enable_CodeQuestions_For_Operating_System) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Computer Hardware":
                if(ConfigPanel.Enable_CodeQuestions_For_Computer_Hardware) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Linux":
                if(ConfigPanel.Enable_CodeQuestions_For_Linux) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Software Testing":
                if(ConfigPanel.Enable_CodeQuestions_For_Software_Testing) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Object-Oriented Programming (OOP)":
                if(ConfigPanel.Enable_CodeQuestions_For_Object_Oriented_Programming_OOP) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Embedded Systems":
                if(ConfigPanel.Enable_CodeQuestions_For_Embedded_Systems) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Artificial Intelligence":
                if(ConfigPanel.Enable_CodeQuestions_For_Artificial_Intelligence) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Cyber Security":
                if(ConfigPanel.Enable_CodeQuestions_For_Cyber_Security) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "IoT (Internet of Things)":
                if(ConfigPanel.Enable_CodeQuestions_For_IoT_Internet_of_Things) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Hadoop":
                if(ConfigPanel.Enable_CodeQuestions_For_Hadoop) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Data Science":
                if(ConfigPanel.Enable_CodeQuestions_For_Data_Science) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Unix":
                if(ConfigPanel.Enable_CodeQuestions_For_Unix) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Visual Basic":
                if(ConfigPanel.Enable_CodeQuestions_For_Visual_Basic) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "MapReduce":
                if(ConfigPanel.Enable_CodeQuestions_For_MapReduce) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Cassandra":
                if(ConfigPanel.Enable_CodeQuestions_For_Cassandra) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Redis":
                if(ConfigPanel.Enable_CodeQuestions_For_Redis) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Apache Spark":
                if(ConfigPanel.Enable_CodeQuestions_For_Apache_Spark) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Apache Kafka":
                if(ConfigPanel.Enable_CodeQuestions_For_Apache_Kafka) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "RabbitMQ":
                if(ConfigPanel.Enable_CodeQuestions_For_RabbitMQ) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "DevOps":
                if(ConfigPanel.Enable_CodeQuestions_For_DevOps) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Docker":
                if(ConfigPanel.Enable_CodeQuestions_For_Docker) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Git":
                if(ConfigPanel.Enable_CodeQuestions_For_Git) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
            case "Kubernetes":
                if(ConfigPanel.Enable_CodeQuestions_For_Kubernetes) CODE_QUESTION_TYPES.add(new CodeQuestionType(questionType, data.get(questionType)));
                break;
        }

    }
}


}
