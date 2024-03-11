

run utilities
---

  mvn test-compile package exec:java -DskipTests -Dexec.mainClass=<full-class-name> -Dexec.classpathScope="test"

for example to run import sample data we will run

```shell
mvn test-compile package exec:java -DskipTests -Dexec.mainClass=com.kgignatyev.fss.service.utils.ImportTestData -Dexec.classpathScope="test"
```



