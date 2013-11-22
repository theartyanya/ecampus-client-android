package ua.kpi.campus.serverside;

public final class JSON {
	private final String original;
	
	public JSON(String str){
		this.original = str;
	}
	
	@Override
	public String toString(){
		return original;
	}

}
