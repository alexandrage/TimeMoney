package timemoney;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
import tick.events.TickPlayerEvent;

public class EventListener implements Listener {

	@EventHandler
	public void onTick(TickPlayerEvent e) {
		Player p = e.getPlayer();
		CustomConfig cfgp = Main.getConfigs().get("players/"+p.getName());
		long last = cfgp.get().getLong("time");
		cfgp.get().set("time", last+1);
		CustomConfig cfg = Main.getConfigs().get("config");
		long total = cfg.get().getLong("tickmoney");
		if(last>total) {
			long money = cfg.get().getLong(cfgp.get().getString("group"));
			cfgp.get().set("time", 0);
			String group = Main.getPermission().getPrimaryGroup(p);
			cfgp.get().set("group", group);
			Main.getEconomy().depositPlayer(e.getPlayer(), money);
			String message = ChatColor.translateAlternateColorCodes('&', cfg.get().getString("message").replace("%money%", money+""));
			p.sendMessage(message);
		}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		CustomConfig cfgp = Main.getConfigs().get("players/"+p.getName());
		String group = Main.getPermission().getPrimaryGroup(p);
		cfgp.get().set("group", group);
	}
	
	@EventHandler
	public void onLogin(PlayerQuitEvent e) {
		Main.getConfigs().save("players/"+e.getPlayer().getName());
	}
}