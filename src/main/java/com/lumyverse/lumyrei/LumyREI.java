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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.Collections;

public class LumyREI implements REIClientPlugin {

    private static Field xField;

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CookingPotCategory());
        registry.addWorkstations(CookingPotCategory.COOKING_POT, EntryStacks.of(Registries.ITEM.get(Identifier.of("cobblemon", "campfire_pot_red"))));

        registry.add(new BrewingStandCategory());
        registry.addWorkstations(BrewingStandCategory.BREWING, EntryStacks.of(Items.BREWING_STAND));

        registry.add(new StarkForgeCategory());
        registry.addWorkstations(StarkForgeCategory.ID, EntryStacks.of(Registries.ITEM.get(Identifier.of("lumymon", "stark_forge"))));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {

        registry.registerRecipeFiller(CookingPotShapelessRecipe.class, CobblemonRecipeTypes.INSTANCE.getCOOKING_POT_SHAPELESS(), CookingPotDisplay::new);
        registry.registerRecipeFiller(CookingPotRecipe.class, CobblemonRecipeTypes.INSTANCE.getCOOKING_POT_COOKING(), CookingPotDisplay::new);
        registry.registerRecipeFiller(BrewingStandRecipe.class, CobblemonRecipeTypes.INSTANCE.getBREWING_STAND(), BrewingStandDisplay::new);

        try {
            Identifier starkRecipeTypeId = Identifier.of("lumymon", "stark_forging");
            registry.registerFiller(net.minecraft.recipe.RecipeEntry.class,
                    entry -> {
                        if (entry == null || entry.value() == null) return false;
                        return entry.value().getType().toString().contains("stark_forging");
                    },
                    recipeEntry -> {
                        try {
                            Object recipe = recipeEntry.value();
                            net.minecraft.recipe.Ingredient input = (net.minecraft.recipe.Ingredient) recipe.getClass().getMethod("getInput").invoke(recipe);
                            ItemStack output = (ItemStack) recipe.getClass().getMethod("getOutput").invoke(recipe);
                            int cookTime = (int) recipe.getClass().getMethod("getCookTime").invoke(recipe);

                            return new StarkForgeDisplay(
                                    Collections.singletonList(me.shedaniel.rei.api.common.util.EntryIngredients.ofIngredient(input)),
                                    Collections.singletonList(me.shedaniel.rei.api.common.util.EntryIngredients.of(output)),
                                    cookTime
                            );
                        } catch (Exception e) { return null; }
                    }
            );
        } catch (Exception ignored) {}
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> {
            int guiLeft = getGuiLeft(screen);
            int centerY = screen.height / 2;
            return new Rectangle(guiLeft + 96, centerY - 44, 21, 12);
        }, CookingPotScreen.class, CookingPotCategory.COOKING_POT);
    }

    private int getGuiLeft(Screen screen) {
        if (!(screen instanceof HandledScreen)) return 0;
        try {
            if (xField == null) {
                try { xField = HandledScreen.class.getDeclaredField("x"); }
                catch (NoSuchFieldException e) { xField = HandledScreen.class.getDeclaredField("field_2776"); }
                xField.setAccessible(true);
            }
            return xField.getInt(screen);
        } catch (Exception e) { return (screen.width - 146) / 2; }
    }
}