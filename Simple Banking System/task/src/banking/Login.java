package banking;

import java.util.Scanner;

public class Login {

    private Connect connect;
    private CreditCardAccount creditCardAccount;

    public Login(Connect connect, CreditCardAccount creditCardAccount) {
        this.connect = connect;
        this.creditCardAccount = creditCardAccount;
    }

    public void logIn() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        do {
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
            System.out.print(">");

            String menu = scanner.nextLine();
            switch (menu) {
                case "1":
                    System.out.println("\nBalance: " + connect.getBalance(creditCardAccount.getCreditCardNum()) + "\n");
                    break;
                case "2":
                    System.out.println("\nEnter income:");
                    System.out.print(">");
                    Integer income = Integer.parseInt(scanner.nextLine());
                    connect.depositIncome(creditCardAccount.getCreditCardNum(), income);
                    break;
                case "3":
                    System.out.println("\nTransfer");
                    System.out.println("Enter card number:");
                    System.out.print(">");
                    String cardNumber = scanner.nextLine();
                    if (cardNumber.equals(creditCardAccount.getCreditCardNum())) {
                        System.out.println("You can't transfer money to the same account!\n");
                        break;
                    }

                    if (!CreditCardAccount.validateCreditCardNumber(cardNumber)) {
                        System.out.println("Probably you made mistake in the card number.");
                        System.out.println("Please try again!\n");
                        break;
                    }

                    if (!connect.accountExists(cardNumber)) {
                        System.out.println("Such a card does not exist.\n");
                        break;
                    }

                    Integer balance = connect.getBalance(creditCardAccount.getCreditCardNum());
                    System.out.println("How much money do you want to transfer:");
                    System.out.print(">");
                    int moneyToTransfer = Integer.parseInt(scanner.nextLine());

                    if (moneyToTransfer > balance) {
                        System.out.println("Not enough money!\n");
                        break;
                    }

                    connect.transferMoney(creditCardAccount.getCreditCardNum(), cardNumber, moneyToTransfer);

                    break;
                case "4":
                    connect.closeAccount(creditCardAccount.getCreditCardNum());
                    running = false;
                    break;
                case "5":
                    System.out.println("\nYou have successfully logged out!\n");
                    running = false;
                    break;
                case "0":
                    System.out.println("\nBye!\n");
                    System.exit(0);
                    break;
            }
        } while (running);

    }

}
