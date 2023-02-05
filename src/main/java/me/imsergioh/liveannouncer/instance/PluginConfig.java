package me.imsergioh.liveannouncer.instance;

import me.imsergioh.liveannouncer.LiveAnnouncer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PluginConfig {

    private final File file;

    private Configuration configuration;

    public PluginConfig(String name) {
        this.file = new File(LiveAnnouncer.getPlugin().getDataFolder(), name);
        setupFile();
        loadConfig();
    }

    public void save() {
        try {
            YamlConfiguration.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Configuration getSection(String path) {
        return configuration.getSection(path);
    }

    public String getString(String path) {
        return configuration.getString(path);
    }

    public List<String> getStringList(String path) {
        return configuration.getStringList(path);
    }

    public void regDefault(String path, Object value) {
        if (!configuration.contains(path)) {
            configuration.set(path, value);
        }
    }

    public void set(String path, Object value) {
        configuration.set(path, value);
    }

    private void loadConfig() {
        try {
            configuration = YamlConfiguration.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupFile() {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (Exception ignored) {
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
