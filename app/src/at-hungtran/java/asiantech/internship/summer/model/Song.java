package asiantech.internship.summer.model;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

public class Song {
    private String title;
    private int fileName;
    private int cdImage;
    private String singer;

    private Song(String title, int fileName, int cdImage, String singer) {
        this.title = title;
        this.fileName = fileName;
        this.cdImage = cdImage;
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFileName() {
        return fileName;
    }

    public void setFileName(int fileName) {
        this.fileName = fileName;
    }

    public int getCdImage() {
        return cdImage;
    }

    public void setCdImage(int cdImage) {
        this.cdImage = cdImage;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public static List<Song> listSong(){
        List<Song> listSongs = new ArrayList<>();
        listSongs.add(new Song("Đừng như thói quen", R.raw.dung_nhu_thoi_quen, R.drawable.img_jakii_hansara, "Jaykii and Sara"));
        listSongs.add(new Song("Cô gái nhà bên", R.raw.co_gai_nha_ben, R.drawable.img_jun_pham, "Jun Phạm"));
        listSongs.add(new Song("Mình yêu nhau đi", R.raw.minh_yeu_nhau_di, R.drawable.img_bich_phuong, "Bích Phương"));
        listSongs.add(new Song("Sau tất cả", R.raw.sau_tat_ca, R.drawable.img_erik, "Ẻrik"));
        listSongs.add(new Song("Tháng tư là lời nói dối của em", R.raw.thang_tu_la_loi_noi_doi_cua_em, R.drawable.img_ha_anh_tuan, "Hà Anh Tuấn"));
        return listSongs;
    }
}
