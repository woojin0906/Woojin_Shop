package kr.spring.exception;
// 상품 주문 수량보다 현재 재고의 수가 적을 때 발생시킬 Exception
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super(message);
    }
}
