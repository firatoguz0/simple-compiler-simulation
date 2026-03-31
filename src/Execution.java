import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Execution {

    private static int AX, BX, CX, DX; 
    private static final int[] RAM = new int[256]; 
    private static boolean SifirBayrak = false;
    private static boolean IsaretBayrak = false;
    private static boolean TasmaBayrak = false;
    private static int PC = 0; 
    private static final Map<String, Integer> etiketler = new HashMap<>();
    private static final List<String> kaynakKodSatirlari = new ArrayList<>();
    
    private static Scanner scanner = new Scanner(System.in); 

    public static void main(String[] args) {
        String dosyaYolu = "Odev3-2.txt"; 

        try {
            List<String> lines = Files.readAllLines(Paths.get(dosyaYolu));
            kaynakKodSatirlari.addAll(lines);
            
            for (int i = 0; i < kaynakKodSatirlari.size(); i++) {
                String satir = kaynakKodSatirlari.get(i).trim();
                if (satir.contains(":")) {
                    String etiketAd = satir.substring(0, satir.indexOf(':')).trim();
                    if (!etiketAd.isEmpty()) {
                        etiketler.put(etiketAd, i);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Hata: Kaynak dosya (" + dosyaYolu + ") okunamadi.");
            return;
        }

        while (PC >= 0 && PC < kaynakKodSatirlari.size()) {
            String tamSatir = kaynakKodSatirlari.get(PC).trim();

            if (tamSatir.isEmpty()) {
                PC++;
                continue; 
            }
            
            String islenecekSatir = tamSatir;
            if (islenecekSatir.contains(":")) {
                int kolonIndeksi = islenecekSatir.indexOf(':');
                islenecekSatir = islenecekSatir.substring(kolonIndeksi + 1).trim();
                
                if (islenecekSatir.isEmpty()) {
                    PC++;
                    continue;
                }
            }
            
            String[] parcalar = islenecekSatir.split("\\s+");
            String komut = parcalar[0];
            
            String op1Str = parcalar.length > 1 ? parcalar[1] : null;
            String op2Str = parcalar.length > 2 ? parcalar[2] : null;

            int sonrakiPC = PC + 1;

            switch (komut) {
                case "ATM": ATM(op1Str, op2Str); break;
                case "TOP": TOP(op1Str, op2Str); break;
                case "CRP": CRP(op1Str, op2Str); break;
                case "CIK": CIK(op1Str, op2Str); break;
                case "BOL": BOL(op1Str, op2Str); break;
                case "VE":  VE(op1Str, op2Str);  break;
                case "VEY": VEY(op1Str, op2Str); break;
                
                case "D":   sonrakiPC = D(op1Str); break;
                case "DEG": sonrakiPC = DEG(op1Str); break;
                case "DE":  sonrakiPC = DE(op1Str); break;
                case "DED": sonrakiPC = DED(op1Str); break;
                case "DB":  sonrakiPC = DB(op1Str); break;
                case "DBE": sonrakiPC = DBE(op1Str); break;
                case "DK":  sonrakiPC = DK(op1Str); break;
                case "DKE": sonrakiPC = DKE(op1Str); break;
                case "OKU": OKU(op1Str); break;
                case "YAZ": YAZ(op1Str); break;
                
                case "SON": 
                    yazdirRegister();
                    PC = -1; 
                    continue; 
            }
            PC = sonrakiPC;
        }
    }
    
    private static void yazdirRegister() {
        System.out.println("----------------------------------");
        System.out.println(String.format("AX: %d", AX));
        System.out.println(String.format("BX: %d", BX));
        System.out.println(String.format("CX: %d", CX));
        System.out.println(String.format("DX: %d", DX));
    }

    private static int degerAl(String operand) {
        switch (operand) {
            case "AX": return AX;
            case "BX": return BX;
            case "CX": return CX;
            case "DX": return DX;
            default:
                if (operand.startsWith("[") && operand.endsWith("]")) {
                    String adresStr = operand.substring(1, operand.length() - 1);
                    int adres;
                    switch (adresStr) {
                        case "AX": adres = AX; break;
                        case "BX": adres = BX; break;
                        case "CX": adres = CX; break;
                        case "DX": adres = DX; break;
                        default: adres = Integer.parseInt(adresStr); break;
                    }
                    return RAM[adres];
                }
                return Integer.parseInt(operand);
        }
    }
    private static void degerAta(String hedef, int deger) {

        int yeniDeger = deger & 0xFF; 

        SifirBayrak = (yeniDeger == 0);
        IsaretBayrak = ((yeniDeger & 0x80) != 0);
        TasmaBayrak = (deger > 255 || deger < 0);
        
        switch (hedef) {
            case "AX": AX = yeniDeger; break;
            case "BX": BX = yeniDeger; break;
            case "CX": CX = yeniDeger; break;
            case "DX": DX = yeniDeger; break;
            default:
                
                String adresStr = hedef.substring(1, hedef.length() - 1);
                int adres;
                switch (adresStr) {
                    case "AX": adres = AX; break;
                    case "BX": adres = BX; break;
                    case "CX": adres = CX; break;
                    case "DX": adres = DX; break;
                    default: adres = Integer.parseInt(adresStr); break;
                }
                RAM[adres] = yeniDeger;
        }
    }
   
    public static void ATM(String hedef, String kaynak) {
        degerAta(hedef, degerAl(kaynak));
    }
    public static void TOP(String hedef, String kaynak) {
        degerAta(hedef, degerAl(hedef) + degerAl(kaynak));
    }
    public static void CRP(String hedef, String kaynak) {
        degerAta(hedef, degerAl(hedef) * degerAl(kaynak));
    }
    public static void CIK(String hedef, String kaynak) {
        degerAta(hedef, degerAl(hedef) - degerAl(kaynak));
    }
    public static void BOL(String hedef, String kaynak) {
        int kaynakDeger = degerAl(kaynak);
        if (kaynakDeger == 0) throw new ArithmeticException("Sifira bölme hatasi!");
        degerAta(hedef, degerAl(hedef) / kaynakDeger);
    }
    public static void VE(String hedef, String kaynak) {
        degerAta(hedef, degerAl(hedef) & degerAl(kaynak));
    }
    public static void VEY(String hedef, String kaynak) {
        degerAta(hedef, degerAl(hedef) | degerAl(kaynak));
    }

    public static int D(String etiket) {
        if (etiketler.containsKey(etiket)) return etiketler.get(etiket); 
        throw new IllegalArgumentException("Bilinmeyen etiket: " + etiket);
    }
    
    public static int DEG(String etiket) {
    if (!SifirBayrak) { 
        return D(etiket); 
    } else {
        return PC + 1; 
    }
}
     public static int DE(String etiket) {
    if (SifirBayrak) { 
        return D(etiket);
    } else {
        return PC + 1;
    }
}   
    public static int DED(String etiket) {
    if (IsaretBayrak) { 
        return D(etiket); 
    } else {
        return PC + 1;
    }
}
public static int DK(String etiket) {
    if (IsaretBayrak) { 
        return D(etiket); 
    } else {
        return PC + 1;
    }
}   
public static int DB(String etiket) {
    if (!SifirBayrak && !IsaretBayrak) {
        return D(etiket); 
    }
        return PC + 1; 
}
    public static int DBE(String etiket) {

    if (!IsaretBayrak) {
        return D(etiket); 
    } else {
        return PC + 1; 
    }
} 
    public static int DKE(String etiket) {
  
    if (SifirBayrak || IsaretBayrak) {
        return D(etiket); 
    } else {
        return PC + 1; 
    }
}
    public static void OKU(String hedef) {
        if (scanner.hasNextInt()) {
            degerAta(hedef, scanner.nextInt());
        } 
    }
    public static void YAZ(String kaynak) {
        System.out.println(degerAl(kaynak));
    }
}
