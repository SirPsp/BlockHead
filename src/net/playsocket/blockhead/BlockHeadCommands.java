package net.playsocket.blockhead;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.chat.Colors;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandListener;

public class BlockHeadCommands implements CommandListener {

	@Command(aliases = { "blockhead" },
			description = "Puts a block from your hand onto your head",
			permissions = { "blockhead.blockhead" },
			toolTip = "/blockhead",
			min = 1)
	public void blockheadCMD(MessageReceiver caller, String[] split) {
		if (caller instanceof Player){
			Player player = (Player)caller;
			if (player.getInventory().getSlot(39) == null){
				if (split.length == 1){
					if (player.getItemHeld() != null && player.getItemHeld().getId() >= 1){
						Item item = player.getItemHeld();
						item.setAmount(item.getAmount() -1);
						Item item2 =item.clone();
						item2.setAmount(1);
						item2.setDamage(item.getDamage());
						item2.setSlot(39);
						player.getInventory().setSlot(item2);
						player.getInventory().update();
						player.message(Colors.TURQUIOSE + "There you go!");
						return ;
					}else{
						player.message("§cYou're not holding anything!");
						return ;
					}
				}else{
					if (split.length > 1 && player.hasPermission("blockhead.spawn")){
					try{
						int id = Integer.parseInt(split[1]);
						if (ItemType.fromId(id) == null){
							player.message("§cInvalid item. Usage - /blockhead [ID] (damage)");
							return;
						}
						//INSERT CODE TO CHECK DISALLOWED ITEMS
//						if (!player.canIgnoreRestrictions() && 
//								(etc.getInstance().getDisallowedItems().contains(id) || 
//								etc.getInstance().getItemSpawnBlacklist().contains(id))){
//							player.sendMessage("§cYou are not allowed to spawn that item.");
//							hook.setCanceled();
//							return hook;
//						}
						int dam = 0;
						if (split.length >= 3){
							dam = Integer.parseInt(split[2]);
						}
						Item item = Canary.factory().getItemFactory().newItem(ItemType.fromId(id));
						item.setDamage(dam);
						item.setSlot(39);
						player.getInventory().setSlot(item);
						player.getInventory().update();
						player.message(Colors.TURQUIOSE + "There you go!");
						return;
					}catch(Throwable t){
						player.message("§cInvalid item. Usage - /blockhead [ID] (damage)");
						return;
					}
				}
				}
			}else{
				player.message("§cYou already have something on your head!");
				return;
			}
		}
	}
	
	@Command(aliases = { "bhead" },
			description = "Puts a block from your hand onto another player's head",
			permissions = { "blockhead.bhead" },
			toolTip = "/bhead <player>",
			min = 1)
	public void bheadCMD(MessageReceiver caller, String[] split) {
		if (caller instanceof Player){
			Player player = (Player)caller;
			if (split.length > 1){
				Player pl = Canary.getServer().matchPlayer(split[1]);
				try{
					if (pl.getInventory().getSlot(39) == null){
						if (split.length == 2){
							if (player.getItemHeld() != null && player.getItemHeld().getId() >= 1){
								Item item = player.getItemHeld();
//								player.getInventory().setSlot(1, 0, item.getSlot());
								item.setAmount(item.getAmount() -1);
//								player.giveItem(item);
								Item item2 = Canary.factory().getItemFactory().newItem(item);
								item2.setAmount(1);
								item2.setSlot(39);
								item2.setAmount(1);
								pl.getInventory().setSlot(item2);
								player.message(Colors.TURQUIOSE + "You put a block on " + pl.getName() + "'s head!");
								pl.message(Colors.TURQUIOSE + player.getName() + " put a block on your head!");
								return;
							}else{
								player.message("§cYou're not holding anything!");
								return;
							}
						}else{
							if (split.length > 2 && player.hasPermission("blockhead.spawn")){
								try{
									int id = Integer.parseInt(split[2]);
									if (ItemType.fromId(id) == null){
										player.message("§cInvalid item. Usage - /blockhead [ID] (damage)");
										return;
									}
									//INSERT CODE TO CHECK DISALLOWED ITEMS
//									if (!player.canIgnoreRestrictions() && 
//											(etc.getInstance().getDisallowedItems().contains(id) || 
//											etc.getInstance().getItemSpawnBlacklist().contains(id))){
//										player.sendMessage("§cYou are not allowed to spawn that item.");
//										hook.setCanceled();
//										return hook;
//									}
									int dam = 0;
									if (split.length >= 4){
										dam = Integer.parseInt(split[3]);
									}
									Item item = Canary.factory().getItemFactory().newItem(ItemType.fromId(id));
									item.setDamage(dam);
									item.setSlot(39);
									pl.getInventory().setSlot(item);
									pl.getInventory().update();
									player.message(Colors.TURQUIOSE + "You put a block on " + pl.getName() + "'s head!");
									pl.message(Colors.TURQUIOSE + player.getName() + " put a block on your head!");
									return;
								}catch(Throwable t){
									player.message("§cInvalid item. Usage - /blockhead [ID] (damage)");
									return;
								}
							}
						}
					}else{
						player.message("§cThat player already has something on their head!");
						return;
					}
				}catch (Throwable t) {
					player.message(Colors.LIGHT_RED + split[1] + " is not online.");
					return;}
			}else{
				player.message("§cUsage - /bhead [player]");
				return;
			}
		}
	}
	
	
	
	
	
	

}