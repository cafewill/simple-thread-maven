# simple-thread-maven

This is a simple thread util (Java, Spring Boot) \
 \
유틸로 사용 할 수도 있는 간단한 Java, Spring Boot 쓰레드 예제 입니다. \
최근 대량의 데이터 수집용 멀티 클라이언트를 개발코자 아래와 같이 챗GPT 제안 코드를 참고하여 진행 했습니다 :-) \
 \
질문 #1 : Java Spring 멀티 쓰레드 예제 부탁해 \
질문 #2 : Java Spring 멀티 쓰레드 실행 결과 통합 예제 부탁해 \
질문 #3 : Java Spring 멀티 쓰레드 동시 실행수 10개 제한 예제 부탁해 
 
## Sample Code

* SimpleApplication.workerDemo : 간단한 예제 (질문 #1 참고, SimpleConfigureation @EnableAsync 적용)
* SimpleApplication.resultDemo : 실행 결과 통합 예제 (질문 #2 참고, SimpleConfigureation @EnableAsync 적용)
* SimpleApplication.lottoDemo : 멀티 쓰레드 동시 실행 제한 설정 및 결과 통합 예제 (질문 #2 #3 참고, 로또 데이터 수집 및 통계, SimpleConfigureation @EnableAsync 및 ThreadPoolTaskExecutor 적용)
* SimpleConfigureation : @EnableAsync, ThreadPoolTaskExecutor 를 활용하여 (비동기) 멀티 쓰레드 및 동시 실행 제한 설정

## See Also

* ChatGPT : https://chat.openai.com/
* https://github.com/cafewill/simple-thread-maven
* https://github.com/cafewill/simple-thread-gradle
* https://github.com/cafewill/simple-thread-python
