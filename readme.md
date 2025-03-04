# Тестовое задание в isands

### Запуск приложения

```bash
docker compose build
docker compose up
```

Если выдается ошибка <code>"/bin/sh: 1: ./mvnw: not found"</code>, то необходимо вручную поменять line separator у файла <code>estore-microservice/mvnw</code> с <code>CRLF</code> на <code>LF</code>.
