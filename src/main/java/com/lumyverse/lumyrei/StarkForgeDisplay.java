package com.lumyverse.lumyrei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import java.util.List;

public class StarkForgeDisplay extends BasicDisplay {

    private final int cookTime;

    public StarkForgeDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, int cookTime) {
        super(inputs, outputs);
        this.cookTime = cookTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return StarkForgeCategory.ID;
    }
}