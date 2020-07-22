package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<CreditCardAccount> creditCardAccounts = new ArrayList<>();

    public static void main(String[] args) {
        if (args[0].equals("-fileName") && args[1] != null) {
            Connect connect = new Connect(args[1]);
            connect.connect();
            connect.createNewTable();

            boolean running = true;

            do {
                System.out.println("1. Create an account");
                System.out.println("2. Log into account");
                System.out.println("0. Exit");
                System.out.print(">");

                String menu = scanner.nextLine();

                switch (menu) {
                    case "1":
                        CreditCardAccount account = new CreditCardAccount();
                        account.createAccount();
                        System.out.println("");
                        System.out.println("Your card has been created");
                        System.out.println("Your card number:");
                        System.out.println(account.getCreditCardNum());
                        System.out.println("Your card PIN:");
                        System.out.println(account.getPin());
                        account.persist(connect);
                        creditCardAccounts.add(account);
                        System.out.println("");
                        break;
                    case "2":
                        System.out.println("");
                        System.out.println("Enter your card number:");
                        System.out.print(">");
                        String cardNumber = scanner.nextLine();

                        System.out.println("Enter your PIN:");
                        System.out.print(">");
                        String pin = scanner.nextLine();
                        System.out.println("");

                        boolean found = false;
                        for (CreditCardAccount cc : creditCardAccounts) {
                            if (cc.getCreditCardNum().equals(cardNumber) &&
                                    cc.getPin().equals(pin)) {
                                found = true;
                                System.out.println("You have successfully logged in!");
                                System.out.println("");
                                Login login = new Login(connect, cc);
                                login.logIn();
                                break;
                            }
                        }

                        if (!found) {
                            System.out.println("Wrong card number or PIN!");
                        }

                        break;
                    case "0":
                        running = false;
                        break;
                }
            } while (running);
        } else {
            System.out.println("Database name is not supplied. \nExiting the program.");
        }

    }
}
