# Linguist-bot

Развертывание приложения на [Railway](https://railway.com/).

Шаг 1. Дополнить pom.xml. Ниже представлено то место, которое у нас изменилось. Этот плагин собирает один исполняемый JAR-файл со всеми зависимостями и указывает, с какого класса начать запуск (pack.Main).

```.xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.5.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals><goal>shade</goal></goals>
            <configuration>
                <transformers>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>pack.Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Шаг 2. В командной строке набираем mvn clean package, если сборка прошла успешно, значит, бот готов к деплою.

Шаг 3. Пушим на гитхаб конечную версию кода.

Шаг 4. Заходим на [Railway](https://railway.com/). Регистрируемся на гитхаб, даем доступ к репозиторию с ботом. Когда репозиторий там появится, выбираем его, переходим на вкладку Variables. Там добавим две переменные: 

BOT_TOKEN =\
BOT_USERNAME =

После этого проект будет собираться и деплоиться, если все прошло успешно, появится зеленая надпись ACTIVE. Если деплой не получится, можно будет нажать на вкладку Deploy Logs, посмотреть ошибки и исправить их.
