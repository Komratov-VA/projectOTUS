mySQL(докер)

docker запуск на 3307
docker run -p 3307:3306 --name=mysqlOtus -d mysql/mysql-server:8.0.21

смотрим логи для пароля 
docker logs mysqlOtus

подключаемся
docker exec -it mysqlOtus mysql -uroot -p    

меняем пароль 
ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
чтобы подключаться с localhost к докеру нужно изменить конфиг mysql(0.0.0.0)
или сделать
CREATE USER 'myuser'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'myuser'@'%';

подключаемся с помощью myuser из idea к докер образу
