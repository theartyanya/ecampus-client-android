package ua.kpi.campus.api.jsonparsers;
/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 25 Nov 2013
 */
public final class PermissionsData {
	private final String subsystemName;
	private final boolean isCreate;
	private final boolean isRead;
	private final boolean isUpdate;
	private final boolean isDelete;
	
	public PermissionsData(String subsystemName, boolean isCreate, boolean isRead,
                           boolean isUpdate, boolean isDelete) {
		this.subsystemName=subsystemName;
		this.isCreate = isCreate;
		this.isRead = isRead;
		this.isUpdate = isUpdate;
		this.isDelete = isDelete;
	}
	
	String getSubsystemName() {
		return subsystemName;
	}

	@Override
	public String toString() {
		return "PermissionsData [subsystemName=" + subsystemName
				+"\n"+ ", isCreate=" + isCreate +"\n"+ ", isRead=" + isRead
				+"\n"+ ", isUpdate=" + isUpdate +"\n"+ ", isDelete=" + isDelete + "]";
	}

	boolean isCreate() {
		return isCreate;
	}

	boolean isRead() {
		return isRead;
	}

	boolean isUpdate() {
		return isUpdate;
	}

	boolean isDelete() {
		return isDelete;
	}

}
