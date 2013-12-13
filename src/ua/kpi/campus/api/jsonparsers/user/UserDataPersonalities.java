package ua.kpi.campus.api.jsonparsers.user;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 10.12.13
 * Time: 23:55
 * To change this template use File | Settings | File Templates.
 */
public class UserDataPersonalities extends UserData {
    public ArrayList<Personality> getPersonalities() {
        return personalities;
    }

    private final ArrayList<Personality> personalities;
    public UserDataPersonalities(int userAccountID, String photo, String fullName, Object scientificInterest, ArrayList<Personality> personalities, ArrayList<Profile> profiles) {
        super(userAccountID, photo, fullName, scientificInterest, profiles);
        this.personalities=personalities;
    }
}
