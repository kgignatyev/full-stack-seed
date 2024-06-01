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
