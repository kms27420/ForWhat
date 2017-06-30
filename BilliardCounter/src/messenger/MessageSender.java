package messenger;

/**
 * 클래스 간의 정보 전달을 위한 인터페이스, 그 중 정보를 전달하는 인터페이스
 * @author Kwon
 *
 */
public interface MessageSender {
	/**
	 * 정보를 전달해주는 매서드
	 * @param messenger, 정보를 받을 인터페이스를 구현한 객체
	 * @param message, 전달할 정보
	 */
	public abstract void sendMessage(MessageGetter messenger, Object message);
}
