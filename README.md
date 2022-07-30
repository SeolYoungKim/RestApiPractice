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
   