package org.example.kafkaApplication;


import java.util.Scanner;
    public class Main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Run Producer");
                System.out.println("2. Run Consumer");
                System.out.println("3. Run EventConsumer");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();

                if (choice == 0) {
                    break;
                }
                switch (choice) {
                    case 1:
                        runCommand("cmd /c start cmd /k java -cp out\\artifacts\\ProducerMain_jar\\KafkaApplication.jar org.example.kafkaApplication.Producer.ProducerMain");
                        break;
                    case 2:
                        runCommand("cmd /c start cmd /k java -cp out\\artifacts\\ConsumerMain_jar\\KafkaApplication.jar org.example.kafkaApplication.Consumer.ConsumerMain");
                        break;
                    case 3:
                        runCommand("cmd /c start cmd /k java -cp out\\artifacts\\EventConsumerMain_jar\\KafkaApplication.jar org.example.kafkaApplication.EventConsumer.EventConsumerMain");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }



            }

            System.out.println("Exiting the program.");
            scanner.close();
        }

        private static void runCommand(String command) {
            try {
                Runtime.getRuntime().exec(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
