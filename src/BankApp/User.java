package BankApp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    // Class Attribute
    private final String firstName, lastName, uuid;
    private byte[] pinHash;
    private final ArrayList<Account> accounts;

    /*
    * User Constructor
    * @param firstName dan lastName         nama awal dan akhir dari user
    * @param pin                            pin dari user untuk akunnya
    * @param theBank                        objek Bank dimana user adalah customernya
    */
    public User(String firstName, String lastName, String pin, Bank theBank) {

        // Initialize
        this.firstName = firstName;
        this.lastName = lastName;

        // Mengimplementasikan penyimpan pin yang telah di hash untuk keperluan security
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // Mendapatkan UUID baru untuk user
        this.uuid = theBank.makeNewUserUUID();

        // Initialize list kosong untuk User
        this.accounts = new ArrayList<Account>();
    }

    /*
    * Menambahkan objek Account ke dalam list accounts pada objek User
    * @param anAccount                      objek Account yang akan ditambahkan
    */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    /*
    * Cek apakah pin yang diberikan cocok dengan pin milik user
    * @param  thePin                        pin untuk dicek
    * @return kondisi true/false setelah pengecekan
    */
    public boolean validatePin(String thePin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(thePin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException e){
            System.err.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // jika pin tidak tervalidasi
        return false;
    }

    /*
    * Menampilkan rekapitulasi akun beserta saldo yang dimiliki tiap akun
    * @param loggedUser                     user yang telah terautentikasi
    */
    public void userRekapitulasi(User loggedUser) {

        System.out.printf("\n\nSelamat datang %s\n\n", loggedUser.getFirstName());

        // Menampilkan seluruh objek Account yang dimiliki user
        System.out.println("Akun anda :");
        for (int i = 0; i < loggedUser.accounts.size(); i++) {
            System.out.printf("%d) %s\n", (i + 1),
                    loggedUser.accounts.get(i).akunRekapitulasi());
        }
    }

    /*
    * Menghapus Objek Account sesuai nama yang diberikan user
    * @param delName                        String nama yang diberikan user
    * @param loggedUser                     objek User yang terautentikasi
    */
    public void deleteAccount(String delName, User loggedUser) {

        for (int i = 0; i < loggedUser.accounts.size(); i++) {
            if (loggedUser.accounts.get(i).getName().compareTo(delName) == 0) {
                loggedUser.accounts.remove(i);
            }
        }
    }

    /*
    * */
    public void addAccTransaction(User loggedUser, int index, double amount, String memo) {

        // akun yang akan digunakan untuk pengecekan transaksi
        Account cerAccount = loggedUser.accounts.get(index);

        loggedUser.accounts.get(index).addTransaction(amount, memo, cerAccount);
    }


    /* GETTER */

    public String getUUID() {
        return this.uuid;
    }

    public String getAccountUUID(User loggedUser, int index) {
        return loggedUser.accounts.get(index).getUUID();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    /*
     * Mendapatkan histori transaksi dari akun sesuai index
     * @param loggedUser                    objek User yang akan dicari index akunnya
     * @param index                         index dari akun yang akan dicari
     */
    public void getUserTransHistory(User loggedUser, int index) {

        // akun yang akan dilihat histori transaksinya
        Account cerAccount = loggedUser.accounts.get(index);

        loggedUser.accounts.get(index).printTransHistory(cerAccount);
    }

    /*
    * Mendapatkan saldo dari akun sesuai index
    * @param index                          index dari akun yang akan dicari
    * @return saldo dari akun
    */
    public double getAccBalance(int index) {
        return this.accounts.get(index).getBalance();
    }
}
