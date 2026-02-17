package com.lumyverse.lumyrei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class StarkForgeCategory implements DisplayCategory<StarkForgeDisplay> {
    public static final CategoryIdentifier<StarkForgeDisplay> ID = CategoryIdentifier.of("lumymon", "stark_forging");

    @Override
    public CategoryIdentifier<? extends StarkForgeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("container.lumymon.stark_forge");
    }

    @Override
    public Renderer getIcon() {

        return EntryStacks.of(Registries.ITEM.get(Identifier.of("lumymon", "stark_forge")));
    }

    @Override
    public List<Widget> setupDisplay(StarkForgeDisplay display, Rectangle bounds) {

        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 6);
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));

        // 1. Slot Input
        widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y - 11))
                .entries(display.getInputEntries().getFirst())
                .markInput());

        // 2. Slot Carburante (Lava)
        widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y + 11))
                .entries(EntryIngredients.of(Items.LAVA_BUCKET))
                .markInput());

        // 3. Freccia
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y))
                .animationDurationTicks(display.getCookTime()));

        // 4. Slot Output
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y))
                .entries(display.getOutputEntries().getFirst())
                .markOutput());

        return widgets;
    }
}