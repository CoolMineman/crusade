package io.github.CoolMineman.crusade;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CrusadeMod implements ModInitializer {
	public static final ArmorItem GREAT_HELM = new ArmorItem(new GreatHelmMaterialEpic(), EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.MISC));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new Identifier("crusade", "great_helm"), GREAT_HELM);
		System.out.println("Hello Fabric world!");
	}
}
