package lesson4;

import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GameXO {

    private final static char EMPTY = '_';
    private final static char DOT_X = 'X';
    private final static char DOT_O = 'O';
    private static int fieldSize = 3;
    private static Random rand = new Random(); // для рандомного ответа компа
    //|_|_|_|
    //|_|_|_|
    //|_|_|_|

    private void printTab(char[][] tab) {
        int counter = 1;
        System.out.print("   ");
        for (int i = 0; i < tab.length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (char[] chars : tab) {
            System.out.print(counter + " ");
            counter++;
            for (char sym : chars) {
                System.out.print("|" + sym);
            }
            System.out.println("|");
        }
    }

    private void fillTab(char[][] tab) {
        for (char[] chars : tab) {
            Arrays.fill(chars, EMPTY);
        }
    }

    public void startGame(int fieldSize) {
        // TODO: 23.03.2020 *** fieldSize > 3, 4(WIN)
        char[][] tab = new char[fieldSize][fieldSize];
        fillTab(tab);
        System.out.println("Игра крестики нолики. Вы играете за Х");
        printTab(tab);
        System.out.println("Для того, чтобы совершить" +
                "" +
                " ход введите номер строки" +
                " и номер столбца таблицы");
        boolean inGame = true;
        Scanner in = new Scanner(System.in);
        while (inGame) {
            System.out.println("Ваш ход:");
            int x, y;
            try {
                x = in.nextInt();
                y = in.nextInt();
                x--;
                y--;
                if (isValid(x, y, tab)) {
                    tab[x][y] = DOT_X;
                    printTab(tab);
                    if (isVictory(tab, DOT_X)) {
                        System.out.println("Вы победили");
                        break;
                    }
                    System.out.print("Компьютер совершает ход");
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(300);
                        System.out.print(".");
                    }
                    System.out.println();
                    movePC(tab);
                    if (isVictory(tab, DOT_O)) {
                        System.out.println("Вы проиграли");
                        break;
                    }
                    if (draw(tab)) {
                        System.out.println("Ничья!");
                        break;          // допиши ничью TODO: 23.03.2020 Ничья ???
                    }
                } else {
                    System.out.println("Данный ход невозможен! Введите новые значения");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Вы ввели не цифры! Введите цифры");
                in.next();
                continue;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //..... game .....
    }

    private boolean isVictory(char [][] tab, char DOT_X /*X, O*/) {
        if (tab[0][0] == DOT_X && tab[0][1] == DOT_X && tab[0][2] == DOT_X ) return true;
        if (tab[1][0] == DOT_X && tab[1][1] == DOT_X && tab[1][2] == DOT_X ) return true;
        if (tab[2][0] == DOT_X && tab[2][1] == DOT_X && tab[2][2] == DOT_X ) return true;
        if (tab[0][0] == DOT_X && tab[1][0] == DOT_X && tab[2][0] == DOT_X ) return true;
        if (tab[0][1] == DOT_X && tab[1][1] == DOT_X && tab[2][1] == DOT_X ) return true;
        if (tab[0][2] == DOT_X && tab[1][2] == DOT_X && tab[2][2] == DOT_X ) return true;
        if (tab[0][0] == DOT_X && tab[1][1] == DOT_X && tab[2][2] == DOT_X ) return true;
        if (tab[2][0] == DOT_X && tab[1][1] == DOT_X && tab[0][2] == DOT_X ) return true;
        // у х и о - 8 вариантов победы // [0] начало с нулевого  TODO: 23.03.2020
        return false;
    }

    public static boolean draw(char[][] tab) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tab[i][j] == EMPTY) return false;
            }
        }return true;
    }
    private void movePC(char[][] tab) {
        // дописать ИИ TODO: 23.03.2020 smart strategy
        int len = tab.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (tab[i][j] == EMPTY) {
                    tab[i][j] = DOT_O;
                    printTab(tab);
                    return;
                }
            }
        }// здесь буду менять всё
    }

    private boolean isValid(int x, int y, char[][] tab) {
        int len = tab.length;
        if (x >= 0 && x < len && y >= 0 && y < len) {
            if (tab[x][y] == EMPTY) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        if (args != null && args.length == 1) {
            new GameXO().startGame(Integer.parseInt(args[0]));
        } else {
            new GameXO().startGame(3);
        }

    }
}
