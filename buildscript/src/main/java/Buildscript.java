import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.SimpleFabricProject;
import io.github.coolcrabs.brachyura.fabric.Yarn;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import net.fabricmc.mappingio.tree.MappingTree;

import static io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag.*;

public class Buildscript extends SimpleFabricProject {

    @Override
    public VersionMeta createMcVersion() {
        return Minecraft.getVersion("1.19");
    }

    @Override
    public int getJavaVersion() {
        return 17;
    }

    @Override
    public MappingTree createMappings() {
        return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn("1.19+build.1")).tree;
    }

    @Override
    public FabricLoader getLoader() {
        return new FabricLoader(FabricMaven.URL, FabricMaven.loader("0.14.7"));
    }

    @Override
    public void getModDependencies(ModDependencyCollector d) {
        // Libraries
        String[][] fapiModules = new String[][] {
            {"fabric-resource-loader-v0", "0.5.2+9e7660c6cd"},
            {"fabric-rendering-v1", "1.10.13+9ff28f40cd"},
            {"fabric-object-builder-api-v1", "4.0.4+9ff28f40cd"},
            {"fabric-registry-sync-v0", "0.9.14+92cf9a3ecd"},
            // Deps of above
            {"fabric-api-base", "0.4.8+e62f51a3cd"},
            {"fabric-mining-level-api-v1", "2.1.6+9ff28f40cd"},
            {"fabric-networking-api-v1", "1.0.25+9ff28f40cd"},
        };
        for (String[] module : fapiModules) {
            d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", module[0], module[1]), RUNTIME, COMPILE);
        }
    }
}
