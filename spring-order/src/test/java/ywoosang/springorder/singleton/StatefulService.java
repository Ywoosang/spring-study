package ywoosang.springorder.singleton;

public class StatefulService {
    private int price; // 상태를 유지하는 필드

    // 클라이언트가 겂울 변경할 수 있는 필드가 있으면 안된다.
    // 특정 클라이언트가 price 의 값을 변경하면 이로인해 문제가 발생한다.
    // 가급적 읽기만 가능해야 한다.
    // 스프링 빈은 항상 무상태 (stateless) 로 설계한다.
    // 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
    // 스프링 빈의 필드에 공유 값을 설정하면 큰 장애가 발생할 수 있다.
    public void order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);
        this.price = price;
        // 이 코드 대신에 return price; 로 변경한다.
        // int userAPrice = statefulService1.order(); 처럼 사용한다.
    }

    public int getPrice() {
        return price;
    }
}
