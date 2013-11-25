package ua.kpi.campus.jsonparsers;
/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @version 25 лист. 2013
 */
public class GetPermissionsData {
	private String subsystemName;
	private boolean isCreate;
	private boolean isRead;
	private boolean isUpdate;
	private boolean isDelete;
	
	public GetPermissionsData(String subsystemName, boolean isCreate, boolean isRead,
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
		return "GetPermissionsData [subsystemName=" + subsystemName
				+"\n"+ ", isCreate=" + isCreate +"\n"+ ", isRead=" + isRead
				+"\n"+ ", isUpdate=" + isUpdate +"\n"+ ", isDelete=" + isDelete + "]";
	}

	void setSubsystemName(String subsystemName) {
		this.subsystemName = subsystemName;
	}

	boolean isCreate() {
		return isCreate;
	}
	void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}
	boolean isRead() {
		return isRead;
	}
	void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	boolean isUpdate() {
		return isUpdate;
	}
	void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	boolean isDelete() {
		return isDelete;
	}
	void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
