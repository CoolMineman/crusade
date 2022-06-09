import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.FabricProject;
import io.github.coolcrabs.brachyura.fabric.Yarn;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import net.fabricmc.mappingio.tree.MappingTree;

import static io.github.coolcrabs.brachyura.fabric.FabricProject.ModDependencyFlag.*;

public class Buildscript extends FabricProject {

    @Override
    public VersionMeta createMcVersion() {
        return Minecraft.getVersion("1.18.2");
    }

    @Override
    public MappingTree createMappings() {
        return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn("1.18.2+build.1")).tree;
    }

    @Override
    public FabricLoader getLoader() {
        return new FabricLoader(FabricMaven.URL, FabricMaven.loader("0.13.3"));
    }

    @Override
    public void getModDependencies(ModDependencyCollector d) {
        // Libraries
        String[][] fapiModules = new String[][] {
            {"fabric-resource-loader-v0", "0.4.16+55dca1a4d2"},
            {"fabric-rendering-v1", "1.10.6+54e5b2ecd2"},
            {"fabric-object-builder-api-v1", "2.0.1+d882b915d2"},
            {"fabric-registry-sync-v0", "0.9.5+55dca1a4d2"},
            // Deps of above
            {"fabric-api-base", "0.4.3+d7c144a8d2"},
            {"fabric-mining-level-api-v1", "2.0.2+d1027f7dd2"},
            {"fabric-networking-api-v1", "1.0.20+d882b915d2"},
        };
        for (String[] module : fapiModules) {
            d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", module[0], module[1]), RUNTIME, COMPILE, JIJ);
        }
    }
}
