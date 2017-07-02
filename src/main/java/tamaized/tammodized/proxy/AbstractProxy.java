package tamaized.tammodized.proxy;

public abstract class AbstractProxy {

	protected static enum Side {
		CLIENT, SERVER
	}

	private final Side side;

	public AbstractProxy(Side side) {
		this.side = side;
	}

	public abstract void preRegisters();

	public abstract void preInit();

	public abstract void init();

	public abstract void postInit();

}
