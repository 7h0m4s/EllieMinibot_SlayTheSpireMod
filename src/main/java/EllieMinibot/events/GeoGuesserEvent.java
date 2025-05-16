package EllieMinibot.events;

import EllieMinibot.cards.specialcards.GeoGuesserTileCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
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
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static EllieMinibot.ModFile.makeID;

public class GeoGuesserEvent extends AbstractImageEvent {
    public static final String ID = makeID("GeoGuesserEvent");
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private AbstractCard chosenCard;
    private AbstractCard hoveredCard;
    private boolean cardFlipped = false;
    private boolean gameDone = false;
    private boolean cleanUpCalled = false;
    private int attemptCount = 5;
    private CardGroup cards;
    private float waitTimer;
    private int cardsMatched;
    private GeoGuesserEvent.CUR_SCREEN screen;
    private static final String MSG_2;
    private static final String MSG_3;
    private List<String> matchedCards;
    
    private static float drawScaleSmall;
    private static float drawScaleMedium;
    private static float drawScaleLarge;

    private int GRID_ROWS = 6;
    private int GRID_COLS = 8;

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
    private final float MIN_ZOOM = -10f;
    private final float MAX_ZOOM = 10f;
    private final float ZOOM_SENSITIVITY = 1.5f;

    public GeoGuesserEvent() {
        super(NAME, DESCRIPTIONS[2], "ellieminibotResources/images/events/GeoGuesserEvent.png");


        this.cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        this.waitTimer = 0.0F;
        this.cardsMatched = 0;
        this.screen = GeoGuesserEvent.CUR_SCREEN.INTRO;
        Collections.shuffle(this.cards.group, new Random(AbstractDungeon.miscRng.randomLong()));
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.matchedCards = new ArrayList();

        this.portraitImg = ImageMaster.loadImage("ellieminibotResources/images/events/GeoGuesserEvent.png");
        this.worldTexture = ImageMaster.loadImage("ellieminibotResources/images/events/GeoGuesserEvent_WorldMap.png");


        this.cards.group = this.initializeCards();


        init3D();
    }

    private void init3D() {
        panoTexture = new Texture(Gdx.files.internal("ellieminibotResources/images/events/GeoGuesser/panorama_00.jpg"));
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
                GeoGuesserTileCard c = new GeoGuesserTileCard(new TextureRegion(worldTexture,x,y,cellWidth,cellHeight),row, col);
                c.current_x = (float) Settings.WIDTH / 2.0F;
                c.target_x = c.current_x;
                c.current_y = -300.0F * Settings.scale;
                c.target_y = c.current_y;
                retVal.add(c);
                i++;

            }
        }

        return retVal;
    }

    public void update() {
        super.update();
        this.cards.update();
        if (this.screen == GeoGuesserEvent.CUR_SCREEN.PLAY) {
            this.updateControllerInput();
            this.updateMatchGameLogic();
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
                    this.imageEventText.updateBodyText(MSG_3);
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                }
            }
        }

        if (!GenericEventDialog.waitForInput) {
            this.buttonEffect(GenericEventDialog.getSelectedOption());
        }



        // Non Shader 360 Viewer

        // Handle mouse wheel zoom
        if (InputHelper.scrolledDown) {
            targetZoom += ZOOM_SENSITIVITY;
        } else if (InputHelper.scrolledUp) {
            targetZoom -= ZOOM_SENSITIVITY;
        }
        targetZoom = MathUtils.clamp(targetZoom, MIN_ZOOM, MAX_ZOOM);

        // Smoothly interpolate current zoom
        currentZoom = MathUtils.lerp(currentZoom, targetZoom, 0.1f);

        if (InputHelper.justClickedLeft) {
            dragging = true;
            lastX = InputHelper.mX;
            lastY = InputHelper.mY;
        }

        if (dragging && InputHelper.isMouseDown) {
            float deltaX = InputHelper.mX - lastX;
            float deltaY = InputHelper.mY - lastY;

            // Update velocity based on mouse drag
            yawVelocity = deltaX * SENSITIVITY;
            pitchVelocity = -deltaY * SENSITIVITY;

            lastX = InputHelper.mX;
            lastY = InputHelper.mY;
        } else {
            dragging = false;
        }

        // Apply inertia
        targetYaw += yawVelocity;
        targetPitch += pitchVelocity;

        // Apply friction to velocity
        yawVelocity *= FRICTION;
        pitchVelocity *= FRICTION;

        // Clamp pitch
        targetPitch = MathUtils.clamp(targetPitch, -85f, 85f);

        // Smoothly interpolate
        currentYaw = MathUtils.lerpAngleDeg(currentYaw, targetYaw, 0.1f);
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
                if (!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed()) {
                    if (!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed()) {
                        if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed()) {
                            if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                                float x = ((AbstractCard) this.cards.group.get(index)).hb.cX + 210.0F * Settings.scale;
                                if (x > 1375.0F * Settings.scale) {
                                    x = 640.0F * Settings.scale;
                                }

                                Gdx.input.setCursorPosition((int) x, Settings.HEIGHT - (int) ((AbstractCard) this.cards.group.get(index)).hb.cY);
                            }
                        } else {
                            float x = ((AbstractCard) this.cards.group.get(index)).hb.cX - 210.0F * Settings.scale;
                            if (x < 530.0F * Settings.scale) {
                                x = 1270.0F * Settings.scale;
                            }

                            Gdx.input.setCursorPosition((int) x, Settings.HEIGHT - (int) ((AbstractCard) this.cards.group.get(index)).hb.cY);
                        }
                    } else {
                        float y = ((AbstractCard) this.cards.group.get(index)).hb.cY - 230.0F * Settings.scale;
                        if (y < 175.0F * Settings.scale) {
                            y = 750.0F * Settings.scale;
                        }

                        Gdx.input.setCursorPosition((int) ((AbstractCard) this.cards.group.get(index)).hb.cX, (int) ((float) Settings.HEIGHT - y));
                    }
                } else {
                    float y = ((AbstractCard) this.cards.group.get(index)).hb.cY + 230.0F * Settings.scale;
                    if (y > 865.0F * Settings.scale) {
                        y = 290.0F * Settings.scale;
                    }

                    Gdx.input.setCursorPosition((int) ((AbstractCard) this.cards.group.get(index)).hb.cX, (int) ((float) Settings.HEIGHT - y));
                }

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
                    this.hoveredCard = c;
                    if (InputHelper.justClickedLeft && this.hoveredCard.isFlipped) {
                        InputHelper.justClickedLeft = false;
                        this.hoveredCard.isFlipped = false;
                        if (!this.cardFlipped) {
                            this.cardFlipped = true;
                            this.chosenCard = this.hoveredCard;
                        } else {
                            this.cardFlipped = false;
                            if (this.chosenCard.cardID.equals(this.hoveredCard.cardID)) {
                                this.waitTimer = 1.0F;
                                this.chosenCard.targetDrawScale = drawScaleMedium;
                                this.chosenCard.target_x = (float) Settings.WIDTH / 2.0F;
                                this.chosenCard.target_y = (float) Settings.HEIGHT / 2.0F;
                                this.hoveredCard.targetDrawScale = drawScaleMedium;
                                this.hoveredCard.target_x = (float) Settings.WIDTH / 2.0F;
                                this.hoveredCard.target_y = (float) Settings.HEIGHT / 2.0F;
                            } else {
                                this.waitTimer = 1.25F;
                                this.chosenCard.targetDrawScale = drawScaleLarge;
                                this.hoveredCard.targetDrawScale = drawScaleLarge;
                            }
                        }
                    }
                } else if (c != this.chosenCard) {
                    c.targetDrawScale = drawScaleSmall;
                }
            }
        } else {
            this.waitTimer -= Gdx.graphics.getDeltaTime();
            if (this.waitTimer < 0.0F && !this.gameDone) {
                this.waitTimer = 0.0F;
                if (this.chosenCard.cardID.equals(this.hoveredCard.cardID)) {
                    ++this.cardsMatched;
                    this.cards.group.remove(this.chosenCard);
                    this.cards.group.remove(this.hoveredCard);
                    this.matchedCards.add(this.chosenCard.cardID);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.chosenCard.makeCopy(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    this.chosenCard = null;
                    this.hoveredCard = null;
                } else {
                    this.chosenCard.isFlipped = true;
                    this.hoveredCard.isFlipped = true;
                    this.chosenCard.targetDrawScale = drawScaleSmall;
                    this.hoveredCard.targetDrawScale = drawScaleSmall;
                    this.chosenCard = null;
                    this.hoveredCard = null;
                }

                --this.attemptCount;
                if (this.attemptCount == 0) {
                    this.gameDone = true;
                    this.waitTimer = 1.0F;
                }
            } else if (this.gameDone) {
                this.screen = GeoGuesserEvent.CUR_SCREEN.CLEAN_UP;
            }
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(MSG_2);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.screen = GeoGuesserEvent.CUR_SCREEN.RULE_EXPLANATION;
                        return;
                    default:
                        return;
                }
            case RULE_EXPLANATION:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.removeDialogOption(0);
                        GenericEventDialog.hide();
                        this.screen = GeoGuesserEvent.CUR_SCREEN.PLAY;
                        this.placeCards();
                        return;
                    default:
                        return;
                }
            case COMPLETE:
                logMetricObtainCards("Match and Keep!", this.cardsMatched + " cards matched", this.matchedCards);
                this.openMap();
        }

    }

    private void placeCards() {
       /* for (int i = 0; i < this.cards.size(); ++i) {
            ((AbstractCard) this.cards.group.get(i)).target_x = (float) (i % gridWidth) * 210.0F * Settings.xScale + 640.0F * Settings.xScale;
            ((AbstractCard) this.cards.group.get(i)).target_y = (float) (i % gridHeight) * -230.0F * Settings.yScale + 750.0F * Settings.yScale;
            ((AbstractCard) this.cards.group.get(i)).targetDrawScale = 0.25F;
            ((AbstractCard) this.cards.group.get(i)).isFlipped = true;
        }*/

        int i = 0;
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (i < this.cards.size()) {
                    //grid.add(new Cell(row, col, objects.get(index++)));
                    ((AbstractCard) this.cards.group.get(i)).target_x = (float) (col) * 70.0F * Settings.xScale + 1220.0F * Settings.xScale;
                    ((AbstractCard) this.cards.group.get(i)).target_y = (float) (row) * -80.0F * Settings.yScale + 650.0F * Settings.yScale;
                    ((AbstractCard) this.cards.group.get(i)).targetDrawScale = drawScaleSmall;
                    ((AbstractCard) this.cards.group.get(i)).isFlipped = true;
                    i++;
                }
            }
        }

    }

    public void render(SpriteBatch sb) {
        this.cards.render(sb);
        if (this.chosenCard != null) {
            this.chosenCard.render(sb);
        }

        if (this.hoveredCard != null) {
            this.hoveredCard.render(sb);
        }

        if (this.screen == GeoGuesserEvent.CUR_SCREEN.PLAY) {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, OPTIONS[3] + this.attemptCount, 780.0F * Settings.scale, 80.0F * Settings.scale, 2000.0F * Settings.scale, 0.0F, Color.WHITE);


            //drawTextureScaled(sb,this.worldImg,200.0F, 300.0F);


            // Non Shader 360 Viewer
            sb.end();
            // Calculate viewport size (¼ of screen width)
            int width = 1200;
            int height = 800;
            int x = 175;
            int y = 400;

            // Set the small viewport for 3D rendering
            Gdx.gl.glViewport(x, y, width, height);

            // Adjust camera aspect for new viewport
            camera.viewportWidth = width;
            camera.viewportHeight = height;
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

            // Draw the rest of your event
        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void renderAboveTopPanel(SpriteBatch sb) {
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Match and Keep!");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        MSG_2 = DESCRIPTIONS[0];
        MSG_3 = DESCRIPTIONS[1];

        drawScaleSmall = 0.125F;
        drawScaleMedium = 0.25F;
        drawScaleLarge = 0.5F;
    }

    private static enum CUR_SCREEN {
        INTRO,
        RULE_EXPLANATION,
        PLAY,
        COMPLETE,
        CLEAN_UP;

        private CUR_SCREEN() {
        }
    }
}
