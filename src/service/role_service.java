package service;
import model.role;
import repository.role_repository;
import java.sql.SQLException;
import java.util.List;
public class role_service {
    private final role_repository repo = new role_repository();
    public void create(role r) throws Exception {
        if (r.getrolename() == null || r.getrolename().isBlank()) {
            throw new IllegalArgumentException("Role name cannot be empty.");
        }
        repo.create(r);
    }
    public role findById(int id) throws Exception {
        role r = repo.findById(id);
        if (r == null) throw new Exception("Role not found.");
        return r;
    }
    public List<role> findAll() {
        try {
            return repo.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(role r) throws Exception {
        if (repo.findById(r.getroleid()) == null) {
            throw new Exception("Role not found.");
        }
        repo.update(r);
    }
    public void delete(int id) throws Exception {
        if (repo.findById(id) == null) {
            throw new Exception("Role not found.");
        }
        repo.delete(id);
    }
}
