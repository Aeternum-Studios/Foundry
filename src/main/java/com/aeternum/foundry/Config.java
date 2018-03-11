package com.aeternum.foundry;

import java.lang.reflect.Field;

import surge.cluster.DataCluster;
import surge.cluster.Key;

public class Config
{
	@Key("feature.stock")
	public static boolean STOCK = true;

	@Key("feature.viewer")
	public static boolean VIEWER = true;

	public static DataCluster defaultCluster = pullConfig();

	public static void pushConfig(DataCluster cc)
	{
		for(Field i : Config.class.getDeclaredFields())
		{
			try
			{
				pullField(cc, i);
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static DataCluster pullConfig()
	{
		try
		{
			DataCluster cc = new DataCluster();

			for(Field i : Config.class.getDeclaredFields())
			{
				putField(cc, i);
			}

			return cc;
		}

		catch(Throwable e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private static void putField(DataCluster cc, Field f) throws IllegalArgumentException, IllegalAccessException
	{
		if(f.isAnnotationPresent(Key.class))
		{
			String key = f.getAnnotation(Key.class).value();
			cc.trySet(key, f.get(null));
		}
	}

	private static void pullField(DataCluster cc, Field f) throws IllegalArgumentException, IllegalAccessException
	{
		if(f.isAnnotationPresent(Key.class))
		{
			String key = f.getAnnotation(Key.class).value();
			f.set(null, cc.get(key));
		}
	}
}
