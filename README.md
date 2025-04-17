# YoureDoingWell
## 현재 개발중
## 소개
해당 앱은 오늘 공부한것, 한일들을 관리하고 깃허브, 블로그들을 연동해서 한것들을 보여주는 앱입니다.

## 개발환경
Kotlin : 2.0.0  
Java : Java 17  
gradle : 8.9  
AGP : 8.7.2  
IDE : Android Studio Ladybug (2024.2.1 Patch 2)  

## 기술 스택  
- network
  - Firebase Realtime Database, coroutine
- UI
  - Jetpack Compose, AAC-ViewModel, Coroutine Flows
- DI
  - Hilt
- Test
  - Junit4, mockito-kotlin, Espresso
 
## 기술정보
- Firebase Authentication
  이메일 로그인, 구글 로그인 기능을 구현했습니다.  
  feature:main에서 authentication 정보를 수집하며 다른 feature모듈로는 유저의 정보를 담은 객체를 보내줍니다.
    
