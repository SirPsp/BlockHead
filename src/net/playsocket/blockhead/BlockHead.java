package net.playsocket.blockhead;

import net.canarymod.Canary;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.plugin.Plugin;

public class BlockHead extends Plugin {

	@Override
	public void disable() {
		getLogman().logInfo(getName() + " v" + getVersion() + " disabled");
	}

	@Override
	public boolean enable() {
        try {
			Canary.commands().registerCommands(new BlockHeadCommands(), this, true);
		} catch (CommandDependencyException e) {
			e.printStackTrace();
		}
        getLogman().logInfo(getName() + " v"+ getVersion() + " by " + getAuthor() + " enabled");
        return true;
	}

}
