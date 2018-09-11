package tamaized.tammodized.registry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import tamaized.tammodized.common.capabilities.CapabilityList;
import tamaized.tammodized.common.capabilities.dimTracker.IDimensionCapability;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PortalHandlerRegistry {

	private static Map<Block, TeleporterWrapper> map = new HashMap<>();

	public static void register(IBlockState state, int dimension, Class<? extends Teleporter> teleporter) {
		register(state.getBlock(), dimension, teleporter);
	}

	public static void register(Block block, int dimension, Class<? extends Teleporter> teleporter) {
		map.put(block, new TeleporterWrapper(dimension, teleporter));
	}

	public static TeleporterWrapper getTeleporter(IBlockState state) {
		return getTeleporter(state.getBlock());
	}

	public static TeleporterWrapper getTeleporter(Block block) {
		return map.get(block);
	}

	public static boolean contains(IBlockState state) {
		return contains(state.getBlock());
	}

	public static boolean contains(Block block) {
		return map.containsKey(block);
	}

	public static void doTeleport(IDimensionCapability cap, EntityPlayerMP player, TeleporterWrapper teleporter) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (player.dimension != teleporter.getDimension() && player.dimension != 1) {
			cap.setLastDimension(player.dimension);
			TeleportLoc port = new TeleportLoc(teleporter.getTeleporter().getConstructor(WorldServer.class).newInstance(player.server.getWorld(teleporter.getDimension())));
			transferPlayerToDimension(player.server, player, teleporter.getDimension(), port);
		} else if (player.dimension == 1) { // From end
			cap.setLastDimension(player.dimension);
			TeleportLoc port = new TeleportLoc(teleporter.getTeleporter().getConstructor(WorldServer.class).newInstance(player.server.getWorld(teleporter.getDimension())));
			transferPlayerToDimension(player.server, player, teleporter.getDimension(), port);
			transferPlayerToDimension(player.server, player, teleporter.getDimension(), port);
		} else {
			TeleportLoc port = new TeleportLoc(teleporter.getTeleporter().getConstructor(WorldServer.class).newInstance(player.server.getWorld(cap.getLastDimension() == teleporter.getDimension() ? 0 : cap.getLastDimension())));
			transferPlayerToDimension(player.server, player, cap.getLastDimension() == teleporter.getDimension() ? 0 : cap.getLastDimension(), port);
			cap.setLastDimension(teleporter.getDimension());
		}
	}

	private static void transferPlayerToDimension(MinecraftServer server, EntityPlayerMP player, int dimId, TeleportLoc teleporter) { // Custom Made to handle teleporting to and from The End (DIM 1)
		int j = player.dimension;
		WorldServer worldserver = server.getWorld(player.dimension);
		player.dimension = dimId;
		WorldServer worldserver1 = server.getWorld(player.dimension);
		player.connection.sendPacket(new SPacketRespawn(player.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), player.interactionManager.getGameType())); // Forge: Use new dimensions information
		server.getPlayerList().updatePermissionLevel(player);
		worldserver.removeEntityDangerously(player);
		player.isDead = false;
		transferEntityToWorld(player, j, worldserver, worldserver1, teleporter);
		server.getPlayerList().preparePlayer(player, worldserver);
		player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
		player.interactionManager.setWorld(worldserver1);
		player.connection.sendPacket(new SPacketPlayerAbilities(player.capabilities));
		server.getPlayerList().updateTimeAndWeatherForPlayer(player, worldserver1);
		server.getPlayerList().syncPlayerInventory(player);
		Iterator iterator = player.getActivePotionEffects().iterator();
		while (iterator.hasNext()) {
			PotionEffect potioneffect = (PotionEffect) iterator.next();
			player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
		}
		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, j, dimId);
	}

	private static void transferEntityToWorld(Entity p_82448_1_, @SuppressWarnings("unused") int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_, TeleportLoc teleporter) { // Custom Made to handle teleporting to and from The End (DIM 1)
		WorldProvider pOld = p_82448_3_.provider;
		WorldProvider pNew = p_82448_4_.provider;
		double moveFactor = pOld.getMovementFactor() / pNew.getMovementFactor();
		double d0 = p_82448_1_.posX * moveFactor;
		double d1 = p_82448_1_.posZ * moveFactor;
		float f = p_82448_1_.rotationYaw;
		p_82448_3_.profiler.startSection("moving");
		p_82448_3_.profiler.endSection();

		p_82448_3_.profiler.startSection("placing");
		d0 = (double) MathHelper.clamp((int) d0, -29999872, 29999872);
		d1 = (double) MathHelper.clamp((int) d1, -29999872, 29999872);

		if (p_82448_1_.isEntityAlive()) {
			p_82448_1_.setLocationAndAngles(d0, p_82448_1_.posY, d1, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
			if (teleporter.teleporter != null)
				teleporter.teleporter.placeInPortal(p_82448_1_, f);
			else
				p_82448_1_.setPositionAndUpdate(teleporter.pos.getX(), teleporter.pos.getY(), teleporter.pos.getZ());
			p_82448_4_.spawnEntity(p_82448_1_);
			p_82448_4_.updateEntityWithOptionalForce(p_82448_1_, false);
		}
		p_82448_3_.profiler.endSection();
		p_82448_1_.setWorld(p_82448_4_);
	}

	@SubscribeEvent
	public void update(PlayerTickEvent e) {
		if (e.phase == Phase.END) {
			IDimensionCapability cap = e.player.getCapability(CapabilityList.DIMENSION, null);
			if (cap != null)
				cap.update(e.player);
		}
	}

	private static class TeleporterWrapper {

		private final int dim;
		private final Class<? extends Teleporter> teleporter;

		public TeleporterWrapper(int dimension, Class<? extends Teleporter> t) {
			dim = dimension;
			teleporter = t;
		}

		public int getDimension() {
			return dim;
		}

		public Class<? extends Teleporter> getTeleporter() {
			return teleporter;
		}

	}

	private static class TeleportLoc {

		public final Teleporter teleporter;
		public final BlockPos pos;

		public TeleportLoc(Teleporter tele) {
			pos = null;
			teleporter = tele;
		}

		@SuppressWarnings("unused")
		public TeleportLoc(BlockPos p) {
			teleporter = null;
			pos = p;
		}

	}

}
