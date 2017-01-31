package Tamaized.TamModized.tileentity;

import java.util.ArrayList;

import Tamaized.TamModized.tileentity.TamTileEntityRecipeList.TamTERecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class TamTileEntityRecipeList<T extends TamTERecipe> {

	private ArrayList<T> recipes = new ArrayList<T>();

	public final boolean registerRecipe(T recipe) {
		if (recipes.contains(recipe)) return false;
		recipes.add(recipe);
		return true;
	}

	public final ArrayList<T> getList() {
		return recipes;
	}

	public final boolean isInput(Item item) {
		for (T r : recipes) {
			for (ItemStack stack : r.getInput()) {
				if (stack.getItem() == item) return true;
			}
		}
		return false;
	}

	public final boolean isInput(ItemStack[] stacks) {
		loop: for (T recipe : recipes) {
			if (recipe.getInput().length != stacks.length) continue;
			for (ItemStack stack : stacks) {
				boolean flag2 = false;
				for (ItemStack checkStack : recipe.getInput()) {
					if (stack.getItem() == checkStack.getItem()) {
						flag2 = true;
						break;
					}
				}
				if (!flag2) continue loop;
			}
			return true;
		}
		return false;
	}

	public final boolean isInput(ItemStack stack) {
		for (T r : recipes) {
			for (ItemStack checkStack : r.getInput()) {
				if (checkStack.areItemStacksEqual(checkStack, stack)) return true;
			}
		}
		return false;
	}

	public final ItemStack getOutput(Item item) {
		for (T r : recipes) {
			if (isInput(item)) return r.getOutput();
		}
		return null;
	}

	public final ItemStack getOutput(ItemStack stack) {
		for (T r : recipes) {
			if (isInput(stack)) return r.getOutput();
		}
		return null;
	}

	public final ItemStack getOutput(ItemStack[] stacks) {
		loop: for (T recipe : recipes) {
			if (recipe.getInput().length != stacks.length) continue;
			for (ItemStack stack : stacks) {
				boolean flag2 = false;
				for (ItemStack checkStack : recipe.getInput()) {
					if (stack.getItem() == checkStack.getItem()) {
						flag2 = true;
						break;
					}
				}
				if (!flag2) continue loop;
			}
			return recipe.getOutput();
		}
		return null;
	}

	public final T getRecipe(ItemStack[] stacks) {
		loop: for (T recipe : recipes) {
			if (recipe.getInput().length != stacks.length) continue;
			for (ItemStack stack : stacks) {
				boolean flag2 = false;
				for (ItemStack checkStack : recipe.getInput()) {
					if (stack.getItem() == checkStack.getItem()) {
						flag2 = true;
						break;
					}
				}
				if (!flag2) continue loop;
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
