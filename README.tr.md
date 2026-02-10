# Java Spring Boot Sahtekarlık (Fraud) Tespit Sistemi

[Click here for English Document (İngilizce Belge İçin Tıklayın)](README.md)

Bu mikroservis, gerçek zamanlı bir sahtekarlık (fraud) tespit motoru olarak çalışır. Apache Kafka üzerinden gelen işlem (transaction) mesajlarını tüketir, özel iş kurallarına göre analiz eder ve sonuçları PostgreSQL veritabanına kaydeder.

## Mimari ve Teknolojiler
- **Java 17 & Spring Boot 3:** Temel altyapı.
- **Apache Kafka:** Olay güdümlü asenkron veri işleme.
- **Spring Data JPA & PostgreSQL:** Kalıcı veri saklama ve hesap durumu takibi.
- **Docker Compose:** Konteyner tabanlı altyapı kurulumu.

## İş Kuralları (Fraud Senaryoları)
Bir hesap aşağıdaki durumlarda "Sahtekar (Fraud)" olarak işaretlenir:
1. Tek seferde belirlenen maksimum limitin üzerinde işlem yapmaya çalışması.
2. Bir dakika içerisinde izin verilen maksimum işlem sayısını aşması (Hız/Velocity kontrolü).

Hesap bir kez işaretlendikten sonra, o hesaptan gelen tüm yeni istekler otomatik olarak reddedilir.

## Kurulum ve Çalıştırma

1. Altyapıyı Başlatın:
   ```bash
   docker-compose up -d
   ```
2. Uygulamayı Derleyip Çalıştırın:
   ```bash
   ./mvnw spring-boot:run
   ```

## Test Etme
Kafka'ya sahte bir mesaj yollamak için test uç noktasını kullanın:
```bash
curl -X POST http://localhost:8082/api/test/publish \
     -H "Content-Type: application/json" \
     -d '{"accountId": "ACC-100", "amount": 150.00}'
```
Hız (velocity) kuralını test etmek ve hesabın bloke olduğunu görmek için bu komutu arka arkaya 4 kez hızlıca çalıştırın.
