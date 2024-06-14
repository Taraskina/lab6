package org.example.commands;

import org.example.entity.Person;
import org.example.main.Command;
import org.example.main.CommandType;
import org.example.main.Response;
import org.example.utilites.interfaces.Callable;

import static org.example.main.App.collectionManager;

public class SumOfWeight extends Command implements Callable {

    public SumOfWeight() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }
    public final String name = "sum_of_weight";
    public static SumOfWeight staticFactory(String[] args,String value){
        SumOfWeight inst =  new SumOfWeight();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        int i = collectionManager.getCollectionStream().mapToInt(Person::getWeight).sum();
        resp.addMessage(Integer.toString(i));
        return resp;
    }
}
