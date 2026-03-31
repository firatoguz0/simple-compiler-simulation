import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Tokenizer {

    public static void main(String[] args ) throws Exception {
        
            BufferedReader okuyucu = new BufferedReader(new FileReader("test.html"));
            StringBuilder htmlMetni = new StringBuilder();
            String satir;

            // HTML dosyasını satır satır okuma.
            while ((satir = okuyucu.readLine()) != null) {
                htmlMetni.append(satir);
            }
            okuyucu.close();

            // Frame ve panel oluşturma.
            JFrame pencere = new JFrame();
            pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Başlangıç mesajı.
            panel.add(new JLabel("Merhaba, HTML dosyanızı goruntuleyebilirsiniz."));

            int indeks = 0;
            boolean etiketIcinde = false;
            StringBuilder Etiketici = new StringBuilder();
            StringBuilder metinToken = new StringBuilder();

            while (indeks < htmlMetni.length()) {
                char karakter = htmlMetni.charAt(indeks);

                if (karakter == '<') {
                    etiketIcinde = true;
                    Etiketici.setLength(0);  

                } else if (karakter == '>') {
                    etiketIcinde = false;
                    String etiket = Etiketici.toString(); 

                      
                    if ( etiket.equals("/p")) {
                        if (metinToken.length() > 0) {
                         panel.add(new JLabel(metinToken.toString()));
                         metinToken.setLength(0);
                    }
                }
                    else if (etiket.equals("/title")) {
                        if (metinToken.length() > 0) {
                             pencere.setTitle(metinToken.toString());
                             metinToken.setLength(0);
                     }
                 }

                   else if(etiket.equals("/span")){
                        if(metinToken.length()>0){
                            panel.add(new JLabel(metinToken.toString()));
                            metinToken.setLength(0);
                        }
                    }

                    else if (etiket.equals("/h1") || etiket.equals("/h2") || etiket.equals("/h3") ||
                         etiket.equals("/h4") || etiket.equals("/h5") || etiket.equals("/h6")) {

                        if (metinToken.length() > 0) {
                            JLabel baslik = new JLabel(metinToken.toString());
        
                            int yaziBoyutu =24; 
        
                            switch (etiket) {
                            case "/h1": yaziBoyutu = 24; break;
                            case "/h2": yaziBoyutu = 22; break;
                            case "/h3": yaziBoyutu = 20; break;
                            case "/h4": yaziBoyutu = 18; break;
                            case "/h5": yaziBoyutu = 16; break;
                            case "/h6": yaziBoyutu = 14; break;
                        }
                            baslik.setFont(new Font("Arial", Font.BOLD, yaziBoyutu));
                            panel.add(baslik);
                            metinToken.setLength(0);
                        }
                    }
            
                     else if (etiket.equals("input")) {
                        panel.add(new JTextField(1000));
                    } else if (etiket.equals("button")) {
                        panel.add(new JButton("Button"));
                    } else if (etiket.equals("a")) {
                        JButton link = new JButton("Bağlantı");
                        panel.add(link);
                    }
                }
                 else {
                    // Etiketin içinde veya dışında olmasına göre ekleme yapar.
                    if (etiketIcinde==true) {
                        Etiketici.append(karakter);
                    } else {
                        metinToken.append(karakter);
                    }
                }
                indeks++;
            }
            pencere.add(panel);
            pencere.setSize(500, 400);
            pencere.setVisible(true);
        }
}   