package shoppinglist.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.service.ShoppingListService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
public class ShoppingListCtrl {
    @Autowired
    private ShoppingListService service;
    /**
     * Mengembalikan daftar obejek DaftarBelanja utk pengaksesan HTTP GET.
     */
    @GetMapping
    public Iterable<DaftarBelanja> getAll() {
        return service.getAllData();
    }

    // Membaca sebuah objek DaftarBelanja berdasarkan ID
    @GetMapping("/daftar/{id}")
    public DaftarBelanja getByID(@PathVariable long id) {
        return service.getDataByID(id);
    }

    // Mencari daftar DaftarBelanja berdasarkan kemiripan string judul yg diberikan.
    @GetMapping("/daftar/{judul}")
    public Iterable<DaftarBelanja> getSimilar(@PathVariable String judul) {
        return service.getSimilarData(judul);
    }

    // Menyimpan sebuah objek DaftarBelanja ke tabel database
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ShoppingDataCreateDto json) {
        // Ubah data yg terkanung dlm JSON ke dalam objek yg bisa diterima oleh service.

        DaftarBelanja entity = new DaftarBelanja();
        entity.setJudul(json.getJudul());

        // Ubah java.util.Date ke LocalDateTime
        LocalDateTime tglLocalDateTime = json.getTanggal().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        entity.setTanggal(tglLocalDateTime);

        List<ShoppingDataCreateDto.DataBarang> listDatabarang = json.getListbarang();
        DaftarBelanjaDetil[] arrDetilBelanja = new DaftarBelanjaDetil[listDatabarang.size()];


        for (int i = 0; i < listDatabarang.size(); i++) {
            arrDetilBelanja[i] = new DaftarBelanjaDetil();

            arrDetilBelanja[i] = new DaftarBelanjaDetil();
            arrDetilBelanja[i].setByk(listDatabarang.get(i).getByk());
            arrDetilBelanja[i].setMemo(listDatabarang.get(i).getMemo());
            arrDetilBelanja[i].setNamaBarang(listDatabarang.get(i).getNama());
            arrDetilBelanja[i].setSatuan(listDatabarang.get(i).getSatuan());
        }

        if(service.create(entity, arrDetilBelanja))
            return  ResponseEntity.ok("Data tersimpan dengan ID: " + entity.getId());
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data gagal tersimpan");
    }

    // Mengupdate sebuah objek DaftarBelanja ke tabel database
    @PutMapping("/update/{id}")
    public ResponseEntity<String> udpate(@RequestBody ShoppingDataCreateDto json, @PathVariable long id) {
        // Ubah data yg terkanung dlm JSON ke dalam objek yg bisa diterima oleh service.

        DaftarBelanja entity = new DaftarBelanja();
        entity.setId(id);
        entity.setJudul(json.getJudul());

        // Ubah java.util.Date ke LocalDateTime
        LocalDateTime tglLocalDateTime = json.getTanggal().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        entity.setTanggal(tglLocalDateTime);

        List<ShoppingDataCreateDto.DataBarang> listDatabarang = json.getListbarang();
        DaftarBelanjaDetil[] arrDetilBelanja = new DaftarBelanjaDetil[listDatabarang.size()];


        for (int i = 0; i < listDatabarang.size(); i++) {
            arrDetilBelanja[i] = new DaftarBelanjaDetil();

            arrDetilBelanja[i] = new DaftarBelanjaDetil();
            arrDetilBelanja[i].setByk(listDatabarang.get(i).getByk());
            arrDetilBelanja[i].setMemo(listDatabarang.get(i).getMemo());
            arrDetilBelanja[i].setNamaBarang(listDatabarang.get(i).getNama());
            arrDetilBelanja[i].setSatuan(listDatabarang.get(i).getSatuan());
        }

        if(service.create(entity, arrDetilBelanja))
            return  ResponseEntity.ok("Data tersimpan dengan ID: " + entity.getId());
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data gagal tersimpan");
    }

    // Menghapus objek DaftarBelanja berdasarkan ID yg diberikan
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable long id) {
        if(service.deleteDataByID(id))
            return ResponseEntity.ok("Data dengan ID: ");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data gagal dihapus");
    }

}
