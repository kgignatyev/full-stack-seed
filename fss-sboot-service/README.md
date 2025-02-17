run in development mode
---
```shell    
mvn spring-boot:run 
```
The application will be running on http://localhost:8080
Spring development utilities are enabled
and therefore the application will reload on changes. To trigger a reload simply 
modify source code and run compilation.

```shell
 mvnd package -DskipTests
```
Note that *mvn* command can be used but *mvnd* will be faster because it maintains a daemon process.


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
  mvn  test -Dtest=ArchUnitTest
```

Verify "modulith" mudularity and produce documentation
```shell
   mvn  test -Dtest=ModularityTest
```
Check for errors and look at the produced documentation in the target/spring-modulith-docs
directory (plantUML and adoc IntellJ plugins are very helpful here ).

![modulith-report.png](docs/modulith-report.png)
