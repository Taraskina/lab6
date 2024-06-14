package org.example.main;



import org.example.commands.utility.CommandMapper;
import org.example.commands.utility.NotFound;
import org.example.exceptions.LOLDIDNTREAD;
import org.example.exceptions.NotFoundedCommand;
import org.example.utilites.Context;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import static org.example.commands.utility.Command.extractCommand;
import static org.example.commands.utility.CommandMapper.setCommands;
import static org.example.utilites.ServerMessaging.nioRead;
import static org.example.utilites.ServerMessaging.nioSend;


public class Main {
    private Main() {
    }

    private final static Set<String> wasExecuted = new HashSet<>();

    public static Set<String> getWasExecuted() {
        return wasExecuted;
    }
    static SocketChannel socketChannel;

    public static boolean flag = true;
    private  static void setConnection(){
        boolean flag = true;
        while (flag) {
            try {
                flag = false;
                InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8081);

                //InetSocketAddress  socketAddress = new InetSocketAddress(InetAddress.getByName("helios.cs.ifmo.ru"),8081);
                socketChannel = SocketChannel.open(socketAddress);

                socketChannel.configureBlocking(false);
                System.out.println("connect with server");
                //socketChannel.write(ByteBuffer.wrap("QkfR<6584".getBytes()));
            } catch (ConnectException e) {
                flag = true;
                e.printStackTrace();
                System.out.println("Не удалось подключиться к серверу,введите любую строку,чтобы попробовать еще раз");
                new Scanner(System.in).nextLine();
            } catch (IOException e) {
                flag = true;
                e.printStackTrace();
                System.out.println("Не удалось подключиться к серверу,введите любую строку,чтобы попробовать еще раз");
                new Scanner(System.in).nextLine();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        setConnection();
        setCommands(socketChannel);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ + SelectionKey.OP_WRITE);
        System.out.println("доступные команды и их типы \n" + CommandMapper.nameToTypeMap);

        while (flag) {
            try {

                try {
                    if (System.in.available() > 0) {
                        executeNext(sc);
                    }
                } catch (NotFoundedCommand e) {
                    System.out.println("Unknown command,try again or use 'help' toget information about aviable commands");

                }
                try {
                    if (selector.selectNow() >= 0) {
                        for (SelectionKey key : selector.selectedKeys()) {
                            getAnswerFromServer();
                            socketChannel.register(selector, SelectionKey.OP_READ + SelectionKey.OP_WRITE);
                        }
                    }
                } catch (LOLDIDNTREAD e) {
                }

            } catch (NoSuchElementException | IOException e) {

                System.err.println("Не надо вводить ctrl+D !!!");
                System.exit(0);
            }
        }



    }
    public static void executeNext(Scanner s) throws IOException{
        Request req = extractCommand(s.nextLine(), new Context(new Scanner(System.in))).calling();//прогоняем через кастрированую систему команд,инициализируя commandToExecute и принимая аргументы в ее args
        if (!(req.commandToExecute instanceof NotFound)) {
            req.addMessage(req.getCommandToExecute().getName());
            nioSend(socketChannel, req);
        } else throw new NotFoundedCommand();
    }

    public static void getAnswerFromServer() throws LOLDIDNTREAD {
        Response response = null;

        try {
            response = nioRead(socketChannel);
        } catch (IOException ignored) {

        }
        if (response != null) {
            if (!response.getMessages().isEmpty()) {
                response.getMessages().forEach(System.out::println);
                flag = response.isFlag();
            }

        }
    }


}