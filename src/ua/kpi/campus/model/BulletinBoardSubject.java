package ua.kpi.campus.model;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public final class BulletinBoardSubject {
    private final String text;
    private final long dateCreate;
    private final int creatorUserAccountId;
    private final String creatorUserAccountFullname;
    private final int bulletinBoardSubjectId;
    private final int bulletinBoardId;
    private final String bulletinBoardLinkRecipients;
    private final String subject;

    public BulletinBoardSubject(String text, long dateCreate, int creatorUserAccountId, String creatorUserAccountFullname, int bulletinBoardSubjectId, int bulletinBoardId, String bulletinBoardLinkRecipients, String subject) {

        this.text = text;
        this.dateCreate = dateCreate;
        this.creatorUserAccountId = creatorUserAccountId;
        this.creatorUserAccountFullname = creatorUserAccountFullname;
        this.bulletinBoardSubjectId = bulletinBoardSubjectId;
        this.bulletinBoardId = bulletinBoardId;
        this.bulletinBoardLinkRecipients = bulletinBoardLinkRecipients;
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public int getCreatorUserAccountId() {
        return creatorUserAccountId;
    }

    public String getCreatorUserAccountFullname() {
        return creatorUserAccountFullname;
    }

    public int getBulletinBoardSubjectId() {
        return bulletinBoardSubjectId;
    }

    public int getBulletinBoardId() {
        return bulletinBoardId;
    }

    public String getBulletinBoardLinkRecipients() {
        return bulletinBoardLinkRecipients;
    }

    public String getSubject() {
        return subject;
    }
}
