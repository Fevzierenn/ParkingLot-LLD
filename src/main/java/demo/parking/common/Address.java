package demo.parking.common;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String district;
    private String street;
    private String postalCode;

    protected Address() {
    }

    public Address(String city, String district, String street, String postalCode) {
        this.city = city;
        this.district = district;
        this.street = street;
        this.postalCode = postalCode;
    }

    // getters
}
