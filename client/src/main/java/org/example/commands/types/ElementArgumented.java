package org.example.commands.types;


import org.example.commands.ElementArgumentable;
import org.example.utilites.Context;

import static org.example.utilites.CheckingReader.readSomeArgs;

public class ElementArgumented extends ElementAndValueArgumented implements ElementArgumentable {
    public ElementArgumented(String[] args){
        super(null,args);
    }
    public ElementArgumented(Context ctx){
        super(null,readSomeArgs(9,"s,l,l,d,i,s,l,l,f".split(","),ctx.getSc(),(
                        "Введите имя;" +
                                "Введите целочисленную x-координату;" +
                                "Введите целочисленную y-координату (y>-867);" +
                                "Введите рост в дробных значениях (>0);" +
                                "Введите вес (целочисленный);" +
                                """
                                                Введите один из цвета волос:
                                                    RED,
                                                    GREEN,
                                                    BLUE,
                                                    YELLOW;"""+
                                "Введите целочисленную x-координату;" +
                                "Введите целочисленную y-координату;" +
                                "Введите дробно-десятичную z-координату;").split(";"),
                "more length 0;more than -867;more than -867;more than 0;more than 0;;;is color;more than -867;more than -867;more than -867".split(";")));
    }
    public ElementAndValueArgumented elFactory(String v, String[] args){
        return  new ElementArgumented(args);
    }

    @Override
    public ElementArgumented elFactory(String[] args) {
        return null;
    }
}
