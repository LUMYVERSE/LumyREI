package com.lumyverse.lumyrei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class ChiselingCategory implements DisplayCategory<ChiselingDisplay> {

    public static final CategoryIdentifier<ChiselingDisplay> ID = CategoryIdentifier.of("lumyrei", "chiseling");

    @Override
    public CategoryIdentifier<? extends ChiselingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.rechiseled.chiseling");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Registries.ITEM.get(Identifier.of("rechiseled", "chisel")));
    }

    @Override
    public List<Widget> setupDisplay(ChiselingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 9);
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));

        // Slot Input
        widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y))
                .entries(display.getInputEntries().getFirst())
                .markInput());

        // Freccia
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y)));

        // Slot Output
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 56, startPoint.y))
                .entries(display.getOutputEntries().getFirst())
                .markOutput());

        return widgets;
    }
}