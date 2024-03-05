FROM amazoncorretto:21.0.0
ARG APP_VERSION
COPY target/ladder-service-${APP_VERSION}.jar /app/ladder-service.jar

ENV HZ_NETWORK_JOIN_MULTICAST_ENABLED=true
ENV HZ_NETWORK_JOIN_KUBERNETES_ENABLED=false

WORKDIR /app
EXPOSE 8080
CMD ["java", \
 "--add-modules"  ,"java.se", \
 "--add-exports" ,"java.base/jdk.internal.ref=ALL-UNNAMED", \
 "--add-opens"   ,"java.base/java.lang=ALL-UNNAMED", \
 "--add-opens"   ,"java.base/java.nio=ALL-UNNAMED", \
 "--add-opens"   ,"java.base/sun.nio.ch=ALL-UNNAMED", \
 "--add-opens"   ,"java.management/sun.management=ALL-UNNAMED", \
 "--add-opens"   ,"jdk.management/com.sun.management.internal=ALL-UNNAMED", \
 "-jar","/app/ladder-service.jar" \
 ]

