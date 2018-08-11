package timemoney;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	private static Main instance;
	private static Configs configs;
	private static Permission permission = null;
	private static Economy economy = null;

	public static Main plugin() {
		return instance;
	}

	public static Configs getConfigs() {
		return configs;
	}

	public static Permission getPermission() {
		return permission;
	}

	public static Economy getEconomy() {
		return economy;
	}

	@Override
	public void onEnable() {
		instance = this;
		configs = new Configs();
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		setupPermissions();
		setupEconomy();
		for (String group : getPermission().getGroups()) {
			CustomConfig cfg = getConfigs().get("config");
			if (!cfg.get().contains("tickmoney")) {
				cfg.get().set("tickmoney", 20 * 300);
			}
			if (!cfg.get().contains(group)) {
				cfg.get().set(group, 5000);
			}
			if (!cfg.get().contains("message")) {
				cfg.get().set("message", "Бонус за игровое время %money%");
			}
			configs.save("config");
		}
	}

	@Override
	public void onDisable() {
		configs.saveAll();
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> cp = getServer().getServicesManager().getRegistration(Permission.class);
		if (cp != null) {
			permission = (Permission) cp.getProvider();
		}
		return permission != null;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
}