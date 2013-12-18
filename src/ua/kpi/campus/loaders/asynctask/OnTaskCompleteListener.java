package ua.kpi.campus.loaders.asynctask;

public interface OnTaskCompleteListener {
    // Notifies about task completeness
    void onTaskComplete(HttpLoadTask task);
}