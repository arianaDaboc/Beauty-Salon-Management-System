package ui.session;

import model.user;

public class Session {

    private static user currentUser;
    private static Integer selectedServiceId;

    public static user getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(user user) {
        currentUser = user;
    }

    public static void clear() {
        currentUser = null;
        selectedServiceId = null;
    }
    public static void setSelectedServiceId(int serviceId) {
        selectedServiceId = serviceId;
    }

    public static Integer getSelectedServiceId() {
        return selectedServiceId;
    }
}
