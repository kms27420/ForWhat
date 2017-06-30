package messenger;

/**
 * 클래스 간의 정보 전달을 위한 인터페이스, 그 중 정보를 받을 수 있는 인터페이스
 * @author Kwon
 *
 */
public interface MessageGetter {
	/**
	 * 정보를 전달 받는 매서드
	 * @param message, 전달 받는 정보
	 */
	public abstract void getMessage(Object message);
}
