package org.example;

import java.util.Scanner;

public class Bot {
    private Scanner scanner = new Scanner(System.in);
    // создаем объект класса scanner и читаем ввод пользователя в интерактивном режиме

    public void start() {
        System.out.println("Вас приветствует бот-лингвист!\nДля получения справки введите команду /help");

        while (true) {
            System.out.print("\nВведите слово или команду: ");
            if (!(new CommandProcessor().processCommand(scanner.nextLine()))) {
                break;
            }
        }
        scanner.close();
    }
}