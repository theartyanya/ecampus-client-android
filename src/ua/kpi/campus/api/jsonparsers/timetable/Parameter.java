package ua.kpi.campus.api.jsonparsers.timetable;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class Parameter {
    final String name;
    final String type;

    public Parameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
