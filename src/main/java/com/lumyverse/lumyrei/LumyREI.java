package com.lumyverse.lumyrei;

import com.cobblemon.mod.common.CobblemonRecipeTypes;
import com.cobblemon.mod.common.client.gui.cookingpot.CookingPotScreen;
import com.cobblemon.mod.common.item.crafting.CookingPotRecipe;
import com.cobblemon.mod.common.item.crafting.CookingPotShapelessRecipe;
import com.cobblemon.mod.common.item.crafting.brewingstand.BrewingStandRecipe;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class LumyREI implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {

        registry.add(new CookingPotCategory()); // Categoria Cooking Pot

        registry.addWorkstations(
                CookingPotCategory.COOKING_POT,
                EntryStacks.of(Registries.ITEM.get(Identifier.of("cobblemon", "campfire_pot_red"))) // Icona Workstation
        );

        registry.add(new BrewingStandCategory()); // Categoria Brewing Stand

        registry.addWorkstations(
                BrewingStandCategory.BREWING,
                EntryStacks.of(Items.BREWING_STAND) // Icona Workstation
        );
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {

        // Cooking Pot (Shapeless)
        registry.registerRecipeFiller(
                CookingPotShapelessRecipe.class,
                CobblemonRecipeTypes.INSTANCE.getCOOKING_POT_SHAPELESS(),
                CookingPotDisplay::new
        );

        // Cooking Pot (Shaped)
        registry.registerRecipeFiller(
                CookingPotRecipe.class,
                CobblemonRecipeTypes.INSTANCE.getCOOKING_POT_COOKING(),
                CookingPotDisplay::new
        );

        // Brewing Stand
        registry.registerRecipeFiller(
                BrewingStandRecipe.class,
                CobblemonRecipeTypes.INSTANCE.getBREWING_STAND(),
                BrewingStandDisplay::new
        );
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(
                (screen.width - 176) / 2 + 78,
                ((screen.height - 166) / 2) + 38,
                20,
                25
        ), CookingPotScreen.class, CookingPotCategory.COOKING_POT);
    }
}