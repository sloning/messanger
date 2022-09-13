# API для мессенджера

Простейщий мессенджер с elastic search'ем.

Приложение создано с использованием:

* Spring Boot
* Spring Data
* OpenAPI
* PostgreSQL
* ElasticSearch (будет заменено)
* Docker
* Jenkins

## Сборка и запуск

Запустить базы данных:

```
docker-compose up -d
```

Запустить приложение:

```
./gradlew bootJar docker dockerRun
```
