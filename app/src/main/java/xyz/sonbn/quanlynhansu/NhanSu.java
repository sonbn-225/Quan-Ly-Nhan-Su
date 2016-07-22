package xyz.sonbn.quanlynhansu;

/**
 * Created by SonBN on 7/14/2016.
 */
public class NhanSu {
    private int id;
    private String name, birthday, address, phone, email, image;

    public NhanSu(){}

    public NhanSu(String name, String birthday, String address, String phone, String email, String image){
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    //Ham voi id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Ham voi Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Ham voi Age
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    //Ham voi Address
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    //Ham voi PhoneNumber
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Ham voi email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //Ham voi Image
    public String getImage() {
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }
}
