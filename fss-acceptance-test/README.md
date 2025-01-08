Run acceptance test
---

Note, by default, it runs against the local server at http://localhost:8080
in order to target a different server, set the environment variable `SPRING_PROFILE`

```shell
export SPRING_PROFILE=local
```

by default it runs management tests

```shell
./run-tests.sh 
```

to run all tests

```shell
./run-tests.sh  --tags "\"@test-init  or  @create-account or @scale\""
```


Publish test report
---

```shell
 ./publish-test-results.sh
```

Run scalability test
---

```shell
./run-tests.sh --tags "\"@test-init  or @scale\""
```

or some arbitrary tags
```shell
./run-tests.sh  --tags "\"@test-init  or  @games-management\""
```

Debug on port 7000
---

```shell

./run-tests.sh debug 

```
