package approaches.activity;

public class Client extends Entity {
	
	public Type type;
	public boolean isInCashPayment;
	
	public static enum Type {
		PAYMENT, PLAY, PAYMENT_AND_PLAY;
	}
	
	public Client (double arrive) {
		this.arrive = start = end = arrive;
	}
	
}
