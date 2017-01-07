package Tamaized.TamModized.registry;

import java.util.ArrayList;

import Tamaized.TamModized.TamModized;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public class TamModelResourceHandler {

	private ArrayList<TamModelResource> models;

	public static final TamModelResourceHandler instance = new TamModelResourceHandler();

	private TamModelResourceHandler() {
		models = new ArrayList<TamModelResource>();
	}

	public void register(TamModelResource obj) {
		models.add(obj);
	}

	public void compile() {
		TamModized.instance.logger.info("Registering Models");
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		for (TamModelResource resource : models) {
			renderItem.getItemModelMesher().register(resource.model.getAsItem(), 0, new TamModelResourceLocation(resource.modid, resource.model.getModelDir() + "/", resource.model.getName(), "inventory"));
		}
		models.clear();
	}

	public class TamModelResource {

		private final ITamModel model;
		private final String modid;

		public TamModelResource(ITamModel m, String id) {
			model = m;
			modid = id;
		}

	}

	private class TamModelResourceLocation extends ModelResourceLocation {

		public TamModelResourceLocation(String id, String path, String file, String v) {
			super(0, new String[] { id, path.concat(file), v });
		}

	}

}
