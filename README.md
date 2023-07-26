# git하는 방법 

1. 로컬에서 브랜치 생성
    1. 브랜치명
        1. **{label tag}/개발내용**
           ex) feature/login_domain
2. 개발한다
3. 로컬의 브랜치 원격 브랜치(깃허브)에 push
4. Pull Request 생성 + 내용 작성
 **Squash and Merge**
 **Remote Branch 삭제**
5. Remote develop 브랜치 pull 받기
6. 로컬 작업 브랜치 삭제 (git branch -D {branch})

1~6 반복

git flow
develop과 main부분을 직접적으로 건들이는 개발자가 없기 때문에 충돌이 일어나지 않습니다.
두 명 이상의 개발자와 함께 새로운 기능을 개발할 때 유용합니다.
그렇기 때문에 우리는 feature 브랜치에 코드를 작성합니다.
