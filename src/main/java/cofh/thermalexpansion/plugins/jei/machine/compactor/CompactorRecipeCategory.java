package cofh.thermalexpansion.plugins.jei.machine.compactor;

import cofh.core.util.helpers.StringHelper;
import cofh.thermalexpansion.gui.client.machine.GuiCompactor;
import cofh.thermalexpansion.plugins.jei.Drawables;
import cofh.thermalexpansion.plugins.jei.RecipeUidsTE;
import cofh.thermalexpansion.plugins.jei.machine.BaseRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class CompactorRecipeCategory extends BaseRecipeCategory<CompactorRecipeWrapper> {

	public static boolean enable = true;

	public static void register(IRecipeCategoryRegistration registry) {

		if (!enable) {
			return;
		}
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(new CompactorRecipeCategoryPress(guiHelper));
		registry.addRecipeCategories(new CompactorRecipeCategoryStorage(guiHelper));
		registry.addRecipeCategories(new CompactorRecipeCategoryMint(guiHelper));
		registry.addRecipeCategories(new CompactorRecipeCategoryGear(guiHelper));
	}

	public static void initialize(IModRegistry registry) {

		if (!enable) {
			return;
		}
		CompactorRecipeCategoryPress.initialize(registry);
		CompactorRecipeCategoryStorage.initialize(registry);
		CompactorRecipeCategoryMint.initialize(registry);
		CompactorRecipeCategoryGear.initialize(registry);
		registry.addRecipeClickArea(GuiCompactor.class, 79, 34, 24, 16, RecipeUidsTE.COMPACTOR_PRESS, RecipeUidsTE.COMPACTOR_STORAGE, RecipeUidsTE.COMPACTOR_MINT, RecipeUidsTE.COMPACTOR_GEAR);
	}

	protected IDrawableStatic progress;
	protected IDrawableStatic speed;

	public CompactorRecipeCategory(IGuiHelper guiHelper) {

		background = guiHelper.createDrawable(GuiCompactor.TEXTURE, 26, 11, 124, 62, 0, 0, 16, 24);
		energyMeter = Drawables.getDrawables(guiHelper).getEnergyEmpty();
		localizedName = StringHelper.localize("tile.thermalexpansion.machine.compactor.name");

		progress = Drawables.getDrawables(guiHelper).getProgress(Drawables.PROGRESS_ARROW);
		speed = Drawables.getDrawables(guiHelper).getScale(Drawables.SCALE_COMPACT);
	}

	@Override
	public IDrawable getIcon() {

		return icon;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {

		progress.draw(minecraft, 69, 23);
		speed.draw(minecraft, 43, 33);
		energyMeter.draw(minecraft, 2, 8);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CompactorRecipeWrapper recipeWrapper, IIngredients ingredients) {

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 42, 14);
		guiItemStacks.init(1, false, 105, 23);

		guiItemStacks.set(0, inputs.get(0));
		guiItemStacks.set(1, outputs.get(0));
	}

}
