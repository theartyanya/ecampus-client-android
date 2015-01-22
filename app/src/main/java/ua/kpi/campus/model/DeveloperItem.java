package ua.kpi.campus.model;

import java.io.Serializable;

/**
 * Created by Admin on 22.01.2015.
 */
public class DeveloperItem implements Serializable {
    
    private String OPTION_NAME;
    private String OPTION_DESCRIPTION;
    private int PHOTO;

    public String getOptionName() {
        return OPTION_NAME;
    }

    public DeveloperItem setOptionName(String OPTION_NAME) {
        this.OPTION_NAME = OPTION_NAME;
        return this;
    }

    public String getOptionDescription() {
        return OPTION_DESCRIPTION;
    }

    public DeveloperItem setOptionDescription(String OPTION_DESCRIPTION) {
        this.OPTION_DESCRIPTION = OPTION_DESCRIPTION;
        return this;
    }

    public int getPhoto() {
        return PHOTO;
    }

    public void setPhoto(int PHOTO) {
        this.PHOTO = PHOTO;
    }
}
