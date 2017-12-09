package iceandshadow2;

import iceandshadow2.nyx.world.NyxTeleporter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class IaSServerCommand implements ICommand {

	private static void send(ICommandSender snd, String msg) {
		snd.addChatMessage(new ChatComponentText(msg));
	}

	private final List aliases;

	public IaSServerCommand() {
		aliases = new ArrayList();
		aliases.add("ias");
		aliases.add("iceandshadow");
	}

	@Override
	public List addTabCompletionOptions(ICommandSender snd, String[] str) {
		final ArrayList<String> tc = new ArrayList<String>();
		if (str.length == 1) {
			tc.add("goto");
			return tc;
		}
		if (str.length == 2) {
			if (str[0].contentEquals("goto")) {
				tc.add("nyx");
				tc.add("overworld");
			}
			return tc;
		}
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender snd) {
		if (snd instanceof EntityPlayerMP)
			return snd.canCommandSenderUseCommand(3, "ban");
		else if (snd instanceof MinecraftServer)
			return true;
		return false;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public String getCommandName() {
		return "iceandshadow";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "ias <operation> [arguments]\noperations:\n\tgoto <overworld|nyx>: Teleports the user to a specified dimension.";
	}

	@Override
	public boolean isUsernameIndex(String[] list, int indx) {
		return false;
	}

	@Override
	public void processCommand(ICommandSender snder, String[] args) {
		if (args.length == 0) {
			IaSServerCommand.send(snder, "Insufficient arguments");
			return;
		}

		if (args[0].contentEquals("goto")) {
			if (args.length >= 2) {
				EntityPlayerMP plai = null;
				if (args.length == 2) {
					if (snder instanceof EntityPlayerMP)
						plai = (EntityPlayerMP) snder;
					else {
						IaSServerCommand.send(snder, "This command can only be used by an opped player.");
						return;
					}
				} else {
					IaSServerCommand.send(snder, "Excessive arguments, expected 'goto <overworld|nyx>'");
					return;
				}
				/*
				 * if(plai == null) {
				 * send(snder,"Could not find the player to teleport."); return;
				 * }
				 */
				int dim;
				if (args[1].contentEquals("overworld"))
					dim = 0;
				else if (args[1].contentEquals("nyx"))
					dim = IaSFlags.dim_nyx_id;
				else {
					IaSServerCommand.send(snder, "Invalid arguments, expected 'goto <overworld|nyx>'");
					return;
				}
				if (dim == plai.dimension) {
					IaSServerCommand.send(snder, "You're already in that dimension!");
					return;
				}
				plai.mcServer.getConfigurationManager().transferPlayerToDimension(plai, dim,
						new NyxTeleporter(plai.mcServer.worldServerForDimension(dim)));
			} else
				IaSServerCommand.send(snder, "Insufficient arguments, expected 'goto <overworld|nyx>'");
		} else
			IaSServerCommand.send(snder, "Unknown operation.");
	}

}
