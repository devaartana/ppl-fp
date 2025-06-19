package src.model;

import java.util.Date;

public class Participant implements Cloneable {
    private int id;
    private String name;
    private String address;
    private Date dateCreated;
    private String mobilePhone;
    private String emailAddress;
    private int companyId;

    private Participant() {
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public Participant clone() {
        try {
            return (Participant) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public static class Builder {
        private final Participant instance = new Participant();

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

        public Builder dateCreated(Date date) {
            instance.dateCreated = date;
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

        public Builder companyId(int id) {
            instance.companyId = id;
            return this;
        }

        public Participant build() {
            return this.instance;
        }
    }
}