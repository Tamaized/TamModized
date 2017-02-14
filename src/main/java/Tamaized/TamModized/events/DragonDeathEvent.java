package Tamaized.TamModized.events;

import Tamaized.TamModized.TamModized;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DragonDeathEvent {

	@SubscribeEvent
	public void onDeath(LivingDeathEvent e) {
		EntityLivingBase entity = e.getEntityLiving();
		World world = entity.world;
		if (!world.isRemote && world.provider instanceof WorldProviderEnd && entity instanceof EntityDragon && TamModized.config.getDragonEggConfig() && ((WorldProviderEnd) world.provider).getDragonFightManager().hasPreviouslyKilledDragon()) {
			world.spawnEntity(new EntityItem(world, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.DRAGON_EGG)));
		}
	}

}