
# Testinium Catchylabs QE Automation

Bu proje, **Cucumber**, **Selenium WebDriver** ve **Allure** kullanarak **BDD (Behavior-Driven Development)** yaklaşımı ile test otomasyonu gerçekleştirmek üzere tasarlanmıştır. Projede, **Log4j** logging, **WebDriverManager** ile dinamik sürücü yönetimi ve çeşitli iyileştirmeler kullanılmıştır.

## İçindekiler

1. [Genel Bakış](#genel-bakış)
2. [Teknolojiler ve Bağımlılıklar](#teknolojiler-ve-bağımlılıklar)
3. [Proje Klasör Yapısı](#proje-klasör-yapısı)
4. [Önemli Dosyalar ve Sınıflar](#önemli-dosyalar-ve-sınıflar)
   - [POM (Maven) Yapısı](#pom-maven-yapısı)
   - [DriverSettings & DriverManager](#driversettings--drivermanager)
   - [Hooks](#hooks)
   - [Steps (Adım Tanımları)](#steps-adım-tanımları)
   - [Utils / SelectorUtils](#utils--selectorutils)
   - [ScenarioContext](#scenariocontext)
5. [Testlerin Çalıştırılması](#testlerin-çalıştırılması)
   - [Maven Kullanarak Testleri Çalıştırma](#maven-kullanarak-testleri-çalıştırma)
   - [Belirli Feature Dosyasını Çalıştırma](#belirli-feature-dosyasını-çalıştırma)
   - [IDE Üzerinden Test Çalıştırma](#ide-üzerinden-test-çalıştırma)
   - [Tarayıcı Seçimi](#tarayici-seçimi)
   - [Mobil Emülasyon (Chrome)](#mobil-emulasyon-chrome)
6. [Allure Raporu Oluşturma](#allure-raporu-olusturma)
   - [Maven ile Allure Raporu Üretme](#maven-ile-allure-raporu-uretme)
   - [Allure Konfigürasyonu (pom.xml)](#allure-konfigurasyonu-pomxml)
7. [Lisans](#lisans)


## Genel Bakış

Bu proje, kullanıcı senaryolarını **Cucumber** üzerinden **Given/When/Then** formatında yazarak **Selenium WebDriver** aracılığıyla web arayüz testlerini gerçekleştiren bir otomasyon çerçevesi sunar.  
**Allure** entegrasyonu sayesinde, test sonuçları ayrıntılı ve etkileşimli bir rapor halinde sunulabilir.

## Teknolojiler ve Bağımlılıklar

- **Java 21** (Maven Compiler Plugin üzerinden ayarlanmıştır)
- **Maven** (proje yönetimi ve bağımlılık yönetimi)
- **Cucumber 7.4.1** (BDD testi)
- **Selenium 4.18.1** (WebDriver API)
- **JUnit 4.13.2** (Cucumber entegrasyonu için)
- **WebDriverManager 5.9.2** (tarayıcı sürücülerini otomatik yönetir)
- **Allure 2.19.0** (test raporları oluşturur)
- **Log4j 1.2.17** (loglama için)
- **AssertJ** (daha zengin assertion seçenekleri)

Bu bağımlılıklar, `pom.xml` içerisinde **test** scope veya default scope olarak tanımlanmıştır.

## Proje Klasör Yapısı

```bash
testinium-qe-automation
├── pom.xml
├── src
│   └── test
│       ├── java
│       │   ├── context
│       │   │   └── ScenarioContext.java
│       │   ├── features
│       │   │   ├── add-money.feature
│       │   │   ├── edit-account.feature
│       │   │   ├── happy-path.feature
│       │   │   └── transfer-money.feature
│       │   ├── helpers
│       │   │   ├── ActionHelper.java
│       │   │   ├── AssertionHelper.java
│       │   │   ├── DataHelper.java
│       │   │   ├── DriverHelper.java
│       │   │   ├── ElementHelper.java
│       │   │   ├── NavigationHelper.java
│       │   │   ├── ScreenshotHelper.java
│       │   │   └── WaitHelper.java
│       │   ├── hooks
│       │   │   └── Hooks.java
│       │   ├── runners
│       │   │   └── TestRunner.java
│       │   ├── settings
│       │   │   ├── DriverManager.java
│       │   │   └── DriverSettings.java
│       │   ├── steps
│       │   │   ├── ActionSteps.java
│       │   │   ├── AssertionSteps.java
│       │   │   ├── DataSteps.java
│       │   │   ├── NavigationSteps.java
│       │   │   └── WaitSteps.java
│       │   └── utils
│       │       ├── ExceptionHandler.java
│       │       └── SelectorUtils.java
│       └── resources
│           ├── cucumber.properties
│           ├── elements.json
│           └── log4j.properties
```

> **Not**: `features` klasörünü `src/test/resources/features` altında tutmayı da tercih edebilirsiniz. Önemli olan `@CucumberOptions(features=...)` kısmında ve `TestRunner.java` sınıfında yolu güncellemektir.
---

## Önemli Dosyalar ve Sınıflar

### POM (Maven) Yapısı

`pom.xml` dosyasında şu ayarlar öne çıkar:

- **Surefire Plugin**: `mvn test` komutuyla testleri çalıştırmak için.
- **Allure Maven Plugin**: `mvn allure:serve` veya `mvn allure:report` ile rapor oluşturma.
- **Cucumber**, **Selenium**, **JUnit**, **WebDriverManager**, **AssertJ**, **Log4j** gibi test bağımlılıkları.

### DriverSettings & DriverManager

- **DriverSettings**  
  WebDriver’ı başlatır (`setDriver(...)`) ve her bir thread için `ThreadLocal<WebDriver>` ataması yapar.  
  `getDriver()` ile ilgili test adımlarında sürücüye erişilir.

- **DriverManager**  
  Cucumber **Scenario** etiketlerine bakarak (`@browser:chrome`, `@browser:firefox`, `@mobile` vs.) `DriverSettings.setDriver(...)` çağrısı yapar.  
  Örneğin `@Before` hook’unda `DriverManager.configureDriverBasedOnTag(scenario)` metodu çağrılır.
> Eğer tarayıcı etiketi verilmezse, **varsayılan olarak "chrome" başlatılır.**


### Hooks

- `Hooks.java`  
  - `@Before` metodu ile senaryoyu başlatırken tarayıcıyı (Driver) ayarlar ve senaryo detaylarını (Allure Attachments) ekler.  
  - `@After` metodu ile senaryo bittiğinde (özellikle hata durumlarında) **screenshot** alır ve `DriverSettings.quitDriver()` ile tarayıcıyı kapatır.

### Steps (Adım Tanımları)

- `ActionSteps.java`: Tıklama, JS ile tıklama, metin girişi (`Enter text ...`), temizleyip metin girişi gibi **etkileşim** adımlarını barındırır.  
- `AssertionSteps.java`: Element text doğrulama, element varlık doğrulama, sayfa başlığı vb. **kontrol** (assert) adımlarını içerir.  
- `DataSteps.java`: Değerleri hafızaya alma (`Store text from element ... into variable ...`), bu değerleri karşılaştırma (`Compare text in variable ...`) gibi **değişken** tabanlı adımlar.  
- `NavigationSteps.java`: URL’ye git, geri/ileri yönlendir, sayfa yenile gibi **navigasyon** adımlarını içerir.  
- `WaitSteps.java`: Belirli saniye bekleme (`Wait for X seconds`), element görünür olana kadar bekleme gibi adımlara sahiptir.

### utils / SelectorUtils

- `SelectorHelper.java`  
  `elements.json` içindeki key-value eşleşmelerini okur (örn. `"loginButton": { "cssSelector": "#login-button" }`).  
  JSON’da `cssSelector`, `xpath`, `id`, veya `name` olarak geçen alanı `By.cssSelector(...)`, `By.xpath(...)` vb. olarak döndürür.  

### ScenarioContext

- `ScenarioContext.java`  
  Test senaryosu boyunca saklanması gereken verileri (örneğin, bir elementten okunan `amountText`) **ThreadLocal** olarak tutar.  
  `ScenarioContext.put("key", "value")` ve `ScenarioContext.get("key")` ile erişim sağlar.

---

## Testlerin Çalıştırılması

Testleri çalıştırmak için farklı yöntemler mevcuttur. Maven, IDE veya belirli senaryoları hedefleyen komutlar kullanabilirsiniz.

### Maven Kullanarak Testleri Çalıştırma

```bash
mvn clean test
```

### Belirli Feature Dosyasını Çalıştırma

```bash
mvn test -Dcucumber.options="src/test/resources/features/happy-path.feature"
```

### IDE Üzerinden Test Çalıştırma

-   IntelliJ IDEA veya Eclipse gibi IDE’lerde `TestRunner.java` dosyasına sağ tıklayarak **`Run TestRunner`** şeklinde de testleri tetikleyebilirsiniz.
-   Sonuçlar yine `target/surefire-reports/` altında toplanır.


### Tarayıcı Seçimi

-   Projede kullanılan `DriverManager.configureDriverBasedOnTag(scenario)` fonksiyonu, senaryo etiketlerini inceleyerek tarayıcıyı belirler.
-   Örnek etiketler:
    -   `@browser:chrome`
    -   `@browser:firefox`
    -   `@browser:edge`
-   Eğer etiket belirtilmezse (ya da varsayılan belirlenmişse), **Chrome** (veya projede varsayılan tanımlanmış tarayıcı) başlar.

### Mobil Emülasyon (Chrome)

-   `@mobile` etiketi, **Chrome** üzerinde mobil emülasyon modunu aktif eder (örnek olarak iPhone X tanımlı).
-   Firefox veya Edge için mobil emülasyon desteklenmez, `DriverManager` içindeki `setDriver(...)` bunu log'lar.

---

## Allure Raporu Oluşturma

### Maven ile Allure Raporu Üretme

```bash
allure serve allure-results
```
-   Test sonuçlarını birleştirir.
-   **Geçici bir sunucu** açarak raporu **tarayıcıda** otomatik göstermeye çalışır.

### Allure Konfigürasyonu (`pom.xml`)

```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-cucumber7-jvm</artifactId>
    <version>${allure.version}</version>
    <scope>test</scope>
</dependency>
```

```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>${allure-maven.version}</version>
    <configuration>
        <reportVersion>${allure.version}</reportVersion>
    </configuration>
    <executions>
        <execution>
            <id>allure-report</id>
            <phase>verify</phase>
            <goals>
                <goal>aggregate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Lisans

Bu proje [MIT](LICENSE) lisansı ile lisanslanmıştır.


