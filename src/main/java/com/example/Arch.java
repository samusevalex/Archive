package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Arch {
    public static void main(String[] args) {
        /**
         * Проверка на количество аргументов. Должно быть 3 или более.
         */
        switch (args.length) {
            case 0:
                System.out.println("usage: Arch [ax] SOURCE DESTINATION\nCommands:\n\ta - archive\n\tx - extract");
                return;
            case 1:
                System.out.println("SOURCE and DESTINATION not defined");
                return;
            case 2:
                System.out.println("DESTINATION not defined");
                return;
        }

        try (FileInputStream input = new FileInputStream(args[1]);
             FileOutputStream output = new FileOutputStream(args[2])) {
            /**
             * На основании первого аргумента = command, выбираем действие. Если "а" - заархивировать. Если "х" - разархивировать.
             */
            switch (args[0]) {

                case "a":
                    /**
                     * Основной код Run-length encoding (RLE) архиватора: преобразует последовательность "аааддддд" в "а3д5"
                     */
                    for (int c, s, d = input.read(); (s = d) != -1; ) {
                        for (c = 1; (d = input.read()) == s && c < 255; c++) ;
                        output.write(s);
                        output.write(c);
                    }
                    break;

                case "x":
                    /**
                     * Проверка размера Source файла на чётность. Размер файла заархивированных данных всегда чётный.
                     */
                    long fileLength = new File(args[1]).length();
                    if ((fileLength & 1) == 1) throw new IOException();
                    /**
                     * Основной код Run-length encoding (RLE) разархиватора: преобразует последовательность "а3д5" в "аааддддд"
                     */
                    for (int s; (s = input.read()) != -1; )
                        for (int c = input.read(); c-- > 0; output.write(s)) ;
                    break;

                default:
                    System.err.println("Invalid command");
                    return;
            }
        } catch (FileNotFoundException e) {
            System.err.println("SOURCE file not found");
        } catch (IOException e) {
            System.err.println("SOURCE file corrupted");
        }
        System.out.println("Action completed successfully: " + args[1] + " to " + args[2]);
    }
}