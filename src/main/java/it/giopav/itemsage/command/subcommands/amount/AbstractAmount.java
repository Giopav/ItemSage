package it.giopav.itemsage.command.subcommands.amount;

import it.giopav.itemsage.command.superclasses.AbstractCommand;

public abstract class AbstractAmount extends AbstractCommand {
    protected AbstractAmount() {
        super(argsArray[0]);
    }
}
