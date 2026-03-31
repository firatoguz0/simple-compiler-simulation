# simple-compiler-simulation
El-Ceziri ISA mimarisi için geliştirilmiş, kaynak kodunu makine diline çeviren ve sanal işlemci üzerinde yürüten Java tabanlı derleyici ve yorumlayıcı simülasyonu.

El-Ceziri ISA Interpreter & Assembler
Bu proje, özel bir Komut Kümesi Mimarisine (ISA) sahip olan El-Ceziri işlemcisi için geliştirilmiş bir yazılım simülasyonudur. Java dili kullanılarak geliştirilen bu sistem, kaynak kodu analiz eder, makine diline çevirir ve sanal bir ortamda yürütür.

🛠 Özellikler
Lexical Analysis (Tokenizer): Kaynak kodu anlamlı parçalara ayırır (HTML ve Assembly desteği).
Syntax Validation (Parsing): Komutların ve etiketlerin yazım kurallarına uygunluğunu denetler.
Machine Code Translation: Assembly komutlarını Binary ve Hex formatına dönüştürür.
Execution Engine: Register (AX, BX, CX, DX), RAM ve Bayrak (Sıfır, İşaret, Taşma) yönetimini simüle eder.

### 🚀 Desteklenen Komutlar
* **Veri Transferi:** `ATM` (Atama)
* **Aritmetik:** `TOP`, `CRP`, `CIK`, `BOL`
* **Mantıksal:** `VE`, `VEY`
* **Akış Kontrolü:** `D` (Dallanma), `DE`, `DB`, `DK`
* **I/O:** `OKU`, `YAZ`

  
📂 Dosya Yapısı
Tokenizer.java: HTML ve metin parçalayıcı.
Parsing.java: Yazım denetimi mekanizması.
MakineDilineCev.java: Assembler katmanı.
Execution.java: Komutları yürüten sanal işlemci çekirdeği.**
