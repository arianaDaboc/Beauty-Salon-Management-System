package service;
import model.service;
import repository.service_repository;

import java.sql.SQLException;
import java.util.List;
public class service_service {
    private final service_repository repo=new service_repository();
    public void create(service s) throws Exception {
        if (s.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        if (s.getDurationmin() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
        repo.create(s);
    }
    public service findById(int id) throws Exception {
        service s = repo.findById(id);
        if (s == null) throw new Exception("Service not found.");
        return s;
    }
    public List<service> findAll() {
        try {
            return repo.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(service s) throws Exception {
        if (repo.findById(s.getServiceid()) == null) {
            throw new Exception("Service not found.");
        }
        if (s.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        repo.update(s);
    }
    public void delete(int id) throws Exception {
        if (repo.findById(id) == null) {
            throw new Exception("Service not found.");
        }
        repo.delete(id);
    }
}
