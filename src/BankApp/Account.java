package BankApp;

import java.util.ArrayList;

public class Account {

    // Class Attribute
    private final String name,uuid;
    private final User holder;
    private final ArrayList<Transaction> transactions;

    /*
    * Account Constructor
    * @param name           nama dari akun
    * @param holder         objek User yang memegang akun
    * @param theBank        bank yang mengeluarkan akun
    */
    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;

        // Initialize uuid untuk akun
        this.uuid = theBank.makeNewAccountUUID();

        // Initalize list kosong untuk Transactions
        this.transactions = new ArrayList<Transaction>();
    }

    /*
    * Mendapatkan list rekapitulasi saldo dari akun
    * @return String rekapitulasi
    */
    public String akunRekapitulasi() {

        // Mendapatkan saldo dari akun
        double balance = this.getBalance();

        // format dari saldo yang dimiliki user
        if (balance >= 0) {
            return String.format("%s : Rp %.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : Rp (%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    /*
    * Mendapatkan saldo dari akun
    * @return nilai saldo
    */
    public double getBalance() {
        double balance = 0;
        for (Transaction t: this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    /*
    * Menampilkan histori transaksi dari akun
    * @param cerAccount         objek Account yang akan diihat data transaksinya
    */
    public void printTransHistory(Account cerAccount) {
        System.out.printf("\nHistori Transaksi dari akun %s\n", cerAccount.uuid);

        // loop dimulai dari transaksi paling akhir
        for (int i = cerAccount.transactions.size()-1; i >= 0 ; i--) {
            System.out.println(cerAccount.transactions.get(i).transRekapitulasi());
        }
    }

    /*
    * Menambahkan transaksi baru pada spesifik akun
    * @param amount             saldo dari transaksi
    * @param memo               pesan dari transaksi
    * @param cerAccount         objek Account yang akan ditambahkan transaksinya
    */
    public void addTransaction(double amount, String memo, Account cerAccount) {

        // Membuat objek Transactions baru dan menambahkannya ke list transaksi dalam objek Account
        Transaction newTransc = new Transaction(amount, memo, cerAccount);
        cerAccount.transactions.add(newTransc);
    }

    /* GETTER */

    public String getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }



    /* SETTER */
}
