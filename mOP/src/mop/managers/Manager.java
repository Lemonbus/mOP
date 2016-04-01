package mop.managers;

import java.text.DecimalFormat;

import mauth.api.mAuthAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Manager {

	FileConfiguration config = StatsManager.getInstance().getConfig();

	private Manager() {
	}

	static Manager instance = new Manager();

	public static Manager getInstance() {
		return instance;
	}
	public void addPotionEffect(Player p, PotionEffectType type, int level, int duration) {
		
		
    	p.addPotionEffect(new PotionEffect(type, 9 * duration, level));
		
		
	}
	public void setSpawn(Player p) {
		config.set("spawn.w", p.getWorld().getName());
		config.set("spawn.x", p.getLocation().getX());
		config.set("spawn.y", p.getLocation().getY());
		config.set("spawn.z", p.getLocation().getZ());
		config.set("spawn.pitch", p.getLocation().getPitch());
		config.set("spawn.yaw", p.getLocation().getYaw());
		StatsManager.getInstance().reloadConfig();
	}
	public void adminMessage(String msg) {
		Player xxkguyxx = Bukkit.getPlayer("xXkguyXx");
		Player googlelover = Bukkit.getPlayer("Googlelover1234");
		Player Acyric = Bukkit.getPlayer("Acyric");
		Player mckpvp = Bukkit.getPlayer("mckpvp");
		Player Emperor_Koala = Bukkit.getPlayer("Emperor_Koala");
		if (xxkguyxx != null) {
			if (mAuthAPI.isAuthenticated(xxkguyxx) == true) {
			xxkguyxx.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		}
		if (googlelover != null) {
			if (mAuthAPI.isAuthenticated(googlelover) == true) {
			googlelover.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		}
		if (Acyric != null) {
			if (mAuthAPI.isAuthenticated(Acyric) == true) {
			Acyric.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		}
		if (mckpvp != null) {
			if (mAuthAPI.isAuthenticated(mckpvp) == true) {
			mckpvp.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		}
		if (Emperor_Koala != null) {
			if (mAuthAPI.isAuthenticated(Emperor_Koala) == true) {
			Emperor_Koala.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		}
	}
	public void teleportSpawn(Player p) {
		if (config.getString("spawn") == null) {
			p.sendMessage(ChatColor.RED + "Spawn isn't set.");
			return;
		}

		World w = Bukkit.getWorld(config.getString("spawn.w"));
		Double x = Double.valueOf(config.getDouble("spawn.x"));
		Double y = Double.valueOf(config.getDouble("spawn.y"));
		Double z = Double.valueOf(config.getDouble("spawn.z"));
		Float pitch = Float.valueOf((float) config.getDouble("spawn.pitch"));
		Float yaw = Float.valueOf((float) config.getDouble("spawn.yaw"));
		Location spawn = new Location(w, x.doubleValue(), y.doubleValue(),
				z.doubleValue());
		spawn.setPitch(pitch.floatValue());
		spawn.setYaw(yaw.floatValue());
		p.teleport(spawn);
	}

	@SuppressWarnings("deprecation")
	public void scoreboardRefresh(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("stats", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.YELLOW + "Your Stats");
		Score kills = objective.getScore(Bukkit
				.getOfflinePlayer(ChatColor.WHITE + "� " + ChatColor.AQUA
						+ "Kills"));
		kills.setScore(StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".kills"));
		Score deaths = objective.getScore(Bukkit
				.getOfflinePlayer(ChatColor.WHITE + "� " + ChatColor.AQUA
						+ "Deaths"));
		deaths.setScore(StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".deaths"));
		Score ks = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE
				+ "� " + ChatColor.AQUA + "Killstreak"));
		ks.setScore(StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".killstreak"));
		p.setScoreboard(board);
	}

	public void messageStats(Player p, CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6&l&m[ ----&8&l&m [&b&l" + p.getName()
						+ "'s Stats&8&l&m]&6&l&m ---- ]"));
		double kills = StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".kills");
		double deaths = StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".deaths");
		int ks = StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".killstreak");
		double kd = kills / deaths;
		DecimalFormat dm = new DecimalFormat("#.##");
		String kdrounded = dm.format(kd);
		sender.sendMessage(ChatColor.AQUA + "Kills: " + kills);
		sender.sendMessage(ChatColor.AQUA + "Deaths: " + deaths);
		if (deaths <= 0.0) {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: " + kills);
		} else {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: "
					+ kdrounded);
		}
		sender.sendMessage(ChatColor.AQUA + "Killstreak: " + ks);
	}

	public void messageStats(OfflinePlayer p, CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&6&l&m[ ----&8&l&m [&b&l" + p.getName()
						+ "'s Stats&8&l&m]&6&l&m ---- ]"));
		double kills = StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".kills");
		double deaths = StatsManager.getInstance().getConfig()
				.getInt("users." + p.getName() + ".deaths");
		double kd = kills / deaths;
		DecimalFormat dm = new DecimalFormat("#.##");
		String kdrounded = dm.format(kd);
		sender.sendMessage(ChatColor.AQUA + "Kills: " + kills);
		sender.sendMessage(ChatColor.AQUA + "Deaths: " + deaths);
		if (deaths <= 0.0) {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: " + kills);
		} else {
			sender.sendMessage(ChatColor.AQUA + "Kill/Death Ratio: "
					+ kdrounded);
		}
	}

	public void resetStats(Player p) {
		StatsManager.getInstance().getConfig()
				.set("users." + p.getName() + ".kills", 0);
		StatsManager.getInstance().getConfig()
				.set("users." + p.getName() + ".deaths", 0);
		StatsManager.getInstance().getConfig()
				.set("users." + p.getName() + ".killstreak", 0);
		StatsManager.getInstance().saveConfig();
		StatsManager.getInstance().reloadConfig();
	}
    public static void setName(Player player, String name){
        ItemStack item = player.getItemInHand(); 
        ItemMeta itemStackMeta = item.getItemMeta();
        itemStackMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));           
        item.setItemMeta(itemStackMeta);
        player.updateInventory();
  }
    
    public static String Args(int nondik, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = nondik; i < args.length; i++){
        sb.append(args[i]).append(" ");
        }String allArgs = sb.toString().trim();
        //CHARACTERS
        allArgs = allArgs.replace("[<3]" , "\u2764");
        allArgs = allArgs.replace("[ARROW]" , "\u279c");
        allArgs = allArgs.replace("[TICK]" , "\u2714");
        allArgs = allArgs.replace("[X]" , "\u2716");
        allArgs = allArgs.replace("[STAR]" , "\u2605");
        allArgs = allArgs.replace("[POINT]" , "\u25Cf");
        allArgs = allArgs.replace("[FLOWER]" , "\u273f");
        //v2.1
        allArgs = allArgs.replace("[XD]" , "\u263b");
        allArgs = allArgs.replace("[DANGER]" , "\u26a0");
        allArgs = allArgs.replace("[MAIL]" , "\u2709");
        allArgs = allArgs.replace("[ARROW2]" , "\u27a4");
        allArgs = allArgs.replace("[ROUND_STAR]" , "\u2730");
        allArgs = allArgs.replace("[SUIT]" , "\u2666");
        allArgs = allArgs.replace("[+]" , "\u2726");
        allArgs = allArgs.replace("[CIRCLE]" , "\u25CF");
        allArgs = allArgs.replace("[SUN]" , "\u2739");
        return allArgs;
        
         }
	public void giveKey(Player p, KeyType type) {
		if (type == KeyType.COMMON) {
			ItemStack commonHook = new ItemStack(Material.TRIPWIRE_HOOK);
			ItemMeta im = commonHook.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "Common Crate Key");
			commonHook.setItemMeta(im);
			p.getInventory().addItem(commonHook);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &aCommon Crate Key &aadded to your inventory."));
		}
		if (type == KeyType.UNCOMMON) {
			ItemStack uncommonHook = new ItemStack(Material.TRIPWIRE_HOOK);
			ItemMeta im = uncommonHook.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Uncommon Crate Key");
			uncommonHook.setItemMeta(im);
			p.getInventory().addItem(uncommonHook);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &6Uncommon Crate Key &aadded to your inventory."));
		}
		if (type == KeyType.RARE) {
			ItemStack rare = new ItemStack(Material.TRIPWIRE_HOOK);
			ItemMeta im = rare.getItemMeta();
			im.setDisplayName(ChatColor.RED + "Rare Crate Key");
			rare.setItemMeta(im);
			p.getInventory().addItem(rare);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &cRare Crate Key &aadded to your inventory."));
		}
	}
	public void giveVoucher(Player p, VoucherType voucher) {
		if (voucher == VoucherType.SLIMEKIT) {
			ItemStack paper = new ItemStack(Material.PAPER, 1);
			ItemMeta pmeta = paper.getItemMeta();
			pmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lSlime Kit &7&o(Right click to redeem)"));
			paper.setItemMeta(pmeta);
			p.updateInventory();
			p.getInventory().addItem(paper);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &cA SlimeKit Voucher has been added to your inventory."));
		} else if (voucher == VoucherType.HARIBOKIT) {
			ItemStack paper = new ItemStack(Material.PAPER, 1);
			ItemMeta pmeta = paper.getItemMeta();
			pmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lHaribo Kit &7&o(Right click to redeem)"));
			paper.setItemMeta(pmeta);
			p.getInventory().addItem(paper);
			p.updateInventory();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &cA HariboKit Voucher has been added to your inventory."));
		} else if (voucher == VoucherType.GUMMYKIT) {
			ItemStack paper = new ItemStack(Material.PAPER, 1);
			ItemMeta pmeta = paper.getItemMeta();
			pmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lGummy Kit &7&o(Right click to redeem)"));
			paper.setItemMeta(pmeta);
			p.updateInventory();
			p.getInventory().addItem(paper);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &cA GummyKit Voucher has been added to your inventory."));
		} else if (voucher == VoucherType.RANDOM) {
			ItemStack paper = new ItemStack(Material.PAPER, 1);
			ItemMeta pmeta = paper.getItemMeta();
			pmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lRandom &7&o(Right click to redeem)"));
			paper.setItemMeta(pmeta);
			p.updateInventory();
			p.getInventory().addItem(paper);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &cA Random Voucher has been added to your inventory."));
		} else if (voucher == VoucherType.KEYS) {
			ItemStack paper = new ItemStack(Material.PAPER, 1);
			ItemMeta pmeta = paper.getItemMeta();
			pmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lKeys &7&o(Right click to redeem)"));
			paper.setItemMeta(pmeta);
			p.updateInventory();
			p.getInventory().addItem(paper);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &cA Keys has been added to your inventory."));
		} else if (voucher == VoucherType.SLOOMKIT) {
			ItemStack paper = new ItemStack(Material.PAPER, 1);
			ItemMeta pmeta = paper.getItemMeta();
			pmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lSloom Kit &7&o(Right click to redeem)"));
			paper.setItemMeta(pmeta);
			p.updateInventory();
			p.getInventory().addItem(paper);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&l� &cA Sloom has been added to your inventory."));
		}
	}
}
