package iceandshadow2;

import iceandshadow2.nyx.world.NyxTeleporter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.ChatComponentText;

public class IaSServerCommand implements ICommand {

	private List aliases;
	public IaSServerCommand()
	{
		this.aliases = new ArrayList();
		this.aliases.add("ias");
		this.aliases.add("iceandshadow");
	}

	@Override
	public int compareTo(Object o) {
		return 0;
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
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender snder, String[] args) {
		if(args.length == 0)
		{
			send(snder,"Insufficient arguments");
			return;
		}

		if(snder instanceof EntityPlayerMP) {
			EntityPlayerMP plai = (EntityPlayerMP)snder;
			if(args[0].contentEquals("goto")) {
				if(args.length >= 2) {
					if(args.length == 3)
						plai = (EntityPlayerMP)(plai.getServerForPlayer().getPlayerEntityByName(args[2]));
					int dim;
					if(args[1].contentEquals("overworld"))
						dim = 0;
					else if(args[1].contentEquals("nyx"))
						dim = IaSFlags.dim_nyx_id;
					else {
						send(snder,"Invalid arguments, expected 'goto <overworld|nyx>'");
						return;
					}
					if(dim == plai.dimension) {
						send(snder,"You're already in that dimension!");
						return;
					}
					plai.mcServer
					.getConfigurationManager()
					.transferPlayerToDimension(
							plai, dim,
							new NyxTeleporter(
									plai.mcServer
									.worldServerForDimension(dim)));
				} else {
					send(snder,"Insufficient arguments, expected 'goto <overworld|nyx>'");
				}
			}
		} else {
			send(snder,"Unknown operation.");
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender snd) {
		if(snd instanceof EntityPlayerMP) {
			EntityPlayerMP ep = (EntityPlayerMP)snd;
			if (ep.mcServer.getConfigurationManager().func_152596_g(ep.getGameProfile()))
            {
                UserListOpsEntry userlistopsentry = (UserListOpsEntry)ep.mcServer.getConfigurationManager().func_152603_m().func_152683_b(ep.getGameProfile());
                return userlistopsentry != null ? false : ep.mcServer.getOpPermissionLevel() >= 3;
            }
            else
            {
                return false;
            }
		}
		else
			return false;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender snd,
			String[] str) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] list, int indx) {
		return false;
	}

	private static void send(ICommandSender snd, String msg) {
		snd.addChatMessage(new ChatComponentText(msg));
	}

}
