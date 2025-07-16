# SpringBoot25
스프링 부트 학습용

=================application.properties=================

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.datasource.url=jdbc:mariadb://localhost:3306/??????

spring.datasource.username=????????

spring.datasource.password=????????


spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.format_sql=true

spring.jpa.show-sql=true

=================build.Gradle=================
/*메이븐 리포지토리에서 코드를 가져와 코끼리를 누르면 가져온다.*/

implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'     /*프론트 관련*/

implementation 'org.springframework.boot:spring-boot-starter-web'           /*string-web*/


compileOnly 'org.projectlombok:lombok'                                      /*lombok*/

annotationProcessor 'org.projectlombok:lombok'                              /*lombok*/

testCompileOnly 'org.projectlombok:lombok'                                  /*lombok*/

testAnnotationProcessor 'org.projectlombok:lombok'                          /*lombok*/

developmentOnly 'org.springframework.boot:spring-boot-devtools'             /*boot 개발용*/


/*1,2단계 설정 -> src/main/resources/application.properties 에서 설정함*/

runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'                          /*마리아 db 드라이버*/

implementation 'org.springframework.boot:spring-boot-starter-data-jpa'      /*데이터 베이스 관련*/


testImplementation 'org.springframework.boot:spring-boot-starter-test'      /*테스트 junit 메서드 단위 테스트*/

testRuntimeOnly 'org.junit.platform:junit-platform-launcher'                /*junit용 코드*/

