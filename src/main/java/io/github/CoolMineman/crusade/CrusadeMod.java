package io.github.CoolMineman.crusade;

import java.util.ArrayList;

import io.github.CoolMineman.crusade.trebuchet.BaseTrebuchetBlock;
import io.github.CoolMineman.crusade.trebuchet.TrebuchetBlockEntity;
import io.github.CoolMineman.crusade.trebuchet.TrebuchetItem;
import io.github.CoolMineman.crusade.trebuchet.TrebuchetProjectile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CrusadeMod implements ModInitializer {
	public static final ArmorItem GREAT_HELM = new ArmorItem(new GreatHelmMaterialEpic(), EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.MISC));
	public static final Item WOOD_LANCE = new LanceItem(ToolMaterials.WOOD, 4, 0, new Item.Settings().group(ItemGroup.MISC));
	public static final Item STONE_LANCE = new LanceItem(ToolMaterials.STONE, 4, 0, new Item.Settings().group(ItemGroup.MISC));
	public static final Item IRON_LANCE = new LanceItem(ToolMaterials.IRON, 4, 0, new Item.Settings().group(ItemGroup.MISC));
	public static final Item DIAMOND_LANCE = new LanceItem(ToolMaterials.DIAMOND, 4, 0, new Item.Settings().group(ItemGroup.MISC));
	public static final Item NETHERITE_LANCE = new LanceItem(ToolMaterials.NETHERITE, 4, 0, new Item.Settings().group(ItemGroup.MISC));


	public static ArrayList<Item> LANCES = new ArrayList<>();
	static {
		LANCES.add(WOOD_LANCE);
		LANCES.add(STONE_LANCE);
		LANCES.add(IRON_LANCE);
		LANCES.add(DIAMOND_LANCE);
		LANCES.add(NETHERITE_LANCE);
	}

	public static final Block TREBUCHET_BASE = new BaseTrebuchetBlock(Settings.of(Material.METAL));
	public static BlockEntityType<TrebuchetBlockEntity> TREBUCHET_ENTITY;


	public static final Item TREBUCHET_ITEM = new TrebuchetItem(TREBUCHET_BASE, new Item.Settings().group(ItemGroup.MISC));

	public static final EntityType<TrebuchetProjectile> TREBUCHET_PROJECTILE = Registry.register(
		Registry.ENTITY_TYPE,
		new Identifier("crusade", "trebuchet_projectile"),
		FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TrebuchetProjectile::new).dimensions(EntityDimensions.fixed(1f, 1f)).build()
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new Identifier("crusade", "great_helm"), GREAT_HELM);
		Registry.register(Registry.ITEM, new Identifier("crusade", "wood_lance"), WOOD_LANCE);
		Registry.register(Registry.ITEM, new Identifier("crusade", "stone_lance"), STONE_LANCE);
		Registry.register(Registry.ITEM, new Identifier("crusade", "iron_lance"), IRON_LANCE);
		Registry.register(Registry.ITEM, new Identifier("crusade", "diamond_lance"), DIAMOND_LANCE);
		Registry.register(Registry.ITEM, new Identifier("crusade", "netherite_lance"), NETHERITE_LANCE);

		Registry.register(Registry.ITEM, new Identifier("crusade", "trebuchet"), TREBUCHET_ITEM);

		Registry.register(Registry.BLOCK, new Identifier("crusade", "dontusethisitisinternal1"), TREBUCHET_BASE);
		TREBUCHET_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "crusade:dontusethisitisinternal1", BlockEntityType.Builder.create(TrebuchetBlockEntity::new, TREBUCHET_BASE).build(null));
		FabricDefaultAttributeRegistry.register(TREBUCHET_PROJECTILE, MobEntity.createMobAttributes());
		System.out.println("DEUS VULT!");
	}
}
