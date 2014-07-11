package dny.util;

public interface Wrapper<Core> {

    Core getCore();
	
	public static class ConcreteWrapper<Core> implements Wrapper<Core> {
		
		private final Core core;
		
		@Override public Core getCore() {
			return core;
			}
		
		public ConcreteWrapper(Core core) {
			this.core = core;
		}
		
	}
	
	public static interface MutableWrapper<Core> extends Wrapper<Core> {
		
		void setCore(Core core);
		
		public static class ConcreteMutableWrapper<Core> implements MutableWrapper<Core> {

			private Core core;

			@Override public Core getCore() {
				return core;
			}
			
			@Override public void setCore(Core core) {
				this.core = core;
			}

			public ConcreteMutableWrapper() {}
			
			public ConcreteMutableWrapper(Core core) {
				this.core = core;
			}
			
			
		}
		
	}
	

}
