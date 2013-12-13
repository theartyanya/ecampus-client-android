package ua.kpi.campus.api.jsonparsers.user;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 09.12.13
 * Time: 0:46
 * To change this template use File | Settings | File Templates.
 */
public class Profile {
    private final String subSystemName;
    private final boolean isCreate;
    private final boolean isRead;
    private final boolean isUpdate;
    private final boolean isDelete;

    @Override
    public String toString() {
        return subSystemName +                           "\n" +
                "isCreate=" + isCreate +                 "\n" +
                "isRead=" + isRead +                     "\n" +
                "isUpdate=" + isUpdate +                 "\n" +
                "isDelete=" + isDelete;
    }

    public String getSubSystemName() {
        return subSystemName;
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

    public Profile(String subSystemName, boolean create, boolean read, boolean update, boolean delete) {
        this.subSystemName = subSystemName;
        isCreate = create;
        isRead = read;
        isUpdate = update;
        isDelete = delete;

    }
}
