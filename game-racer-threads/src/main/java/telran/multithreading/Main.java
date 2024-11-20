package telran.multithreading;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      //Runs CLI game's menu with allowing the user to enter number of racers and a distance (number of iterations)
      //Creates Race
      //Runs Racers (Threads)
      //Prints out the Racer-winner number

      Scanner scanner = new Scanner(System.in);
      
      // Запуск меню игры для ввода количества гонщиков и дистанции
      System.out.println("Введите количество гонщиков:");
      int nRacers = scanner.nextInt();
      
      System.out.println("Введите дистанцию (количество итераций):");
      int distance = scanner.nextInt();
      
      // Создание гонки
      Race race = new Race(nRacers, distance);
      
      // Запуск гонщиков
      race.startRace();
      
      // Ожидание завершения гонки и вывод победителя
      
      race.awaitRaceEnd();
    }
}