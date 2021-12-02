import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.FabricProject;
import io.github.coolcrabs.brachyura.fabric.Yarn;
import io.github.coolcrabs.brachyura.maven.MavenId;
import net.fabricmc.mappingio.tree.MappingTree;

import static io.github.coolcrabs.brachyura.fabric.FabricProject.ModDependencyFlag.*;

import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;

public class Buildscript extends FabricProject {

    @Override
    public String getMcVersion() {
        return "1.18";
    }

    @Override
    public MappingTree createMappings() {
        return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn("1.18+build.1")).tree;
    }

    @Override
    public FabricLoader getLoader() {
        return new FabricLoader(FabricMaven.URL, FabricMaven.loader("0.12.8"));
    }

    @Override
    public void getModDependencies(ModDependencyCollector d) {
        // Libraries
        String[][] fapiModules = new String[][] {
            {"fabric-resource-loader-v0", "0.4.11+3ac43d9514"},
            {"fabric-rendering-v1", "1.10.3+6b21378a14"},
            {"fabric-object-builder-api-v1", "1.10.13+3ac43d9514"},
            {"fabric-registry-sync-v0", "0.8.5+3ac43d9514"},
            // Deps of above
            {"fabric-api-base", "0.4.1+b4f4f6cd14"},
            {"fabric-tool-attribute-api-v1", "1.3.4+7de09f5514"},
            {"fabric-mining-level-api-v1", "1.0.3+3ac43d9514"},
            {"fabric-tag-extensions-v0", "1.2.5+3ac43d9514"},
            {"fabric-networking-api-v1", "1.0.18+3ac43d9514"}
        };
        for (String[] module : fapiModules) {
            d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", module[0], module[1]), RUNTIME, COMPILE, JIJ);
        }
    }

    @Override
    public BrachyuraDecompiler decompiler() {
        return null;
    }
}
