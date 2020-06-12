package io.github.CoolMineman.crusade;

import java.util.ArrayList;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CrusadeMod implements ModInitializer {
	public static final ArmorItem GREAT_HELM = new ArmorItem(new GreatHelmMaterialEpic(), EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.MISC));
	public static final Item DIAMOND_LANCE = new Item(new Item.Settings().group(ItemGroup.MISC));

	public static ArrayList<Item> LANCES = new ArrayList<>();
	static {
		LANCES.add(DIAMOND_LANCE);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new Identifier("crusade", "great_helm"), GREAT_HELM);
		Registry.register(Registry.ITEM, new Identifier("crusade", "diamond_lance"), DIAMOND_LANCE);
		System.out.println("Hello Fabric world!");
	}
}
