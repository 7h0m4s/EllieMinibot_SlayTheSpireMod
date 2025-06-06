package EllieMinibot.events;

import EllieMinibot.ModFile;
import EllieMinibot.cards.specialcards.GeoGuesserFailCurseCard;
import EllieMinibot.cards.specialcards.GeoGuesserTileCard;
import EllieMinibot.localization.GeoGuesserLocation;
import EllieMinibot.relics.GeoGuesserRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.*;

import static EllieMinibot.ModFile.*;
import static com.badlogic.gdx.math.MathUtils.random;

public class GeoGuesserEvent extends AbstractImageEvent {
    public static final String ID = makeID("GeoGuesserEvent");
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private GeoGuesserTileCard chosenCard;
    private GeoGuesserTileCard hoveredCard;
    private boolean gameDone = false;
    private boolean cleanUpCalled = false;
    private boolean displayResultsCalled = false;
    private CardGroup cards;
    private Map<String, GeoGuesserTileCard> cardDict = new Hashtable<>();
    private float waitTimer;
    private GeoGuesserEvent.CUR_SCREEN screen;
    private int distanceFromCorrectCard = 0;
    private boolean pickCardReward;

    
    private static float drawScaleSmall;
    private static float drawScaleMedium;
    private static float drawScaleLarge;

    private static int GRID_ROWS = 8;
    private static int GRID_COLS = 17;
    private static float GRID_X_GAP = 50.0F * Settings.xScale;
    private static float GRID_Y_GAP = -60.0F * Settings.yScale;

    private static float GRID_WIDTH = GRID_COLS * GRID_X_GAP;
    private static float GRID_HEIGHT =  GRID_ROWS * GRID_Y_GAP;
    private static float GRID_X = Settings.WIDTH * 0.55F;
    private static float GRID_Y = Settings.HEIGHT * 0.45f - GRID_HEIGHT * 0.5F;


    private Texture portraitImg;
    private Texture worldTexture;
    private TextureRegion worldTextureRegion;


    // Non Shader 360 Viewer
    private Texture panoTexture;
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private ModelInstance sphereInstance;
    private float lastX = 0f;
    private float lastY = 0f;
    private boolean dragging = false;
    private float targetYaw = 0f;    // Horizontal angle
    private float targetPitch = 0f;  // Vertical angle
    private float currentYaw = 0f;
    private float currentPitch = 0f;
    private float yawVelocity = 0f;
    private float pitchVelocity = 0f;
    private final float FRICTION = 0.9f;
    private final float SENSITIVITY = 0.3f;
    private float targetZoom = 0f;    // In range [minZoom, maxZoom]
    private float currentZoom = 0f;
    private final float MIN_ZOOM = -24f;
    private final float MAX_ZOOM = 10f;
    private final float ZOOM_SENSITIVITY = 1.5f;
    private final float BASE_SENSITIVITY = 0.3f;
    private final float VIEWPORT_WIDTH = Settings.WIDTH * 0.45f;
    private final float VIEWPORT_HEIGHT = Settings.HEIGHT * 0.45f;
    private final float VIEWPORT_X = Settings.WIDTH * 0.05f;
    private final float VIEWPORT_Y = Settings.HEIGHT * 0.25f;


    //Border Flash Effect
    private TextureAtlas.AtlasRegion borderFlashImg = ImageMaster.BORDER_GLOW_2;;
    private float borderFlashDuration = 1.0F;
    private boolean borderFlashActive = false;
    private boolean borderFlashAdditive;
    private Color borderFlashColor = Color.RED;

    private GeoGuesserLocation geoGuesserLocation;


    public GeoGuesserEvent() {
        super(NAME, DESCRIPTIONS[0], "ellieminibotResources/images/events/GeoGuesserEvent.png");

        this.cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        this.waitTimer = 0.0F;
        this.screen = GeoGuesserEvent.CUR_SCREEN.INTRO;
        Collections.shuffle(this.cards.group, new Random(AbstractDungeon.miscRng.randomLong()));
        this.imageEventText.setDialogOption(OPTIONS[0]);

        this.portraitImg = ImageMaster.loadImage("ellieminibotResources/images/events/GeoGuesserEvent.png");
        this.worldTexture = ImageMaster.loadImage("ellieminibotResources/images/events/GeoGuesserEvent_WorldMap.png");

        this.cards.group = this.initializeCards();

        randomlySelectGeoLocation();
        init3D();
    }

    private void randomlySelectGeoLocation(){
        int locationIndex = random.nextInt(GEO_GUESSER_LOCATIONS.size());

        //TODO REMOVE
        //locationIndex = 4;

        geoGuesserLocation = GEO_GUESSER_LOCATIONS.get(locationIndex);
    }

    private void init3D() {
        panoTexture = new Texture(Gdx.files.internal("ellieminibotResources/images/events/GeoGuesser/" + geoGuesserLocation.imagePath));
        panoTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);

        ModelBuilder modelBuilder = new ModelBuilder();
        Material material = new Material(TextureAttribute.createDiffuse(panoTexture));

        Model sphere = modelBuilder.createSphere(
                50f, 50f, 50f,     // size
                64, 64,            // segments
                material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );

        sphereInstance = new ModelInstance(sphere);

        // Invert the sphere so we're viewing from inside
        sphereInstance.transform.setToScaling(-1f, 1f, 1f);

        modelBatch = new ModelBatch();

        camera = new PerspectiveCamera(67f, 640F * Settings.scale, 480F * Settings.scale);
        camera.position.set(0, 0, 0);
        camera.lookAt(0, 0, -1);
        camera.near = 0.1f;
        camera.far = 100f;
        camera.update();
    }

    private ArrayList<AbstractCard> initializeCards() {
        ArrayList<AbstractCard> retVal = new ArrayList();

        int cellWidth = this.worldTexture.getWidth() / GRID_COLS;
        int cellHeight = this.worldTexture.getHeight() / GRID_ROWS;
        int textureHeight = this.worldTexture.getHeight();


        int i = 0;
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int x = col * cellWidth;
                //int y = textureHeight - (row + 1) * cellHeight; // y is flipped in LibGDX
                int y = (row + 1) * cellHeight; // y is flipped in LibGDX
                GeoGuesserTileCard c = new GeoGuesserTileCard(new TextureRegion(worldTexture,x,y,cellWidth,cellHeight),row, col, this);
                c.current_x = (float) Settings.WIDTH / 2.0F;
                c.target_x = c.current_x;
                c.current_y = -300.0F * Settings.scale;
                c.target_y = c.current_y;
                retVal.add(c);
                cardDict.put(row+ "," + col, c);
                i++;
            }
        }

        return retVal;
    }

    public float getWaitTimer() {
        return this.waitTimer;
    }

    public boolean isCompleted() {
        return screen == CUR_SCREEN.COMPLETE;
    }

    public void update() {
        super.update();
        this.cards.update();
        if (this.screen == GeoGuesserEvent.CUR_SCREEN.PLAY) {
            this.updateControllerInput();
            this.updateMatchGameLogic();
        } else if (this.screen == CUR_SCREEN.DISPLAY_RESULTS) {
            if (!this.displayResultsCalled) {
                this.displayResultsCalled = true;
                outlineActivate();
            }

            if (this.waitTimer > 0.0F) {
                this.waitTimer -= Gdx.graphics.getDeltaTime();
                if (this.waitTimer < 0.0F) {
                    this.waitTimer = 0.0F;
                }
            }

            if ((InputHelper.justClickedLeft
                    ||
                    (Settings.isControllerMode
                            && (CInputActionSet.up.isJustPressed()
                            || CInputActionSet.down.isJustPressed()
                            || CInputActionSet.left.isJustPressed()
                            || CInputActionSet.right.isJustPressed()
                            || CInputActionSet.altUp.isJustPressed()
                            || CInputActionSet.altDown.isJustPressed()
                            || CInputActionSet.altLeft.isJustPressed()
                            || CInputActionSet.altRight.isJustPressed()
                            || CInputActionSet.drawPile.isJustPressed()
                            || CInputActionSet.discardPile.isJustPressed()
                            ||  CInputActionSet.select.isJustPressed()
                            ||  CInputActionSet.cancel.isJustPressed())))
                    && this.waitTimer == 0.0F) {
                InputHelper.justClickedLeft = false;
                this.screen = GeoGuesserEvent.CUR_SCREEN.CLEAN_UP;
                this.waitTimer = 1.0F;
            }

        } else if (this.screen == GeoGuesserEvent.CUR_SCREEN.CLEAN_UP) {
            if (!this.cleanUpCalled) {
                this.cleanUpCalled = true;
                this.cleanUpCards();
            }

            if (this.waitTimer > 0.0F) {
                this.waitTimer -= Gdx.graphics.getDeltaTime();
                if (this.waitTimer < 0.0F) {
                    this.waitTimer = 0.0F;
                    this.screen = GeoGuesserEvent.CUR_SCREEN.COMPLETE;
                    GenericEventDialog.show();

                    if(this.distanceFromCorrectCard == 0){
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        this.imageEventText.setDialogOption(OPTIONS[1], new GeoGuesserRelic());
                    } else if(this.distanceFromCorrectCard == 1){
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        this.imageEventText.setDialogOption(OPTIONS[0]);
                        //displayCardReward();
                    } else {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4] + this.distanceFromCorrectCard + DESCRIPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        this.imageEventText.setDialogOption(OPTIONS[1], new GeoGuesserFailCurseCard());
                    }
                }
            }
        }

        if (!GenericEventDialog.waitForInput) {
            this.buttonEffect(GenericEventDialog.getSelectedOption());
        }


        //----- Non Shader 360 Viewer -----

        // Zoom Multiplier
        float t = (currentZoom - MIN_ZOOM) / (MAX_ZOOM - MIN_ZOOM);   // 0 … 1
        float zoomMult = MathUtils.lerp(0.05f, 1f, t);

        // Handle mouse wheel zoom
        if (InputHelper.scrolledDown) {
            targetZoom += ZOOM_SENSITIVITY;
        } else if (InputHelper.scrolledUp) {
            targetZoom -= ZOOM_SENSITIVITY;
        }
        if(Settings.isControllerMode) {
            if(CInputActionSet.drawPile.isJustPressed()){
                targetZoom -= (int)(ZOOM_SENSITIVITY * 2.5);
            }
            if(CInputActionSet.discardPile.isJustPressed()){
                targetZoom += (int)(ZOOM_SENSITIVITY * 2.5);
            }
        }

        targetZoom = MathUtils.clamp(targetZoom, MIN_ZOOM, MAX_ZOOM);

        // Smoothly interpolate current zoom
        currentZoom = MathUtils.lerp(currentZoom, targetZoom, 0.1f);

        if (InputHelper.justClickedLeft) {
            dragging = true;
            lastX = InputHelper.mX;
            lastY = InputHelper.mY;
        }

        if(Settings.isControllerMode) {
            float deltaX = 0F;
            float deltaY = 0F;

            if(CInputActionSet.up.isPressed()){
                deltaY = -5F;
            }
            if(CInputActionSet.down.isPressed()){
                deltaY = 5F;
            }
            if(CInputActionSet.left.isPressed()){
                deltaX = -5F;
            }
            if(CInputActionSet.right.isPressed()){
                deltaX = 5F;
            }

            deltaX = deltaX * BASE_SENSITIVITY * zoomMult;
            deltaY = deltaY * BASE_SENSITIVITY * zoomMult;

            // Update velocity based on mouse drag
            yawVelocity = deltaX;
            pitchVelocity = deltaY;

            lastX = InputHelper.mX;
            lastY = InputHelper.mY;
        }

        if (dragging && InputHelper.isMouseDown) {
            float deltaX = (InputHelper.mX - lastX) * BASE_SENSITIVITY * zoomMult;
            float deltaY = (InputHelper.mY - lastY) * BASE_SENSITIVITY * zoomMult;

            // Update velocity based on mouse drag
            yawVelocity = deltaX;
            pitchVelocity = deltaY;

            lastX = InputHelper.mX;
            lastY = InputHelper.mY;
        } else {
            dragging = false;
        }

        // Apply inertia
        targetYaw += Math.max(-15f, Math.min(yawVelocity, 15f));
        targetPitch += Math.max(-15f, Math.min(pitchVelocity, 15f));

        // limit yaw speed
        //targetYaw = MathUtils.clamp(targetYaw,-370f,  370f);

        // Apply friction to velocity
        yawVelocity *= FRICTION;
        pitchVelocity *= FRICTION;

        // Clamp pitch
        targetPitch = MathUtils.clamp(targetPitch, -85f, 85f);

        // Smoothly interpolate
        currentYaw = MathUtils.lerp(currentYaw, targetYaw, 0.1f);
        currentPitch = MathUtils.lerp(currentPitch, targetPitch, 0.1f);

        // Convert yaw/pitch to direction vector
        float yawRad = currentYaw * MathUtils.degreesToRadians;
        float pitchRad = currentPitch * MathUtils.degreesToRadians;

        float x = MathUtils.cos(pitchRad) * MathUtils.cos(yawRad);
        float y = MathUtils.sin(pitchRad);
        float z = MathUtils.cos(pitchRad) * MathUtils.sin(yawRad);

        camera.direction.set(x, y, z).nor();
        camera.up.set(0, 1, 0); // Maintain upright orientation

        // Zoom smoothing
        currentZoom = MathUtils.lerp(currentZoom, targetZoom, 0.1f);

        // Update position based on zoom (pull camera backward)
        camera.position.set(0, 0, 0).sub(camera.direction.cpy().scl(currentZoom));
        camera.update();


        borderFlashUpdate();

    }

    private void borderFlashTrigger(Color color, boolean additive) {
        this.borderFlashImg = ImageMaster.BORDER_GLOW_2;
        this.borderFlashDuration = 1.0F;
        this.borderFlashColor = color.cpy();
        this.borderFlashColor.a = 0.0F;
        this.borderFlashAdditive = additive;
        this.borderFlashActive = true;
    }

    private void borderFlashUpdate(){
        // Border Flash Update
        if (this.borderFlashActive) {
            if (1.0F - this.borderFlashDuration < 0.1F) {
                this.borderFlashColor.a = Interpolation.fade.apply(0.0F, 1.0F, (1.0F - this.borderFlashDuration) * 10.0F);
            } else {
                this.borderFlashColor.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.borderFlashDuration);
            }

            this.borderFlashDuration -= Gdx.graphics.getDeltaTime();
            if (this.borderFlashDuration < 0.0F) {
                this.borderFlashActive = false;
            }
        }
    }

    private void updateControllerInput() {
        if (Settings.isControllerMode) {
            boolean anyHovered = false;
            int index = 0;

            for (AbstractCard c : this.cards.group) {
                if (c.hb.hovered) {
                    anyHovered = true;
                    break;
                }

                ++index;
            }

            if (!anyHovered) {
                Gdx.input.setCursorPosition((int) ((AbstractCard) this.cards.group.get(0)).hb.cX, Settings.HEIGHT - (int) ((AbstractCard) this.cards.group.get(0)).hb.cY);
            } else {
                GeoGuesserTileCard currentCard = (GeoGuesserTileCard) this.cards.group.get(index);
                int newRow = currentCard.row;
                int newCol = currentCard.col;
                if (!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed()) {
                    if (!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed()) {
                        if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed()) {
                            if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                                // RIGHT
                                float x = (currentCard).hb.cX + GRID_X_GAP * Settings.scale;
                                if (x > (GRID_X + GRID_WIDTH) * Settings.scale) {
                                    x = GRID_X + GRID_X_GAP * Settings.scale;
                                }
                                logger.info("Set Cursor to:"+(int) x + ", " + (Settings.HEIGHT - (int) (currentCard).hb.cY));
                                Gdx.input.setCursorPosition((int) x, Settings.HEIGHT - (int) (currentCard).hb.cY);
                                newCol += 1;
                            }
                        } else {
                            // LEFT
                            float x = (currentCard).hb.cX - GRID_X_GAP * Settings.scale;
                            if (x < (GRID_X - GRID_X_GAP) * Settings.scale) {
                                x = (GRID_X + GRID_WIDTH) * Settings.scale;
                            }
                            logger.info("Set Cursor to:"+(int) x + ", " + (Settings.HEIGHT - (int) (currentCard).hb.cY));
                            Gdx.input.setCursorPosition((int) x, Settings.HEIGHT - (int) (currentCard).hb.cY);
                            newCol -= 1;
                        }
                    } else {
                        // DOWN
                        float y = (currentCard).hb.cY - Math.abs(GRID_Y_GAP) * Settings.scale;
                        if (y < (GRID_Y + GRID_HEIGHT + GRID_Y_GAP) * Settings.scale) {
                            y = GRID_Y * Settings.scale;
                        }
                        logger.info("Set Cursor to:"+(int) (int) (currentCard).hb.cX + ", " + (int) ((float) Settings.HEIGHT - y));
                        Gdx.input.setCursorPosition((int) (currentCard).hb.cX, (int) ((float) Settings.HEIGHT - y));
                        newRow += 1;
                    }
                } else {
                    // UP
                    float y = (currentCard).hb.cY + Math.abs(GRID_Y_GAP) * Settings.scale;
                    if (y > GRID_Y * Settings.scale) {
                        y = (GRID_Y + GRID_HEIGHT) * Settings.scale;
                    }
                    logger.info("Set Cursor to:"+(int) (int) (currentCard).hb.cX + ", " + (int) ((float) Settings.HEIGHT - y));
                    Gdx.input.setCursorPosition((int) (currentCard).hb.cX, (int) ((float) Settings.HEIGHT - y));
                    newRow -= 1;
                }

                // Move hovered card to match new cursor position
                GeoGuesserTileCard newCard = (GeoGuesserTileCard) this.cards.group.get(getCardIndexByRowAndCol(newRow, newCol));
                currentCard.hb.hovered = false;
                newCard.hb.hovered = true;
                //this.hoveredCard = newCard;
                //Gdx.input.setCursorPosition((int)((newCard).hb.cX + (GRID_X_GAP * 0.25f)), Settings.HEIGHT - (int) (newCard).hb.cY);

                if (CInputActionSet.select.isJustPressed()) {
                    CInputActionSet.select.unpress();
                    InputHelper.justClickedLeft = true;
                }
            }
        }
    }

    private void cleanUpCards() {
        for (AbstractCard c : this.cards.group) {
            c.targetDrawScale = drawScaleSmall;
            c.target_x = (float) Settings.WIDTH / 2.0F;
            c.target_y = -300.0F * Settings.scale;
        }

    }

    private void updateMatchGameLogic() {
        if (this.waitTimer == 0.0F) {
            this.hoveredCard = null;

            for (AbstractCard c : this.cards.group) {
                c.hb.update();
                if (this.hoveredCard == null && c.hb.hovered) {
                    c.drawScale = drawScaleMedium;
                    c.targetDrawScale = drawScaleMedium;
                    this.hoveredCard = (GeoGuesserTileCard)c;
                    if (InputHelper.justClickedLeft && this.hoveredCard.isFlipped) {
                        InputHelper.justClickedLeft = false;
                        if (this.chosenCard != null && this.chosenCard.uuid.equals(this.hoveredCard.uuid)) {
                            // Start scoring and ending event
                            GeoGuesserTileCard correctCard = getCorrectCard();
                            distanceFromCorrectCard = toroidalDistance(
                                    correctCard.row, correctCard.col,
                                    this.chosenCard.row, this.chosenCard.col);

                            if(distanceFromCorrectCard == 0) {
                                CardCrawlGame.sound.play(CORRECT_SFX_KEY);
                                borderFlashTrigger(Color.GREEN, true);
                            }
                            else if(distanceFromCorrectCard == 1) {
                                CardCrawlGame.sound.play(QUESTION_SFX_KEY);
                                borderFlashTrigger(Color.YELLOW, true);
                            } else {
                                CardCrawlGame.sound.play(WRONG_SFX_KEY);
                                borderFlashTrigger(Color.RED, true);
                            }

                            this.gameDone = true;
                            this.waitTimer = 0.5F;
                        } else {
                            if(this.chosenCard != null) {
                                this.chosenCard.targetDrawScale = drawScaleSmall;
                            }
                            this.chosenCard = this.hoveredCard; // update chosen card
                            this.waitTimer = 0.5F;
                            this.chosenCard.targetDrawScale = drawScaleMedium;
                        }
                    }
                }
                else {
                    if(this.chosenCard != null && this.hoveredCard != null && this.chosenCard.uuid.equals(c.uuid)){
                        c.drawScale = drawScaleMedium;
                        c.targetDrawScale = drawScaleMedium;
                    }
                    else {
                        c.drawScale = drawScaleSmall;
                        c.targetDrawScale = drawScaleSmall;
                    }
                }

                // Ensure chosen card is always medium scale
                if(this.chosenCard != null && c.uuid.equals(this.chosenCard.uuid)) {
                    c.drawScale = drawScaleMedium;
                    c.targetDrawScale = drawScaleMedium;
                }
            }
        } else {
            this.waitTimer -= Gdx.graphics.getDeltaTime();
            if (this.waitTimer < 0.0F && !this.gameDone) {
                this.waitTimer = 0.0F;
            } else if (this.gameDone) {
                this.screen = CUR_SCREEN.DISPLAY_RESULTS;
                this.waitTimer = 1.0F;

            }
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.updateDialogOption(1, OPTIONS[1]);
                        this.screen = GeoGuesserEvent.CUR_SCREEN.RULE_EXPLANATION;
                        return;
                    default:
                        return;
                }
            case RULE_EXPLANATION:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.removeDialogOption(0);
                        this.imageEventText.removeDialogOption(1);
                        GenericEventDialog.hide();
                        this.screen = GeoGuesserEvent.CUR_SCREEN.PLAY;
                        this.placeCards();
                        return;
                    case 1:
                        this.imageEventText.removeDialogOption(0);
                        this.imageEventText.removeDialogOption(1);
                        GenericEventDialog.hide();
                        this.screen = GeoGuesserEvent.CUR_SCREEN.COMPLETE;
                        this.openMap();

                        return;
                    default:
                        return;
                }
            case COMPLETE:
                //logMetricObtainCards("Match and Keep!", this.cardsMatched + " cards matched", this.matchedCards);

                if(this.distanceFromCorrectCard == 0){
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, new GeoGuesserRelic());
                } else if(this.distanceFromCorrectCard == 1){
                    this.imageEventText.updateBodyText(DESCRIPTIONS[11]);
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                    this.screen = GeoGuesserEvent.CUR_SCREEN.CARD_REWARD_COMPLETE;
                    displayCardReward();
                    return;
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new GeoGuesserFailCurseCard(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                }


                this.openMap();
                return;
            case CARD_REWARD_COMPLETE:
                if (this.pickCardReward && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard c = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
                this.openMap();
                return;
        }

    }

    private void displayCardReward(){
        this.pickCardReward = true;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for(int i = 0; i < 5; ++i) {
            AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
            boolean containsDupe = true;

            while(containsDupe) {
                containsDupe = false;

                for(AbstractCard c : group.group) {
                    if (c.cardID.equals(card.cardID)) {
                        containsDupe = true;
                        card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                        break;
                    }
                }
            }

            if (group.contains(card)) {
                --i;
            } else {
                for(AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(card);
                }

                group.addToBottom(card);
            }
        }

        for(AbstractCard c : group.group) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }

        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[6], false);
        return;
    }

    private void placeCards() {
        int i = 0;
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (i < this.cards.size()) {
                    ((AbstractCard) this.cards.group.get(i)).target_x = (float)col * GRID_X_GAP + GRID_X;
                    ((AbstractCard) this.cards.group.get(i)).target_y = (float)row * GRID_Y_GAP + GRID_Y;
                    ((AbstractCard) this.cards.group.get(i)).targetDrawScale = drawScaleSmall;
                    ((AbstractCard) this.cards.group.get(i)).isFlipped = true;
                    i++;
                }
            }
        }
    }

    private int getCardIndexByRowAndCol(int row, int col){
        if(row < 0) row = GRID_ROWS;
        if(row > GRID_ROWS) row = 0;
        if(col < 0) col = GRID_COLS;
        if(col > GRID_COLS) col = 0;
        // yes I know this is inefficient, sue me
        for(int i = 0; i < this.cards.group.size(); i++){
            GeoGuesserTileCard c = (GeoGuesserTileCard) this.cards.group.get(i);
            if(c.row == row && c.col == col){
                return i;
            }
        }
        return 0;
    }

    public void render(SpriteBatch sb) {
        this.cards.render(sb);
        if (this.chosenCard != null) {
            this.chosenCard.render(sb);
        }

        if (this.hoveredCard != null) {
            this.hoveredCard.render(sb);
        }

        if (this.screen == GeoGuesserEvent.CUR_SCREEN.PLAY || this.screen == CUR_SCREEN.DISPLAY_RESULTS ) {
            if(!this.displayResultsCalled) {
                if(!Settings.isControllerMode) {
                    FontHelper.renderSmartText(
                            sb,
                            FontHelper.panelNameFont,
                            DESCRIPTIONS[7],
                            Settings.WIDTH * 0.06f,
                            10.0F * Settings.scale + (VIEWPORT_HEIGHT - VIEWPORT_Y),
                            2000.0F * Settings.scale,
                            0.0F,
                            Color.WHITE
                    );
                }
                else {
                        FontHelper.renderSmartText(
                                sb,
                                FontHelper.panelNameFont,
                                DESCRIPTIONS[12],
                                Settings.WIDTH * 0.06f,
                                -40.0F * Settings.scale + (VIEWPORT_HEIGHT - VIEWPORT_Y),
                                2000.0F * Settings.scale,
                                0.0F,
                                Color.WHITE
                        );
                        FontHelper.renderSmartText(
                                sb,
                                FontHelper.panelNameFont,
                                DESCRIPTIONS[13],
                                Settings.WIDTH * 0.06f,
                                10.0F * Settings.scale + (VIEWPORT_HEIGHT - VIEWPORT_Y),
                                2000.0F * Settings.scale,
                                0.0F,
                                Color.WHITE
                        );
                    }
                if (chosenCard != null) {
                    FontHelper.renderSmartText(
                            sb,
                            FontHelper.panelNameFont,
                            DESCRIPTIONS[8],
                            Settings.WIDTH * 0.54f,
                            10.0F * Settings.scale + (VIEWPORT_HEIGHT - VIEWPORT_Y),
                            2000.0F * Settings.scale,
                            0.0F,
                            Color.WHITE
                    );
                } else {
                    FontHelper.renderSmartText(
                            sb,
                            FontHelper.panelNameFont,
                            DESCRIPTIONS[9],
                            Settings.WIDTH * 0.54f,
                            10.0F * Settings.scale + (VIEWPORT_HEIGHT - VIEWPORT_Y),
                            2000.0F * Settings.scale,
                            0.0F,
                            Color.WHITE);
                }
            }
            else {
                FontHelper.renderSmartText(
                        sb,
                        FontHelper.panelNameFont,
                        this.geoGuesserLocation.locationName +"    ("+ this.geoGuesserLocation.latitude + ", " + this.geoGuesserLocation.longitude + ")",
                        Settings.WIDTH * 0.54f,
                        10.0F * Settings.scale + (VIEWPORT_HEIGHT - VIEWPORT_Y),
                        2000.0F * Settings.scale,
                        0.0F,
                        Color.WHITE
                );

                FontHelper.renderSmartText(
                        sb,
                        FontHelper.panelNameFont,
                        DESCRIPTIONS[10],
                        (Settings.WIDTH * 0.5f) - 0.5f * (FontHelper.getSmartWidth(FontHelper.panelNameFont,DESCRIPTIONS[10], 500.0F * Settings.scale, 0.0f)),
                        -80.0F * Settings.scale + (VIEWPORT_HEIGHT - VIEWPORT_Y),
                        500.0F * Settings.scale,
                        0.0F,
                        Color.WHITE
                );
            }



            // Non Shader 360 Viewer
            sb.end();

            // Set the small viewport for 3D rendering
            Gdx.gl.glViewport((int)VIEWPORT_X, (int)VIEWPORT_Y, (int)VIEWPORT_WIDTH, (int)VIEWPORT_HEIGHT);

            // Adjust camera aspect for new viewport
            camera.viewportWidth = VIEWPORT_WIDTH;
            camera.viewportHeight = VIEWPORT_HEIGHT;
            camera.update();

            // Render 3D sphere into this small viewport
            modelBatch.begin(camera);
            modelBatch.render(sphereInstance);
            modelBatch.end();

            // Restore full screen for 2D rendering
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
            camera.viewportWidth = Settings.WIDTH;
            camera.viewportHeight = Settings.HEIGHT;
            camera.update();

            sb.begin(); // Resume 2D rendering
            super.render(sb);

            // Draw Border Flash
            if(this.borderFlashActive) {
                if (this.borderFlashAdditive) {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(this.borderFlashColor);
                    sb.draw(this.borderFlashImg, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
                    sb.setBlendFunction(770, 771);
                } else {
                    sb.setColor(this.borderFlashColor);
                    sb.draw(this.img, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
                }
            }
        }

    }

    private GeoGuesserTileCard getCorrectCard(){
        return cardDict.get(this.geoGuesserLocation.rowIndex + "," + this.geoGuesserLocation.columnIndex);
    }

    private void outlineActivate() {
        this.chosenCard.targetDrawScale = drawScaleSmall;
        for (int i = 0; i < this.cards.group.size(); i++) {
            GeoGuesserTileCard currentCard = (GeoGuesserTileCard) this.cards.group.get(i);
            GeoGuesserTileCard correctCard = getCorrectCard();
            int distance = toroidalDistance(currentCard.row, currentCard.col, correctCard.row, correctCard.col);
            boolean isChosenCard = this.chosenCard.uuid.equals(currentCard.uuid);

            switch (distance) {
                case 0:
                    currentCard.setOutline(Color.GREEN,isChosenCard, distance);
                    break;
                case 1:
                    currentCard.setOutline(Color.YELLOW,isChosenCard, distance);
                    break;
                default:
                    currentCard.setOutline(Color.RED,isChosenCard,distance);
                    break;
            }
        }
    }

    /**
     * Computes the wrap-around (toroidal) Manhattan distance between two cells
     * on the event grid whose origin (0,0) is the top-left corner.
     *
     * @param rA  row index of cell A
     * @param cA  column index of cell A
     * @param rB  row index of cell B
     * @param cB  column index of cell B
     * @return the minimal Chebyshev distance from A to B with wrap-around
     */
    public static int toroidalDistance(int rA, int cA, int rB, int cB) {
        // wrapped horizontal separation
        int dx = Math.abs(cA - cB);
        dx = Math.min(dx, GRID_COLS - dx);

        // wrapped vertical separation
        int dy = Math.abs(rA - rB);
        dy = Math.min(dy, GRID_ROWS - dy);

        // Chebyshev (square-ring) distance
        return Math.max(dx, dy);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void renderAboveTopPanel(SpriteBatch sb) {
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(makeID("GeoGuesserEvent"));
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;

        drawScaleSmall = 0.125F;
        drawScaleMedium = 0.25F;
        drawScaleLarge = 0.5F;
    }

    private static enum CUR_SCREEN {
        INTRO,
        RULE_EXPLANATION,
        PLAY,
        COMPLETE,
        CARD_REWARD_COMPLETE,
        DISPLAY_RESULTS,
        CLEAN_UP;

        private CUR_SCREEN() {
        }
    }
}
