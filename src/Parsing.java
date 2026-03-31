import java.io.*;
import java.util.ArrayList;

public class Parsing {

    static String[] KOMUTLAR = {
        "ATM","TOP","CRP","CIK","BOL",
        "VE","VEY","D","DEG","DE",
        "DED","DB","DBE","DK","DKE",
        "OKU","YAZ"
    };

    static boolean etiketGecerliMi(String s) {
        if (s == null) return false;
        if (s.length() < 7) return false;
        if (!baslangicMi(s, "ETIKET")) return false;

        int sayi = 0;
        for (int i = 6; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
            sayi = sayi * 10 + (c - '0');
        }
        return (1 <= sayi && sayi <= 10);
    }

    static boolean registerMi(String s) {
        return s.equals("AX") || s.equals("BX") || s.equals("CX") || s.equals("DX");
    }

    static boolean baslangicMi(String s, String onEk) {
        if (s == null || onEk == null) return false;
        if (s.length() < onEk.length()) return false;
        for (int i = 0; i < onEk.length(); i++) {
            if (s.charAt(i) != onEk.charAt(i)) return false;
        }
        return true;
    }

    static String[] ayir(String satir) {
        ArrayList<String> kelimeler = new ArrayList<String>();
        int n = satir.length();
        int i = 0;
        while (i < n) {
            while (i < n && (satir.charAt(i) == ' ' || satir.charAt(i) == '\t')) i++;
            if (i >= n) break;
            String kelime = "";
            while (i < n && satir.charAt(i) != ' ' && satir.charAt(i) != '\t') {
                kelime += satir.charAt(i);
                i++;
            }
            kelimeler.add(kelime);
        }
        String[] dizi = new String[kelimeler.size()];
        for (int j = 0; j < kelimeler.size(); j++) dizi[j] = kelimeler.get(j);
        return dizi;
    }

    static boolean komutGecerliMi(String s) {
        for (String k : KOMUTLAR) {
            if (s.equals(k)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String dosyaAdi = "hatali_ornek2.txt";
        ArrayList<Integer> hataliSatirlar = new ArrayList<>();

        try {
            BufferedReader dosyaokuyucu = new BufferedReader(new FileReader(dosyaAdi));
            String satir;
            int satirNo = 0;

            while ((satir = dosyaokuyucu.readLine()) != null) {
                satirNo++;

                int ikiNokta = -1;
                for (int k = 0; k < satir.length(); k++) {
                    if (satir.charAt(k) == ':') { ikiNokta = k; break; }
                    if (satir.charAt(k) == ' ') break;
                }

                String komutKismi = satir;
                if (ikiNokta != -1) {
                    
                    String etiket = "";
                    for (int i = 0; i < ikiNokta; i++) {
                        etiket += satir.charAt(i);
                    }
                    if (!etiketGecerliMi(etiket)) {
                        hataliSatirlar.add(satirNo);
                        continue;
                    }
                    komutKismi = "";
                    for (int i = ikiNokta + 1; i < satir.length(); i++) {
                        komutKismi += satir.charAt(i);
                    }

                    boolean sadeceEtiket = true;
                    for (int i = 0; i < komutKismi.length(); i++) {
                        if (komutKismi.charAt(i) != ' ' && komutKismi.charAt(i) != '\t') {
                            sadeceEtiket = false;
                            break;
                        }
                    }
                    if (sadeceEtiket) continue;
                }

                String[] kelimeler = ayir(komutKismi);
                if (kelimeler.length == 0) {
                    hataliSatirlar.add(satirNo);
                }

                String komut = kelimeler[0];
                if (!komutGecerliMi(komut)) {
                    hataliSatirlar.add(satirNo);
                }

                else if (komut.equals("ATM") || komut.equals("TOP") || komut.equals("CRP") ||
                    komut.equals("CIK") || komut.equals("BOL") || komut.equals("VE") || komut.equals("VEY")) {
                    if (kelimeler.length != 3 || !registerMi(kelimeler[1]) || !registerMi(kelimeler[2])) {
                        hataliSatirlar.add(satirNo);
                    }
                } else if (komut.equals("DEG") || komut.equals("OKU") || komut.equals("YAZ")) {
                    if (kelimeler.length != 2 || !registerMi(kelimeler[1])) {
                        hataliSatirlar.add(satirNo);
                    }
                } else if (komut.equals("D") || komut.equals("DE") || komut.equals("DED") ||
                           komut.equals("DB") || komut.equals("DBE") ||
                           komut.equals("DK") || komut.equals("DKE")) {
                    if (kelimeler.length != 2 || !etiketGecerliMi(kelimeler[1])) {
                        hataliSatirlar.add(satirNo);
                    }
                }
            }
            dosyaokuyucu.close();

        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + dosyaAdi);
            return;
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
            return;
        }

        if (hataliSatirlar.size()==0) {
            System.out.println("YAZIM HATASI BULUNMAMAKTADIR");
        } else {
            System.out.println("YAZIM HATASI BULUNMAKTADIR");
            System.out.print("Hatali satir veya satirlar: ");
            for (int n : hataliSatirlar) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }
}