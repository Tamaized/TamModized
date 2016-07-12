package Tamaized.TamModized.tileentity;

import java.util.HashMap;
import java.util.Map;

import Tamaized.TamModized.TamModized;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class TamTileEntityRecipeList {
	
	/**
	 * Input, Output
	 */
	private Map<Item, TamTERecipe> recipes = new HashMap<Item, TamTERecipe>();
	private Map<ItemStack, ItemStack> rawMap = new HashMap<ItemStack, ItemStack>();
	
	public boolean registerRecipe(ItemStack stack, TamTERecipe recipe){
		if(isInput(stack.getItem())) return false;
		recipes.put(stack.getItem(), recipe);
		rawMap.put(stack, recipe.getResult());
		TamModized.logger.info("("+getModID()+":"+getName()+"): "+stack.getItem().getUnlocalizedName()+" => "+recipe.result);
		return true;
	}
	
	public boolean registerRecipe(String item, TamTERecipe recipe){
		for (ItemStack ore : OreDictionary.getOres(item)) {
			if (ore != null) {
				return registerRecipe(ore, recipe);
			}
		}
		return false;
	}
	
	public boolean isInput(Item item){
		return recipes.containsKey(item);
	}
	
	public boolean isInput(ItemStack stack){
		return stack == null ? null : isInput(stack.getItem());
	}
	
	public TamTERecipe getOutput(Item item){
		return recipes.get(item);
	}
	
	public TamTERecipe getOutput(ItemStack stack){
		return stack == null ? null : getOutput(stack.getItem());
	}
	
	public ItemStack getResultItem(Item item){
		if(!isInput(item)) return null;
		return getOutput(item).getResult();
	}
	
	public ItemStack getResultItem(ItemStack stack){
		if(stack == null) return null;
		return getResultItem(stack.getItem());
	}
	
	public Map<ItemStack, ItemStack> getRawMap(){
		return new HashMap<ItemStack, ItemStack>(rawMap);
	}
	
	protected abstract String getName();
	
	protected abstract String getModID();
	
	/**
	 * This holds the end result and additional requirement data
	 */
	public class TamTERecipe {
		
		private final ItemStack result;
		
		public TamTERecipe(ItemStack output){
			result = output;
		}
		
		public ItemStack getResult(){
			return result;
		}
		
	}

}
