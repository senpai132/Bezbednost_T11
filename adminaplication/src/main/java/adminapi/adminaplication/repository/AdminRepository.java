package adminapi.adminaplication.repository;

import adminapi.adminaplication.model.Admin;

public interface AdminRepository {
    Admin findByUsername(String username);
}
