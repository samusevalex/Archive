package com.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Generator {
    public static void main(String[] args) {
        /**
         * Проверка на количество аргументов. Если =0 выдаём --help
         */
        if (args.length == 0) {
            System.out.println("usage: Gererator DESTINATION");
            return;
        }

        try (FileOutputStream output = new FileOutputStream(args[0])) {
            Random random = new Random();
            /**
             * Основной код генератора файла случайных символов.
             * Генерируем случайный символ из таблицы ASCII в диапазоне от 0х30 до 0х7A.
             * Распечатываем его 100 раз.
             * Генерируем следующий символ. Распечатываем его 200 раз.
             * и т.д. до 1500
             */
            for (int c = 0; c <= 1500; c += 100)
                for (int i = c, s = random.nextInt(0x4A) + 0x30; i-- > 0; output.write(s)) ;

        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println(args[0] + " generated successfully");
    }
}