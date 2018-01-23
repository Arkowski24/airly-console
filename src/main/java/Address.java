import java.util.Objects;

public class Address {
    public final String country;
    public final String locality;
    public final String route;
    public final String streetNumber;

    public Address(String country, String locality, String route, String streetNumber) {
        this.country = country;
        this.locality = locality;
        this.route = route;
        this.streetNumber = streetNumber;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) &&
                Objects.equals(locality, address.locality) &&
                Objects.equals(route, address.route) &&
                Objects.equals(streetNumber, address.streetNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, locality, route, streetNumber);
    }

    @Override
    public String toString() {
        return country + " " + locality + " " + route + " " + streetNumber;
    }
}
