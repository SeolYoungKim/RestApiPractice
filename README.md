# RestApiPractice

지금까지 공부한 REST API 지식을 토대로, 혼자 구현해보는 연습을 한 리포지토리입니다.

추가로, **스프링 부트와 AWS로 혼자 구현하는 웹서비스** 도서의 내용을 응용하였습니다.

## 문제 해결
1. TEXT/HTML type test (test: controller/indexController)
    - 문제 발생 상황
      - "/" get 요청 -> index.html 조회
      - 위의 TEXT_HTML 타입의 응답을 mockMvc로 테스트하고자 함
      - HTML 파일의 body 부에 "스프링 부트로 시작하는 웹 서비스"라는 문자열이 포함되어있는지 검증 로직 작성
        ```       
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("스프링 부트로 시작하는 웹 서비스")))
                .andDo(print());
        ```
    - 발생한 문제
      - HTML 파일 상의 모든 한글이 ???로 표기되는 문제 발생
      - mockMvc의 encoding이 UTF-8로 설정되어있지 않다고 판단.
      - 문제 해결을 위해 구글링 후 아래와 같이 설정하여 해결
      ```    
      @Autowired
      private WebApplicationContext ctx;
      
      @BeforeEach
      public void setup() {
          this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
          .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
          .alwaysDo(print())
          .build();
      }
      ```
2. mustache 화면 출력 시, 한글 깨짐 현상
   - application.yaml 파일에 아래의 설정정보를 추가하여 해결
   ```
   server:
       servlet:
           encoding:
               force-response: true
   ```
   
3. WebSecurityConfigurerAdapter -> deprecated
   - 책 내에서 사용하던 WebSecurityConfigurerAdapter가 deprecated되어 현재 버전에서는 지원되지 않음.
   - 원래는 해당 어댑터를 상속받아 설정을 오버라이딩 하는 방식이었음.
   - 변경 후, 상속받지 않고 모두 Bean으로 등록하는 방식으로 변경 됨
   - 아래와 같이 설정 코드를 변경하여 해결
   ```    
   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
   ```
   
4. 세션 - DB 연동 시, Table이 생성되지 않는 문제
   - 책에서는 H2 database를 사용하였으나, 나는 Mysql을 사용하였다.
   - spring.session.store-type=jdbc로 설정하였었으나, 테이블이 생성되지 않아 오류 페이지가 뜨는 문제가 발생함.
   - Mysql 사용 시, 아래의 정보를 추가적으로 기입해주어야 한다. 
     - initialize-schema : 기본 값이 `embedded`이기 때문에, embedded DB가 아니라면 아래 두개의 세션 관련 테이블이 자동 생성되지 않는다.
       - SPRING_SESSION, SPRING_SESSION_ATTRIBUTES
   - [참고 1. spring.session.jdbc.initialize-schema 옵션](https://smpark1020.tistory.com/212)
   - [참고 2. Spring Session with JDBC](https://www.javadevjournal.com/spring/spring-session-with-jdbc/)
   ```  
   session:
       jdbc:
           initialize-schema: always  //always - 자동 테이블 생성 || never - 수동으로 세션 테이블 생성
           table-name: SPRING_SESSION
       store-type: jdbc
   ```