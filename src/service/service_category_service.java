package service;
import model.service_category;
import repository.service_category_repository;

import java.sql.SQLException;
import java.util.List;
public class service_category_service {
    private final service_category_repository repo = new service_category_repository();
    public void create(service_category c) throws Exception {
        if (c.getCategory_name() == null || c.getCategory_name().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        List<service_category> all = repo.findAll();
        for (service_category existing : all) {
            if (existing.getCategory_name().equalsIgnoreCase(c.getCategory_name())) {
                throw new Exception("Category already exists.");
            }
        }
        repo.create(c);
    }
    public service_category findById(int id) throws Exception {
        service_category c = repo.findById(id);
        if (c == null) throw new Exception("Category not found.");
        return c;
    }
    public List<service_category> findAll() {
        try {
            return repo.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(service_category c) throws Exception {
        if (repo.findById(c.getCategory_id()) == null) {
            throw new Exception("Category does not exist.");
        }
        if (c.getCategory_name() == null || c.getCategory_name().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        repo.update(c);
    }
    public void delete(int id) throws Exception {
        if (repo.findById(id) == null) {
            throw new Exception("Category not found.");
        }
        repo.delete(id);
    }
}
