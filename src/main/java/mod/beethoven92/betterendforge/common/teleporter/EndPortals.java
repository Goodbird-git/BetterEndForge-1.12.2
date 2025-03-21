package mod.beethoven92.betterendforge.common.teleporter;

import java.io.File;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.JsonFactory;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigWriter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;


public class EndPortals 
{
	private static PortalInfo[] portals = null;
	
	public static void loadPortals() 
	{
		File file = new File(JsonConfigWriter.MOD_CONFIG_DIR, "portals.json");
		JsonObject json;
		if (!file.exists()) 
		{
			file.getParentFile().mkdirs();
			json = makeDefault(file);
		}
		else 
		{
			json = JsonFactory.getJsonObject(file);
		}
		if (!json.has("portals") || !json.get("portals").isJsonArray()) 
		{
			json = makeDefault(file);
		}
		JsonArray array = json.get("portals").getAsJsonArray();
		if (array.size() == 0) 
		{
			json = makeDefault(file);
			array = json.get("portals").getAsJsonArray();
		}
		portals = new PortalInfo[array.size()];
		for (int i = 0; i < portals.length; i++) 
		{
			portals[i] = new PortalInfo(array.get(i).getAsJsonObject());
		}
	}
	
	public static int getCount() 
	{
		if(portals==null){
			return 1;
		}
		return ModMathHelper.max(portals.length - 1, 1);
	}
	
	public static WorldServer getWorld(MinecraftServer server, int state)
	{
		if (state >= portals.length) 
		{
			return server.getWorld(0); // Get overworld
		}
		return portals[state].getWorld(server);
	}
	
	public static int getPortalState(ResourceLocation item) 
	{
		for (int i = 0; i < portals.length; i++) 
		{
			if (portals[i].item.equals(item)) 
			{
				return i;
			}
		}
		return 0;
	}
	
	public static int getColor(int state) 
	{
		return portals[state].color;
	}
	
	public static boolean isAvailableItem(ResourceLocation item) 
	{
		for (int i = 0; i < portals.length; i++) 
		{
			if (portals[i].item.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	private static JsonObject makeDefault(File file) 
	{
		JsonObject jsonObject = new JsonObject();
		JsonFactory.storeJson(file, jsonObject);
		JsonArray array = new JsonArray();
		jsonObject.add("portals", array);
		array.add(makeDefault().toJson());
		JsonFactory.storeJson(file, jsonObject);
		return jsonObject;
	}
	
	private static PortalInfo makeDefault() 
	{
		return new PortalInfo(0, new ResourceLocation(BetterEnd.MOD_ID, "eternal_crystal"), 255, 255, 255);
	}
	
	private static class PortalInfo 
	{
		private final int dimension;
		private final ResourceLocation item;
		private final int color;
		private WorldServer world;
		
		PortalInfo(JsonObject obj) 
		{
			this(
				0,
				new ResourceLocation(JsonFactory.getString(obj, "item", "betterendforge:eternal_crystal")),
				JsonFactory.getInt(obj, "colorRed", 255),
				JsonFactory.getInt(obj, "colorGreen", 255),
				JsonFactory.getInt(obj, "colorBlue", 255)
			);
		}
		
		PortalInfo(int dimension, ResourceLocation item, int r, int g, int b)
		{
			this.dimension = dimension;
			this.item = item;
			this.color = ModMathHelper.color(r, g, b);
		}

		WorldServer getWorld(MinecraftServer server)
		{
			if (world != null) 
			{
				return world;
			}
			for(WorldServer world : server.worlds)
			{
				if (world.provider.getDimension()==dimension)
				{
					this.world = world;
					return world;
				}
			}
			return server.getWorld(0); // Get Overworld
		}
		
		JsonObject toJson() 
		{
			JsonObject obj = new JsonObject();
			obj.addProperty("dimension", dimension);
			obj.addProperty("item", item.toString());
			obj.addProperty("colorRed", (color >> 16) & 255);
			obj.addProperty("colorGreen", (color >> 8) & 255);
			obj.addProperty("colorBlue", color & 255);
			return obj;
		}
	}
}
