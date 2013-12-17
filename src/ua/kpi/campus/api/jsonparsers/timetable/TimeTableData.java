package ua.kpi.campus.api.jsonparsers.timetable;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serhii Hokhalenko
 * Date: 17.12.13
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
public class TimeTableData {
    private String name;
    private ArrayList<Parameter> parameters;

    public TimeTableData(String name, ArrayList<Parameter> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
    }
}
