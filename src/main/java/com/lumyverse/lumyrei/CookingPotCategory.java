package com.lumyverse.lumyrei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.gui.DrawableConsumer;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import java.util.ArrayList;
import java.util.List;

public class CookingPotCategory implements DisplayCategory<CookingPotDisplay> {

    public static final CategoryIdentifier<CookingPotDisplay> COOKING_POT =
            CategoryIdentifier.of("cobblemon", "cooking_pot_shapeless");

    private static final Identifier GUI_BACKGROUND_TEXTURE = Identifier.of("lumyrei", "textures/gui/rei/campfire_pot.png");

    private static final Identifier PROGRESS_ARROW_TEXTURE = Identifier.of("lumyrei", "textures/gui/rei/cook_progress.png");

    // Dimensioni della GUI Cobblemon
    private static final int TEXTURE_WIDTH = 146;
    private static final int TEXTURE_HEIGHT = 59;
    private static final int SLOT_SIZE = 18;

    private static final int HORIZONTAL_PADDING = 8;
    private static final int VERTICAL_PADDING = 5;

    // Posizioni assolute (rispetto all'angolo in alto a sinistra della texture 146x59)
    private static final int INPUT_GRID_START_X = 16;
    private static final int INPUT_GRID_START_Y = 1;

    private static final int SEASONING_SLOT_START_X = 93;
    private static final int SEASONING_SLOT_START_Y = 1;
    private static final int SEASONING_SLOT_COUNT = 3;

    private static final int ARROW_POS_X = 79;
    private static final int ARROW_POS_Y = 22;

    private static final int ARROW_WIDTH = 22;
    private static final int ARROW_HEIGHT = 12;

    private static final int ARROW_DRAW_WIDTH = 22;
    private static final int ARROW_DRAW_HEIGHT = 12;

    // Offset UV (se la freccia non parte da 0,0 nel suo PNG)
    private static final int ARROW_UV_U = 0;
    private static final int ARROW_UV_V = 0;

    private static final int OUTPUT_SLOT_POS_X = 111;
    private static final int OUTPUT_SLOT_POS_Y = 38;

    // Durata dell'animazione in ticks. 100 ticks = 5 secondi
    private static final int ANIMATION_DURATION_TICKS = 100;

    @Override
    public CategoryIdentifier<? extends CookingPotDisplay> getCategoryIdentifier() {
        return COOKING_POT;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("cobblemon.container.campfire_pot");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Registries.ITEM.get(Identifier.of("cobblemon", "campfire_pot_blue")));
    }

    @Override
    public int getDisplayWidth(CookingPotDisplay display) {
        return TEXTURE_WIDTH + HORIZONTAL_PADDING * 2;
    }

    @Override
    public int getDisplayHeight() {
        return TEXTURE_HEIGHT + VERTICAL_PADDING * 2;
    }

    @Override
    public List<Widget> setupDisplay(CookingPotDisplay display, Rectangle bounds) {

        Point textureStartPoint = new Point(bounds.x + HORIZONTAL_PADDING, bounds.y + VERTICAL_PADDING);

        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createTooltip(bounds));

        // Disegna la texture di Sfondo
        widgets.add(Widgets.createTexturedWidget(
                GUI_BACKGROUND_TEXTURE,
                textureStartPoint.x, textureStartPoint.y,
                0, 0,
                TEXTURE_WIDTH, TEXTURE_HEIGHT,
                TEXTURE_WIDTH, TEXTURE_HEIGHT
        ));

        List<EntryIngredient> allInputs = display.getInputEntries();

        // Posiziona gli slot di Input (Griglia 3x3)
        int inputGridCount = 9;

        for (int i = 0; i < inputGridCount; i++) {

            int x = textureStartPoint.x + INPUT_GRID_START_X + (i % 3) * SLOT_SIZE;
            int y = textureStartPoint.y + INPUT_GRID_START_Y + (i / 3) * SLOT_SIZE;

            if (i < allInputs.size()) {
                widgets.add(Widgets.createSlot(new Point(x, y)).entries(allInputs.get(i)).markInput());
            } else {
                widgets.add(Widgets.createSlot(new Point(x, y)).markInput());
            }
        }

        // Posiziona gli slot Condimento (Seasonings)
        for (int i = 0; i < SEASONING_SLOT_COUNT; i++) {
            int currentIngredientIndex = inputGridCount + i;

            int x = textureStartPoint.x + SEASONING_SLOT_START_X + i * SLOT_SIZE;
            int y = textureStartPoint.y + SEASONING_SLOT_START_Y;

            if (currentIngredientIndex < allInputs.size()) {
                widgets.add(Widgets.createSlot(new Point(x, y)).entries(allInputs.get(currentIngredientIndex)).markInput());
            } else {
                widgets.add(Widgets.createSlot(new Point(x, y)).markInput());
            }
        }

        // Freccia di Progresso
        widgets.add(createCustomArrowWidget(textureStartPoint));

        // Output Slot
        Point outputSlotPoint = new Point(textureStartPoint.x + OUTPUT_SLOT_POS_X, textureStartPoint.y + OUTPUT_SLOT_POS_Y);

        widgets.add(Widgets.createSlot(outputSlotPoint)
                .entries(display.getOutputEntries().getFirst())
                .disableBackground()
                .markOutput());

        return widgets;
    }

    private Widget createCustomArrowWidget(Point textureStartPoint) {

        // Calcola la posizione assoluta della freccia sullo schermo
        int x = textureStartPoint.x + ARROW_POS_X;
        int y = textureStartPoint.y + ARROW_POS_Y;

        // La logica di rendering e animazione
        DrawableConsumer drawable = (DrawContext drawContext, int mouseX, int mouseY, float delta) -> {

            // Calcola il progresso di riempimento da 0.0 a 1.0 (ciclico)
            double speed = 1D / ANIMATION_DURATION_TICKS;
            double progress = (System.currentTimeMillis() / 50D * speed) % 1.0D;

            int filledWidth = (int) Math.max(1, ARROW_DRAW_WIDTH * progress);

            drawContext.drawTexture(
                    PROGRESS_ARROW_TEXTURE,
                    x, y,
                    ARROW_UV_U, ARROW_UV_V,
                    filledWidth, ARROW_DRAW_HEIGHT,
                    ARROW_WIDTH, ARROW_HEIGHT
            );
        };

        return Widgets.createDrawableWidget(drawable);
    }
}