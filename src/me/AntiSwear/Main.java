package me.AntiSwear;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public List<String> falsewords;
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
	    this.falsewords = getConfig().getStringList("badwords");
	}
	
	@EventHandler
	public void AntiCurse(AsyncPlayerChatEvent e) 
	{
		Player p = e.getPlayer();
		List<String> msg = Arrays.asList(e.getMessage().toLowerCase().split(" "));
		for (String cens : this.falsewords) {
			if (msg.contains(cens.toLowerCase()))
			{
				e.setCancelled(true);
				p.getPlayer().chat(getConfig().getString("Message").replaceAll("&", "§"));
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if ((cmd.getName().equalsIgnoreCase("AntiSwear"))) {
			if (!(p.hasPermission("antiswear.admin"))) {
				p.sendMessage("§cSorry, You don't Have Permissions.");
			} else {
			if (args.length == 0) {
				p.sendMessage("§cAntiSwear");
				p.sendMessage("");
				p.sendMessage("§cAuthor: §rFeatherDev");
				p.sendMessage("§cVersion: §r1.0");
				p.sendMessage("");
				p.sendMessage("§cUse: §r/AntiSwear help");
				p.sendMessage("");
				p.sendMessage("§cAntiSwear");
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
				p.sendMessage("§cAntiSwear");
				p.sendMessage("");
				p.sendMessage("§r/AntiSwear addword");
				p.sendMessage("§r/AntiSwear removeword");
				p.sendMessage("§r/AntiSwear list");
				p.sendMessage("§r/AntiSwear reload");
				p.sendMessage("");
				p.sendMessage("§cAntiSwear");
				return true;
			}
			if (args[0].equalsIgnoreCase("addword")) {
				if (!(args.length == 2)) {
					p.sendMessage("§cUse: §r/as addword <word>");
					return false;
				}
				if (this.falsewords.contains(args[1].toLowerCase())) {
					p.sendMessage("§cIs Already in BlackList!");
				}
				if (!this.falsewords.contains(args[1].toLowerCase()))
				{
					this.falsewords.add(args[1].toLowerCase());
					getConfig().set("badwords", this.falsewords);
					p.sendMessage("§aWord: §f" + args[1] + " §aAdded to The blacklist!");
					saveConfig();
				}
			}
			if (args[0].equalsIgnoreCase("removeword")) {
				if (!(args.length == 2)) {
					p.sendMessage("§cUse: §r/as removeword <word>");
					return false;
				}
				if (!this.falsewords.contains(args[1].toLowerCase())) {
					p.sendMessage("§cIs Not in BlackList!");
				}
				if (this.falsewords.contains(args[1].toLowerCase()))
				{
					this.falsewords.remove(args[1].toLowerCase());
					getConfig().set("badwords", this.falsewords);
					p.sendMessage("§aWord: §f" + args[1] + " §aRemoved from The blacklist!");
					saveConfig();
				}
			}
			if (args[0].equalsIgnoreCase("list")) {
				sender.sendMessage("§aBadWords: §7" + getConfig().getStringList("badwords").toString());
			}
			if (args[0].equalsIgnoreCase("reload")) {
				sender.sendMessage("§aConfig Has Been Reloaded!");
				reloadConfig();
			}
			}
		}
		return false;
	}
}
