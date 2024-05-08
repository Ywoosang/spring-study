package ywoosang.springorder.singleton;

public class SingletonService {
    // 자기 자신을 선언한다.
    // 관례상 자기 자신을 내부의 private static 으로 가지고 있는다.
    // 클래스 레벨에 올라가기 때문에 딱 하나만 존재하게 된다.
    // JVM 에 올라갈 때 new SingletonService() 를 내부적으로 실행해서
    // static 변수인 instance 에 그 참조를 넣어둔다.
    // 자기 자신 인스턴스 하나를 계속 사용하게 된다.

    // 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다.
    // private 으로 new 로 인스턴스 생성을 막는다. 다른 곳에서 new 로 생성할 수 없게 된다.
    // 컴파일 오류만으로 웬만한 오류가 잡히도록 하는 것이 객체를 잘 생성한 것이다.

    //
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
