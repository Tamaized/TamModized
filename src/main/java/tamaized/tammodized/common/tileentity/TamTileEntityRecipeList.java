package tamaized.tammodized.common.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tamaized.tammodized.common.tileentity.TamTileEntityRecipeList.TamTERecipe;

import java.util.ArrayList;
import java.util.List;

public abstract class TamTileEntityRecipeList<T extends TamTERecipe> {

	private List<T> recipes = new ArrayList<>();

	public final boolean registerRecipe(T recipe) {
		if (recipes.contains(recipe))
			return false;
		recipes.add(recipe);
		return true;
	}

	public final List<T> getList() {
		return recipes;
	}

	public final boolean isInput(Item item, int meta) {
		for (T r : recipes) {
			for (ItemStack stack : r.getInput()) {
				if (stack.getItem() == item && stack.getMetadata() == meta)
					return true;
			}
		}
		return false;
	}

	public final boolean isInput(ItemStack[] stacks) {
		loop:
		for (T recipe : recipes) {
			if (recipe.getInput().length != stacks.length)
				continue;
			for (ItemStack stack : stacks) {
				boolean flag2 = false;
				for (ItemStack checkStack : recipe.getInput()) {
					if (ItemStack.areItemsEqual(stack, checkStack)) {
						flag2 = true;
						break;
					}
				}
				if (!flag2)
					continue loop;
			}
			return true;
		}
		return false;
	}

	public final boolean isInput(ItemStack stack) {
		for (T r : recipes) {
			for (ItemStack checkStack : r.getInput()) {
				if (ItemStack.areItemsEqual(checkStack, stack))
					return true;
			}
		}
		return false;
	}

	public final ItemStack getOutput(Item item, int meta) {
		for (T r : recipes) {
			if (isInput(item, meta))
				return r.getOutput();
		}
		return ItemStack.EMPTY;
	}

	public final ItemStack getOutput(ItemStack stack) {
		for (T r : recipes)
			for (ItemStack checkStack : r.getInput())
				if (ItemStack.areItemsEqual(checkStack, stack))
					return r.getOutput();
		return ItemStack.EMPTY;
	}

	public final ItemStack getOutput(ItemStack[] stacks) {
		loop:
		for (T recipe : recipes) {
			if (recipe.getInput().length != stacks.length)
				continue;
			for (ItemStack stack : stacks) {
				boolean flag2 = false;
				for (ItemStack checkStack : recipe.getInput()) {
					if (ItemStack.areItemsEqual(checkStack, stack)) {
						flag2 = true;
						break;
					}
				}
				if (!flag2)
					continue loop;
			}
			return recipe.getOutput();
		}
		return ItemStack.EMPTY;
	}

	public final T getRecipe(ItemStack[] stacks) {
		loop:
		for (T recipe : recipes) {
			if (recipe.getInput().length != stacks.length)
				continue;
			for (ItemStack stack : stacks) {
				boolean flag2 = false;
				for (ItemStack checkStack : recipe.getInput()) {
					if (ItemStack.areItemsEqual(checkStack, stack)) {
						flag2 = true;
						break;
					}
				}
				if (!flag2)
					continue loop;
			}
			return recipe;
		}
		return null;
	}

	protected abstract String getName();

	protected abstract String getModID();

	/**
	 * This holds the end result and additional requirement data
	 */
	public class TamTERecipe {

		private final ItemStack[] input;
		private final ItemStack output;

		public TamTERecipe(ItemStack[] input, ItemStack output) {
			this.input = input;
			this.output = output;
		}

		public ItemStack[] getInput() {
			return input;
		}

		public ItemStack getOutput() {
			return output;
		}

	}

}
