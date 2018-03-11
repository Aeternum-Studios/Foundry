package com.aeternum.foundry;

import java.io.File;
import java.io.IOException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import surge.cluster.DataCluster;
import surge.cluster.YamlDataInput;
import surge.cluster.YamlDataOutput;

public class Foundry extends JavaPlugin implements Listener
{
	public static DataCluster cc = new DataCluster();

	@Override
	public void onDisable()
	{
		// hi there
	}

	@Override
	public void onEnable()
	{
		try
		{
			loadConfiguration();
			System.out.println("Foundry Loaded");
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void saveConfiguration() throws IOException
	{
		File f = new File(getDataFolder(), "config.yml");
		f.getParentFile().mkdirs();

		if(!f.exists())
		{
			f.createNewFile();
		}

		new YamlDataOutput().write(Config.pullConfig(), f);
	}

	public void loadConfiguration() throws IOException
	{
		File f = new File(getDataFolder(), "config.yml");
		f.getParentFile().mkdirs();

		if(!f.exists())
		{
			saveConfiguration();
		}

		DataCluster cc = new YamlDataInput().read(f);
		Config.pushConfig(cc);
		saveConfiguration();
	}
}
