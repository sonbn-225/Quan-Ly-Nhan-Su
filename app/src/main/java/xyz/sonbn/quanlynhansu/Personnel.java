package xyz.sonbn.quanlynhansu;

/**
 * Created by SonBN on 7/13/2016.
 */
public class Personnel {
    private int id;
    private String name, age, address, phone, email;
    public Personnel(){}

    public Personnel(String name,String age, String address, String phonenumber, String email) {
        super();
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phonenumber;
        this.email = email;
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
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
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

}

