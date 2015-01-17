package ua.kpi.campus.model;

import java.io.Serializable;

/**
 * Created by Admin on 17.01.2015.
 */
public class AboutItem implements Serializable {
    
    private String OPTION_NAME;
    private String OPTION_DESCRIPTION;


    public String getOptionName() {
        return OPTION_NAME;
    }

    public AboutItem setOptionName(String OPTION_NAME) {
        this.OPTION_NAME = OPTION_NAME;
        return this;
    }

    public String getOptionDescription() {
        return OPTION_DESCRIPTION;
    }

    public void setOptionDescription(String OPTION_DESCRIPTION) {
        this.OPTION_DESCRIPTION = OPTION_DESCRIPTION;
    }
}
