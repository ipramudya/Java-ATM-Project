package BankApp;

import java.util.Date;

public class Transaction {

    // Class Attribute
    private final double amount;
    private final Date timestamp;
    private String memo;
    private final Account inAccount;  //transaksi akan disimpan pada akun tertentu

    /*
    * @OVERLOADING
    * Transaction Constructor
    * @param amount             saldo yang akan disimpan
    * @param inAccount          objek Account yang diacu
    */
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }
    /*
    * @param amount             saldo yang akan disimpan
    * @param inAccount          objek Account yang diacu
    * @param memo               pesan yang akan disimpan
    */
    public Transaction(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;
    }

    /*
    * Mendapatkan saldo transaksi
    * @return saldo transaksi
    */
    public String transRekapitulasi() {
        if (this.amount >= 0) {
            return String.format("%s : Rp %.02f : %s",
                    this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : -Rp %.02f : %s",
                    this.timestamp.toString(), -this.amount, this.memo);
        }
    }

    /* GETTER */

    public double getAmount() {
        return this.amount;
    }
}
