package timemoney;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfig {

	private YamlConfiguration yml;
	private File file;

	CustomConfig(String name) {
		this.file = new File(Main.plugin().getDataFolder(), name + ".yml");
		yml = YamlConfiguration.loadConfiguration(file);
		this.file.getParentFile().mkdirs();
	}

	public FileConfiguration get() {
		return yml;
	}

	public void save() {
		try {
			yml.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		try {
			yml.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean exist(String name) {
		File file = new File(Main.plugin().getDataFolder(), name + ".yml");
		return file.exists();
	}
}