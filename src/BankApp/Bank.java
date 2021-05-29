package BankApp;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Bank {

    // Class Attribute
    private final String name;
    private final ArrayList<User> users;
    private final ArrayList<Account> accounts;

    /*
     * Bank Constructor
     * @param            nama dari bank yang akan diinisialisasi
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /*
     * Membuat uuid baru untuk setiap akun baru
     * @return           account's uuid
     */
    public String makeNewAccountUUID() {

        // Initialize
        String uuid;
        boolean isUnique;

        // loop sampai mendapatkan uuid unik
        do {
            // Membuat UUID baru
            uuid = UUID.randomUUID().toString();

            // Cek keunikan UUID untuk setiap Akunnya
            isUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    isUnique = true;
                    break;
                }
            }
        } while(isUnique);
        return uuid;
    }

    /*
     * Membuat uuid baru untuk setiap user baru
     * Serta menggunakan uuid untuk keperluan login setiap user
     * @return           users's uuid
     */
    public String makeNewUserUUID() {

        // Initialize
        String uuid;
        Random ran = new Random();
        int len = 4; // 4 digit angka untuk keperluan login
        boolean isUnique;

        // loop sampai mendapatkan uuid unik
        do {
            // membuat UUID baru
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)ran.nextInt(10)).toString();
            }

            // Cek keunikan UUID untuk setiap Usernya
            isUnique = false;
            for (User u: this.users) {
                if (uuid.compareTo(u.getUUID()) == 0){
                    isUnique = true;
                    break;
                }
            }
        } while(isUnique);
        return uuid;
    }

    /*
     * Membuat user baru beserta akun tabungan ke dalam Bank
     * @param firstName dan lastName         nama awal dan akhir dari user
     * @param pin                            pin dari user untuk login
     * @return                               objek user yang baru saja ditambahkan
     */
    public User addUser(String firstName, String lastName, String pin) {

        // membuat objek User baru
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // Membuat akun tabungan untuk user
        // Serta Menyimpan akun ke user holder dan list pada Bank
        Account tabunganAccount = new Account("Tabungan", newUser, this);
        newUser.addAccount(tabunganAccount);
        this.accounts.add(tabunganAccount);

        return newUser;
    }

    /*
     * Menambahkan akun ke dalam accounts pada Bank
     * @param anAccount              akun untuk ditambahkan
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    /*
     * Melakukan autentikasi user menggunakan userId dan juga Pin
     * @param userId                 id dari user untuk divalidasi
     * @param pin                    pin dari user untuk divalidasi
     * @return user yang telah berhasil login atau null
     */
    public User userLogin(String userID, String pin) {
        for (User u: this.users) {

            // check user ID
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }

        // jika user ID dan pin tidak tervalidasi
        return null;
    }

    /* GETTER */

    public String getName(){
        return this.name;
    }

    public void getAllUsersInfo() {
        for (int i = 0; i < this.users.size(); i++) {
            System.out.printf("\n%s) %s",
                    this.users.get(i).getUUID(), this.users.get(i).getFullName());
        }
    }
}
