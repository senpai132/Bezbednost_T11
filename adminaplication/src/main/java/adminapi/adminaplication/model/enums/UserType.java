package adminapi.adminaplication.model.enums;

public enum UserType {
    DOCTOR {
        @Override
        public String toString() {
            return "doctor";
        }
    }, HOSPITAL_ADMIN {
        @Override
        public String toString() {
            return "hospital admin";
        }
    }, SUPER_ADMIN {
        @Override
        public String toString() {
            return "super admin";
        }
    }
}
