package com.lumyverse.lumyrei;

import com.cobblemon.mod.common.item.crafting.CookingPotRecipeBase;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.RecipeEntry;

import java.util.Collections;

public class CookingPotDisplay extends BasicDisplay {

    public CookingPotDisplay(RecipeEntry<? extends CookingPotRecipeBase> recipe) {
        super(

                EntryIngredients.ofIngredients(recipe.value().getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.value().getResult()))
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CookingPotCategory.COOKING_POT;
    }
}