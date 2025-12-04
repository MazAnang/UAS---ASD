import java.util.*;

class Mahasiswa {
    String nama, npm;
    int nilai;

    Mahasiswa(String n, String p, int v) {
        nama = n; npm = p; nilai = v;
    }
}

public class uas {

    static ArrayList<Mahasiswa> daftar = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== SISTEM RANKING MAHASISWA ===");
            System.out.println("1. Lihat Data");
            System.out.println("2. Tambah Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Sorting");
            System.out.println("5. Searching (Binary)");
            System.out.println("6. Total Nilai (Rekursi)");
            System.out.println("7. Keluar");
            System.out.print("Pilih: ");

            switch (sc.nextInt()) {
                case 1 -> tampil();
                case 2 -> tambah();
                case 3 -> hapus();
                case 4 -> sorting();
                case 5 -> searching();
                case 6 -> System.out.println("Total nilai = " + total(0));
                case 7 -> { System.out.println("Terima kasih!"); return; }
                default -> System.out.println("Pilihan salah!");
            }
            sc.nextLine();
        }
    }

    static void tampil() {
        if (daftar.isEmpty()){
            System.out.println("Data Kosong");
            return;
        }
        System.out.println("\n=== DATA MAHASISWA ===");
        int no = 1;
        for (Mahasiswa m : daftar)
            System.out.println(no++ + ". " + m.nama + " | " + m.npm + " | " + m.nilai);
    }

    static void tambah() {
        sc.nextLine();
        System.out.print("Nama: ");  String n = sc.nextLine();
        System.out.print("NPM : ");  String p = sc.next();
        System.out.print("Nilai: "); int v = sc.nextInt();
        daftar.add(new Mahasiswa(n, p, v));
        System.out.println("Data ditambah!");
    }

    static void hapus() {
        tampil();
        if (daftar.isEmpty()) return;
        System.out.print("Hapus nomor: ");
        int x = sc.nextInt();
        if (x > 0 && x <= daftar.size()) {
            daftar.remove(x - 1);
            System.out.println("Data dihapus!");
        }
    }

    static void sorting() {
        if (daftar.isEmpty()) return;

        System.out.println("\n1. Descending\n2. Ascending");
        System.out.print("Pilih: ");
        int p = sc.nextInt();

        if (p == 1) quickSort(0, daftar.size() - 1, false);
        else quickSort(0, daftar.size() - 1, true);

        tampil();
    }

    // Quick Sort gabungan ASC & DESC
    static void quickSort(int low, int high, boolean asc) {
        if (low < high) {
            int p = part(low, high, asc);
            quickSort(low, p - 1, asc);
            quickSort(p + 1, high, asc);
        }
    }

    static int part(int low, int high, boolean asc) {
        int pivot = daftar.get(high).nilai, i = low - 1;

        for (int j = low; j < high; j++) {
            boolean kondisi = asc ? daftar.get(j).nilai < pivot
                                  : daftar.get(j).nilai > pivot;
            if (kondisi) Collections.swap(daftar, ++i, j);
        }
        Collections.swap(daftar, i + 1, high);
        return i + 1;
    }

    static void searching() {
        if (daftar.isEmpty()) return;

        // sort oleh NPM dulu
        daftar.sort(Comparator.comparing(a -> a.npm));

        System.out.print("Cari NPM: ");
        String target = sc.next();

        int res = binary(target, 0, daftar.size() - 1);

        if (res >= 0) {
            Mahasiswa m = daftar.get(res);
            System.out.println(m.nama + " | " + m.npm + " | " + m.nilai);
        } else System.out.println("Tidak ditemukan!");
    }

    static int binary(String t, int l, int r) {
        if (l > r) return -1;
        int mid = (l + r) / 2;
        int cmp = daftar.get(mid).npm.compareTo(t);

        return cmp == 0 ? mid :
               cmp > 0 ? binary(t, l, mid - 1)
                       : binary(t, mid + 1, r);
    }

    static int total(int i) {
        return i == daftar.size() ? 0 : daftar.get(i).nilai + total(i + 1);
    }
}