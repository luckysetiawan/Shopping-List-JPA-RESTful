package shoppinglist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShoppingListService {
    @Autowired
    private DaftarBelanjaRepo repo;

    public Iterable<DaftarBelanja> getAllData() {
        return repo.findAll();
    }

    public DaftarBelanja getDataByID(long id) {
        Optional<DaftarBelanja> optDB = repo.findById(id);
        if (optDB.isPresent()) {
            return optDB.get();
        }
        else {
            return null;
        }

    }

    public Iterable<DaftarBelanja> getSimilarData(String judul) {
        return repo.findByJudulLike("%" + judul + "%");
    }

    public boolean create(DaftarBelanja entity, DaftarBelanjaDetil[] arrDetil) {
        try {
            // Pertama simpan dahulu objek DaftarBelanja tanpa mengandung detil apapun
            repo.save(entity);

            // Setelah berhasil tersimpan baca primary key auto-generate lalu set sesuai bagian dari
            // ID composite di DaftarBelanjaDetil
            int noUrut = 1;
            for (DaftarBelanjaDetil detil : arrDetil) {
                detil.setId(entity.getId(), noUrut++);
                entity.addDaftarBarang(detil);
            }
            repo.save(entity);

            return  true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public boolean deleteDataByID(long id) {
        try {
            repo.deleteById(id);
            return  true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }
}
