package com.lumyverse.lumyrei;

import com.cobblemon.mod.common.item.crafting.CookingPotRecipeBase;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.RecipeEntry;

import java.util.Collections;

public class CookingPotDisplay extends BasicDisplay {

    // Cambia il tipo nel costruttore da CookingPotShapelessRecipe a ? extends CookingPotRecipeBase
    public CookingPotDisplay(RecipeEntry<? extends CookingPotRecipeBase> recipe) {
        super(
                // Input: getIngredients() è definito in CookingPotRecipeBase (ereditato da Recipe)
                // Questo funzionerà sia per liste semplici che per griglie (se la ricetta shaped riempie gli spazi vuoti)
                EntryIngredients.ofIngredients(recipe.value().getIngredients()),

                // Output: getResult() è il getter Java per la proprietà "val result" di Kotlin definita nella Base
                Collections.singletonList(EntryIngredients.of(recipe.value().getResult()))
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CookingPotCategory.COOKING_POT;
    }
}