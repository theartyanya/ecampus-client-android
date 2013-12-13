package ua.kpi.campus.api.jsonparsers.user;
/**
 * 
 * 
 * @author Serhii Hokhalenko
 * @author Artur Dzidzoiev
 * @version 04 Dec 2013
 */
public final class SubsystemData {
	private final String subsystemName;
	private final boolean isCreate;
	private final boolean isRead;
	private final boolean isUpdate;
	private final boolean isDelete;
	
	public SubsystemData(String subsystemName, boolean isCreate, boolean isRead,
                         boolean isUpdate, boolean isDelete) {
		this.subsystemName=subsystemName;
		this.isCreate = isCreate;
		this.isRead = isRead;
		this.isUpdate = isUpdate;
		this.isDelete = isDelete;
	}

    public String getSubsystemName() {
        return subsystemName;
    }

    public boolean isCreate() {
		return isCreate;
	}

    public boolean isRead() {
		return isRead;
	}

    public boolean isUpdate() {
		return isUpdate;
	}

    public boolean isDelete() {
		return isDelete;
	}

    @Override
    public String toString() {
        return "SubsystemData [subsystemName=" + subsystemName
                +"\n"+ ", isCreate=" + isCreate +"\n"+ ", isRead=" + isRead
                +"\n"+ ", isUpdate=" + isUpdate +"\n"+ ", isDelete=" + isDelete + "]";
    }
}
