package BankApp.utility;

import BankApp.ATM;
import BankApp.Bank;
import BankApp.User;

import java.util.Scanner;

public class Menu {
    /*
     * Menu awal untuk memilih login atau membuat user baru
     * @param theBank            nama bank yang akan ditampilkan pada menu
     * @param sc                 objek untuk menerima input dari user
     */
    public static void landingMenu(Bank theBank, Scanner sc) {

        // Initialize
        int landingChoose;

        do {
            // Membersihkan console
            Sys.clearScreen();

            System.out.printf("\nWelcome to %s\n\n", theBank.getName());
            System.out.println("1) Login");
            System.out.println("2) Create New User");
            System.out.println("3) Show All Users");
            System.out.println("4) Quit");
            System.out.print("Masukan pilihan anda:");
            landingChoose = Integer.parseInt(sc.nextLine());

            if (landingChoose < 1 || landingChoose > 4) {
                System.out.println("Harap masukan pilihan yang tersedia");
            }
        } while(landingChoose < 1 || landingChoose > 4);

        switch (landingChoose) {
            case 1:
                ATM.userLogin(theBank, sc);
                break;
            case 2:
                ATM.createUser(theBank, sc);
                break;
            case 3:
                ATM.showAllUsers(theBank, sc);
                break;
            case 4:
                System.exit(1);
                break;
        }

        // infinite redisplaying
        if (landingChoose != 4) {
            Menu.landingMenu(theBank, sc);
        }
    }

    /*
     * Menu utama setiap user setelah sukses melakukan user
     * @param loggedUser         objek User yang telah sukses melakukan login
     * @param sc                 objek Scanner untuk menerima input dari user
     */
    public static void userDashboardMenu(User loggedUser, Bank theBank, Scanner sc) {

        // Membersihkan console
        Sys.clearScreen();

        // Menampilkan rekapitulasi akun user
        loggedUser.userRekapitulasi(loggedUser);

        // initialize
        int userChoice;

        // User Dashboard Menu
        do {
            System.out.println();
            System.out.println(" 1) Membuat Akun baru");
            System.out.println(" 2) Menghapus Akun Tertentu");
            System.out.println(" 3) Histori Transaksi");
            System.out.println(" 4) Penarikan Saldo");
            System.out.println(" 5) Deposit Saldo");
            System.out.println(" 6) Transfer Saldo");
            System.out.println(" 7) Logout");
            System.out.println();
            System.out.print("Pilih opsi sesuai kebutuhan anda: ");
            userChoice = Integer.parseInt(sc.nextLine());

            if (userChoice < 1 || userChoice > 7) {
                System.out.println("Pilihan anda tidak valid, Masukan angka sesuai menu");
                Menu.userDashboardMenu(loggedUser, theBank, sc);
            }
        } while (userChoice < 1 || userChoice > 7);

        // Memproses input
        switch (userChoice) {
            case 1:
                ATM.createUserAccount(loggedUser, theBank, sc);
                break;
            case 2:
                ATM.deleteUserAccount(loggedUser, sc);
                break;
            case 3:
                ATM.showTransHistory(loggedUser, sc);
                break;
            case 4:
                ATM.withdrawFunds(loggedUser, sc);
                break;
            case 5:
                ATM.depositFunds(loggedUser, sc);
                break;
            case 6:
                ATM.transferFunds(loggedUser, sc);
                break;
            case 7:
                Menu.landingMenu(theBank, sc);
                break;
        }

        // infinite redisplaying
        if (userChoice != 7) {
            Menu.userDashboardMenu(loggedUser, theBank, sc);
        }
    }
}
