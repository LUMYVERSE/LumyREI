package com.lumyverse.lumyrei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import java.util.Collections;
import java.util.List;

public class ChiselingDisplay extends BasicDisplay {

    public ChiselingDisplay(List<ItemStack> items) {
        super(
                Collections.singletonList(EntryIngredients.ofItemStacks(items)),
                Collections.singletonList(EntryIngredients.ofItemStacks(items))
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ChiselingCategory.ID;
    }
}