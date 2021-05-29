package BankApp;

import BankApp.utility.Menu; // objek untuk keperluan tampilan menu
import BankApp.utility.Sys; // objek untuk keperluan clear console

import java.util.Scanner;

public class ATM {

    /*
     * Method untuk menjalankan program tanpa inisialisasi objek ATM
     */
    public static void run() {

        // Initialize Scanner object for input
        Scanner sc = new Scanner(System.in);

        // Hardcoded bank for initializing
        Bank theBank = new Bank("myMoney Bank");

        Menu.landingMenu(theBank, sc);
    }

    /*
     * Tampilan pertama untuk user melakukan login-authentication
     * @param theBank           objek Bank tempat data user disimpan
     * @param sc                objek Scanner untuk menerima input dari user
     */
    public static void userLogin(Bank theBank, Scanner sc) {
        User curUser;

        while (true) {

            // tetap pada prompt login hingga login sukses
            curUser = ATM.authUser(theBank, sc);

            // tetap pada user main menu hingga user melakukan logOut
            Menu.userDashboardMenu(curUser, theBank, sc);
        }
    }

    /*
     * Melalukan autentikasi user menggunakan UserID dan juga pin
     * @param theBank           objek Bank dimana data user disimpan
     * @param sc                objek Scanner untuk menerima input dari user
     * @return                  objek User yang telah terautentikasi
     */
    private static User authUser(Bank theBank, Scanner sc) {

        Sys.clearScreen();

        // Initialize
        String userID, pin;
        User authUser;

        do {
            System.out.print("\nMasukan ID: ");
            userID = sc.nextLine();
            System.out.print("Masukan Pin: ");
            pin = sc.nextLine();

            // try to get the user object
            authUser = theBank.userLogin(userID, pin);

            if (authUser == null) {
                System.out.println("Pin yang anda masukan salah, Silahkan coba lagi...");
            }
        } while (authUser == null);

        return authUser;
    }

    /*
     * Membuat user baru melalui console input
     * @param theBank           objek Bank untuk menyimpan data
     * @param sc                objek Scanner untuk input dari user
     */
    public static void createUser(Bank theBank, Scanner sc) {

        // Initialize
        String firstName, lastName, pin;

        // Membersihkan console
        Sys.clearScreen();

        System.out.print("Firstname: ");
        firstName = sc.nextLine();
        System.out.print("Lastname: ");
        lastName = sc.nextLine();
        System.out.print("Pin: ");
        pin = sc.nextLine();

        // Membuat user baru
        User newUser = theBank.addUser(firstName, lastName, pin);

        // Membuat Akun CreditCard ketika user baru ditambahkan
        Account creditCardAccount = new Account("Credit Card", newUser, theBank);
        newUser.addAccount(creditCardAccount);
        theBank.addAccount(creditCardAccount);
    }

    /*
     * Menampilkan semua data user yang telah disimpan di dalam Objek Bank
     * @param thebank           objek Bank yang diacu
     * @param sc                objek Scanner untuk input dari user
     */
    public static void showAllUsers(Bank theBank, Scanner sc) {

        Sys.clearScreen();

        int choice = 1;

        do {
            System.out.println("List User pada myMoney Bank");
            theBank.getAllUsersInfo();
            System.out.print("\n\nMasukan 0 untuk kembali: ");
            choice = Integer.parseInt(sc.nextLine());

            if (choice == 0) {
                Menu.landingMenu(theBank, sc);
            } else {
                System.out.println("Input salah...");
                ATM.showAllUsers(theBank, sc);
            }
        } while (true);
    }

    /*
     * Membuat akun baru untuk user yang telah login
     * @param loggedUser        objek User yang telah terautentikasi
     * @param theBank           objek Bank yang diacu
     * @param sc                objek Scanner untuk input dari user
     */
    public static void createUserAccount(User loggedUser, Bank theBank, Scanner sc) {

        // initialize
        String accountName;

        System.out.print("Account name: ");
        accountName = sc.nextLine();

        Account newAccount = new Account(accountName, loggedUser, theBank);
        loggedUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
    }

    /*
     * Menghapus Objek Account sesuai nama akun yang diberikan user
     * @param loggedUser        objek User yang telah terautentikasi
     * @param sc                objek Scanner untuk input dari user
     */
    public static void deleteUserAccount(User loggedUser, Scanner sc) {

        // initialize
        String delName;

        System.out.print("Masukan nama account: ");
        delName = sc.nextLine();

        loggedUser.deleteAccount(delName, loggedUser);
    }


    /*
     * Menampilkan histori transaksi dari akun dengan mengirimkan objek User yang terautentikasi
     * @param loggedUser        objek User yang telah terautentikasi
     * @param sc                objek Scanner untuk input dari user
     */
    public static void showTransHistory(User loggedUser, Scanner sc) {
        int num;

        // Mendapatkan index account yang akan dicari histori transaksinya
        do {

            System.out.printf(
                    "Masukan angka (1-%s) sesuai nomor akun yang tertera\n"
                            + "Histori transaksi mana yang ingin anda lihat: ",
                    loggedUser.numAccounts());

            // karena indeks ArrayList dimulai dari 0
            num = Integer.parseInt(sc.nextLine()) - 1;

            if (num < 0 || num > loggedUser.numAccounts()) {
                System.out.println("Akun tidak tersedia, Silahkan coba kembali.");
            }
        } while (num < 0 || num > loggedUser.numAccounts());

        // Menghapus Screen
        Sys.clearScreen();

        // Menampilkan histori transaksi
        loggedUser.getUserTransHistory(loggedUser, num);

        System.out.println("\n\nTekan apapun untuk kembali ke dashboard awal");

        // Menghentikan terminal hingga User memasukan input
        sc.nextLine();
    }

    /*
     * Memproses dana yang akan ditarik
     * @param loggedUser        objek User yang telah terautentikasi
     * @param sc                objek Scanner untuk input dari user
     */
    public static void withdrawFunds(User loggedUser, Scanner sc) {

        // initialize
        int fromIndex;
        double amount, accountBal;
        String memo;

        // Mendapatkan index akun untuk ditarik saldonya
        do {
            System.out.printf("Masukan angka (1-%s) sesuai nomor akun yang tertera\n"
                    + "Penarikan saldo pada akun: ", loggedUser.numAccounts());
            fromIndex = Integer.parseInt(sc.nextLine()) - 1;

            if (fromIndex < 0 || fromIndex >= loggedUser.numAccounts()) {
                System.out.println("Akun tidak tersedia, silahkan coba kembali.");
            }
        } while (fromIndex < 0 || fromIndex >= loggedUser.numAccounts());

        accountBal = loggedUser.getAccBalance(fromIndex);

        // Mendapatkan saldo untuk ditarik
        do {
            System.out.printf("Masukan saldo yang akan ditarik (max Rp %.02f): Rp ", accountBal);
            amount = Double.parseDouble(sc.nextLine());

            if (amount < 0) {
                System.out.println("Tidak dapat melakukan penarikan saldo dibawah Rp 0");
            } else if (amount > accountBal) {
                System.out.printf("Transaksi gagal, saldo tidak mencukupi.\n"
                        + "Saldo anda saat ini Rp %.0f\n", accountBal);
            }
        } while (amount < 0 || amount > accountBal);

        // Mendapatkan memo yang dimasukan oleh user
        System.out.print("Berikan pesan terhadap transaksi ini: ");
        memo = sc.nextLine();

        // Lakukan penarikan
        loggedUser.addAccTransaction(loggedUser, fromIndex, -1 * amount, memo);
    }

    /*
     * Memproses deposit dana terhadap akun
     * @param loggedUser        objek User yang telah terautentikasi
     * @param sc                objek Scanner untuk input dari user
     */
    public static void depositFunds(User loggedUser, Scanner sc) {

        // initialize
        int toIndex;
        double amount;
        String memo;

        // Mendapatkan index akun untuk dideposit
        do {
            System.out.printf("Masukan angka (1-%s) sesuai nomor akun yang tertera\n"
                    + "Melakukan Deposit pada akun: ", loggedUser.numAccounts());
            toIndex = Integer.parseInt(sc.nextLine()) - 1;

            if (toIndex < 0 || toIndex >= loggedUser.numAccounts()) {
                System.out.println("Akun tidak tersedia, silahkan coba kembali.");
            }
        } while (toIndex < 0 || toIndex >= loggedUser.numAccounts());

        // Mendapatkan saldo untuk dideposit
        do {
            System.out.print("Masukan saldo yang akan anda depositokan: Rp ");
            amount = Double.parseDouble(sc.nextLine());

            if (amount < 0) {
                System.out.println("Masukan saldo diatas Rp 0");
            }
        } while (amount < 0);

        // Mendapatkan memo yang dimasukan oleh user
        System.out.print("Berikan pesan terhadap transaksi ini: ");
        memo = sc.nextLine();

        // Lakukan deposit saldo
        loggedUser.addAccTransaction(loggedUser, toIndex, amount, memo);
    }

    /*
     * Memproses transfer dana dari akun satu ke akun yang lain dalam satu objek User
     * @param loggedUser        objek User yang telah terautentikasi
     * @param sc                objek Scanner untuk input dari user
     */
    public static void transferFunds(User loggedUser, Scanner sc) {

        // initialize
        int fromIndex, toIndex;
        double amount, accountBal;

        // Mendapatkan index akun rekening asal untuk ditransfer
        do {
            System.out.printf("Masukan angka (1-%s) sesuai nomor akun yang tertera\n"
                    + "Akun rekening asal: ", loggedUser.numAccounts());
            fromIndex = Integer.parseInt(sc.nextLine()) - 1;

            if (fromIndex < 0 || fromIndex >= loggedUser.numAccounts()) {
                System.out.println("Akun tidak tersedia, silahkan coba kembali.");
            }
        } while (fromIndex < 0 || fromIndex >= loggedUser.numAccounts());

        accountBal = loggedUser.getAccBalance(fromIndex);

        // Mendapatkan index akun rekening tujuan untuk ditransfer
        do {
            System.out.printf("Masukan angka (1-%s) sesuai nomor akun yang tertera\n"
                    + "Akun rekening tujuan: ", loggedUser.numAccounts());
            toIndex = Integer.parseInt(sc.nextLine()) - 1;

            if (toIndex < 0 || toIndex >= loggedUser.numAccounts()) {
                System.out.println("Akun tidak tersedia, silahkan coba kembali.");
            }
        } while (toIndex < 0 || toIndex >= loggedUser.numAccounts());

        // Mendapatkan saldo untuk ditransfer
        do {
            System.out.printf("Masukan saldo yang akan ditransfer (max Rp %.02f): Rp ", accountBal);
            amount = Double.parseDouble(sc.nextLine());

            if (amount < 0) {
                System.out.println("Tidak dapat melakukan transfer saldo dibawah Rp 0");
            } else if (amount > accountBal) {
                System.out.printf("Transaksi gagal, saldo tidak mencukupi.\n"
                        + "Saldo anda saat ini Rp %.0f\n", accountBal);
            }

        } while (amount < 0 || amount > accountBal);

        // Melakukan transfer ke rekening tujuan
        loggedUser.addAccTransaction(loggedUser, fromIndex, -1 * amount, String
                .format("Transfer menuju akun %s", loggedUser.getAccountUUID(loggedUser, toIndex)));
        loggedUser.addAccTransaction(loggedUser, toIndex, amount, String
                .format("Transfer dari akun %s", loggedUser.getAccountUUID(loggedUser, fromIndex)));
    }
}