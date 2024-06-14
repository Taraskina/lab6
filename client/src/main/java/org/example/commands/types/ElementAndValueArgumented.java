package org.example.commands.types;

import org.example.commands.ElementAndValueArgumentable;
import org.example.commands.utility.Command;
import org.example.utilites.Context;

import static org.example.utilites.CheckingReader.readSomeArgs;


public class ElementAndValueArgumented extends Command implements ElementAndValueArgumentable {
    public String value;

    public ElementAndValueArgumented(String v, String[] args) {
        super(v, args);
    }

    public ElementAndValueArgumented(Context ctx, String v) {
        super(v, readSomeArgs(9, "s,l,l,d,i,s,l,l,f".split(","), ctx.getSc(), (
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
                                            YELLOW;""" +
                                "Введите целочисленную x-координату;" +
                                "Введите целочисленную y-координату;" +
                                "Введите дробно-десятичную z-координату;").split(";"),
                "more length 0;more than -867;more than -867;more than 0;more than 0;;;is color;more than -867;more than -867;more than -867".split(";")));
    }


    @Override
    public ElementAndValueArgumented elFactory(String v, String[] args) {
        return new ElementAndValueArgumented(v, args);
    }

    public static ElementAndValueArgumented newInstance(String v, String[] args) {
        return new ElementAndValueArgumented(v, args).elFactory(v, args);
    }

    public static ElementAndValueArgumented newInstance(Context ctx, String v) {
        return new ElementAndValueArgumented(ctx, v)
                .elFactory(v,
                        readSomeArgs(9, "s,l,l,d,i,s,l,l,f".split(","), ctx.getSc(), (
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
                                                            YELLOW;""" +
                                                "Введите целочисленную x-координату;" +
                                                "Введите целочисленную y-координату;" +
                                                "Введите дробно-десятичную z-координату;").split(";"),
                                "more length 0;more than -867;more than -867;more than 0;more than 0;;;is color;more than -867;more than -867;more than -867".split(";")));
    }
}
