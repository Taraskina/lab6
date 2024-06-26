package org.example.commands;

import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.Response;
import org.example.utilites.interfaces.Callable;

public class Exit extends Command implements Callable {

    public Exit() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public final String name = "exit";

    public static Exit staticFactory(String[] args, String value) {
        Exit inst = new Exit();
        inst.setValue(value);
        inst.setArgs(args);
        return inst;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        resp.setFlag(false);
        new Save().calling(args, v);
        return resp;

    }
}
