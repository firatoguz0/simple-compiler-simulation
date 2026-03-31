
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class MakineDilineCev { 

    static final Map<String, String> KOMUT = new HashMap<>();
    static final Map<String, String> REG = new HashMap<>();

    static {
        KOMUT.put("ATM", "00000"); 
        KOMUT.put("TOP", "00001");
        KOMUT.put("CRP", "00010"); 
        KOMUT.put("CIK", "00011");
        KOMUT.put("BOL", "00100"); 
        KOMUT.put("VE", "00101");
        KOMUT.put("VEY", "00110"); 
        KOMUT.put("D", "00111");
        KOMUT.put("DEG", "01000"); 
        KOMUT.put("DE", "01001");
        KOMUT.put("DED", "01010"); 
        KOMUT.put("DB", "01011");
        KOMUT.put("DBE", "01100"); 
        KOMUT.put("DK", "01101");
        KOMUT.put("DKE", "01110"); 
        KOMUT.put("OKU", "01111");
        KOMUT.put("YAZ", "10000");

        REG.put("AX", "000"); 
        REG.put("BX", "001");
        REG.put("CX", "010"); 
        REG.put("DX", "011");
    }

    public static void main(String[] args) {

        List<String> kaynak;

        try {
            kaynak = Files.readAllLines(Paths.get("ManLanGeneration.txt"));
        } catch (IOException e) {
            System.out.println("Kaynak dosya okunamadi!");
            return;
        }


        StringBuilder tumProgram = new StringBuilder();

        for (String s : kaynak) {
            String satir = s.trim();

            if (satir.contains(":")) {
                satir = satir.substring(satir.indexOf(':') + 1).trim();
                if (satir.isEmpty()) continue;
            }
           
            String ikilik = binaryCevir(satir);
            String onaltilik = Hexcevir(ikilik);

            System.out.println(satir + " -> " + ikilik + " -> " + onaltilik);
            tumProgram.append(onaltilik);
        }
       
        System.out.println("-----------------------------");
        System.out.println(tumProgram.toString());
    }

    static String binaryCevir(String satir) {

        String[] p = satir.split("\\s+");
        String komut = p[0];

        StringBuilder bin = new StringBuilder();
        
        bin.append(KOMUT.get(komut));

        if (komut.equals("OKU") || komut.equals("YAZ")) {
            bin.append(REG.get(p[1]));
            return bin.toString();
        }

        if (komut.startsWith("D")) {
            bin.append("1111111"); 
            return bin.toString();
        }
        bin.append(REG.get(p[1]));
        
        if (REG.containsKey(p[2])) {
            bin.append(REG.get(p[2]));
        } else {
            
            int sabit = Integer.parseInt(p[2]);
            bin.append(Integer.toBinaryString(sabit)); 
        }
        return bin.toString();
    }

    static String Hexcevir(String bin) {

        StringBuilder hex = new StringBuilder();

        int i = 0;
        while (i + 4 <= bin.length()) {
            String dortBit = bin.substring(i, i + 4);
            
            hex.append(Integer.toHexString(Integer.parseInt(dortBit, 2)).toUpperCase());
            i += 4;
        }
        
        if (i < bin.length()) {
            String kalan = bin.substring(i);
            hex.append(Integer.toHexString(Integer.parseInt(kalan, 2)).toUpperCase());
        }
        return hex.toString();
    }
}