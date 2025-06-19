package src.model;

import java.util.Date;

public class Company implements Cloneable {
    private int id;
    private String name;
    private String address;
    private Date dateRegistered;
    private String contactPerson;
    private String mobilePhone;
    private String emailAddress;

    private Company() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public Company clone() {
        try {
            return (Company) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static class Builder {
        private final Company instance = new Company();

        public Builder id(int id) {
            instance.id = id;
            return this;
        }

        public Builder name(String name) {
            instance.name = name;
            return this;
        }

        public Builder address(String address) {
            instance.address = address;
            return this;
        }

        public Builder dateRegistered(Date date) {
            instance.dateRegistered = date;
            return this;
        }

        public Builder contactPerson(String cp) {
            instance.contactPerson = cp;
            return this;
        }

        public Builder mobilePhone(String phone) {
            instance.mobilePhone = phone;
            return this;
        }

        public Builder emailAddress(String email) {
            instance.emailAddress = email;
            return this;
        }

        public Company build() {
            return this.instance;
        }
    }
}