package com.antigrief;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.callback.Hooks;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Anti Grief"
)
public class AntigriefPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private AntigriefConfig config;

	@Inject
	private Hooks hooks;

	private boolean hideMessenger;
	private boolean hideCannons;
	private boolean hideSnow;

	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;

	@Override
	protected void startUp() throws Exception
	{
		updateConfig();
		hooks.registerRenderableDrawListener(drawListener);
		log.info("Anti Grief started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		hooks.unregisterRenderableDrawListener(drawListener);
		log.info("Anti Grief stopped!");
	}

	private void updateConfig()
	{
		hideMessenger = config.hideMessenger();
		hideCannons = config.hideCannons();
		hideSnow = config.hideSnow();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e)
	{
		if (e.getGroup().equals(AntigriefConfig.GROUP))
		{
			updateConfig();
		}
	}

	@Provides
	AntigriefConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AntigriefConfig.class);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		switch(event.getGameObject().getId()) {
			case ObjectID.SNOW:
			case ObjectID.SNOW_15616:
			case ObjectID.SNOW_15617:
			case ObjectID.SNOW_26703:
			case ObjectID.SNOW_26704:
				if (hideSnow) {
					client.getScene().removeGameObject(event.getGameObject());
				}
			case ObjectID.BROKEN_MULTICANNON:
			case ObjectID.BROKEN_MULTICANNON_14916:
			case ObjectID.BROKEN_MULTICANNON_43028:
			case ObjectID.DWARF_MULTICANNON:
			case ObjectID.DWARF_MULTICANNON_11868:
			case ObjectID.DWARF_MULTICANNON_35885:
			case ObjectID.DWARF_MULTICANNON_35886:
			case ObjectID.DWARF_MULTICANNON_43027:
			case ObjectID.DWARF_MULTICANNON_5975:
			case ObjectID.DWARF_MULTICANNON_5976:
			case ObjectID.CANNON_BASE:
			case ObjectID.CANNON_BASE_43029:
			case ObjectID.CANNON_STAND:
			case ObjectID.CANNON_STAND_43030:
			case ObjectID.CANNON_BARRELS:
			case ObjectID.CANNON_BARRELS_43031:
				if (hideCannons) {
					client.getScene().removeGameObject(event.getGameObject());
				}
		}
	}

	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean drawingUI)
	{
		if (renderable instanceof NPC)
		{
			NPC npc = (NPC) renderable;

			if (npc.getId() == NpcID.KINGS_MESSENGER) {
				return !hideMessenger;
			}

		}

		return true;
	}

}
