package com.antigrief;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(AntigriefConfig.GROUP)
public interface AntigriefConfig extends Config
{
	String GROUP = "antigrief";

	@ConfigItem(
			position = 1,
			keyName = "hideMessenger",
			name = "Hide King's Messenger",
			description = "Hide King's Messenger from the Underground Pass Quest"
	)
	default boolean hideMessenger()
	{
		return false;
	}

	@ConfigItem(
			position = 2,
			keyName = "hideCannons",
			name = "Hide Cannons",
			description = "Hide Cannons."
	)
	default boolean hideCannons()
	{
		return false;
	}

	@ConfigItem(
			position = 3,
			keyName = "hideSnow",
			name = "Hide Snow",
			description = "Snow can be used for 3-ticking some activities."
	)
	default boolean hideSnow()
	{
		return false;
	}

}
