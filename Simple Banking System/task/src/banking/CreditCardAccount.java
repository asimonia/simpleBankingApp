package banking;

import java.util.Random;

public class CreditCardAccount {

    private String creditCardNum;
    private String pin;
    private Integer balance;

    public CreditCardAccount() {

    }

    public void createAccount() {
        Random random = new Random();
        StringBuilder creditCardNum = new StringBuilder();
        creditCardNum.append("400000");
        for (int i = 0; i < 9; i++) {
            creditCardNum.append(random.nextInt(10));
        }
        String tempCC = creditCardNum.toString();
        int temp = 0;
        boolean doubled = true;
        for (int j = 14; j >= 0; j--) {
            int n = Integer.parseInt(String.valueOf(tempCC.charAt(j)));
            if (doubled) {
                if ((2 * n) > 9) {
                    temp += (2 * n) - 9;
                } else {
                    temp += 2 * n;
                }
                doubled = false;
            } else {
                temp += n;
                doubled = true;
            }
        }
        temp = (temp * 9) % 10;
        creditCardNum.append(temp);


        this.creditCardNum = creditCardNum.toString();
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append(random.nextInt(10));
        }
        this.pin = pin.toString();
        this.balance = 0;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void persist(Connect connect) {
        connect.insertNewAccount(creditCardNum, pin, balance);
    }

    public static boolean validateCreditCardNumber(String str) {

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;

        for (int anInt : ints) {
            sum += anInt;
        }

        return sum % 10 == 0;
    }

}
