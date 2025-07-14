Before you start
---

Run environment check script
```shell
./utils/environment-check.sh
```
Once you have all the tools installed, please run 
```shell
./utils/bootstrap.sh
```
to setup initial configuration, then verify that DB connection and credentials are correct

run in development mode
---
```shell    
mvn spring-boot:run 
```
The application will be running on http://localhost:8080
Spring development utilities are enabled
and therefore the application will reload on changes. To trigger a reload simply 
modify source code and run compilation.

Note that *mvn* command can be used but *mvnd* will be faster because it maintains a daemon process.
https://github.com/apache/maven-mvnd?tab=readme-ov-file#install-using-homebrew

```shell
 mvnd package -DskipTests
```



Run tests
---
```shell
 mvnd test [-Dtest=TestClassName]
```

to run tests in debug mode
```shell
 mvnd test -Dmaven.surefire.debug [-Dtest=TestClassName]
```

Architecture enforcement
---

Verify that code structure conforms to the chosen standards (see https://www.archunit.org/ for details): 
```shell
  mvnd  test -Dtest=ArchUnitTest
```

Verify "modulith" mudularity and produce documentation
```shell
   mvnd  test -Dtest=ModularityTest
```
Check for errors and look at the produced documentation in the target/spring-modulith-docs
directory (plantUML and adoc IntellJ plugins are very helpful here ).

![modulith-report.png](docs/modulith-report.png)
