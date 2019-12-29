package model;

public class Film {
    private Integer id;
    private String name;
    private Integer duration;
    private Integer ticketPrice;

    private Film(Builder builder) {
        id = builder.id;
        name = builder.name;
        duration = builder.duration;
        ticketPrice = builder.ticketPrice;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                "name=" + name +
                ", duration='" + duration +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Integer duration;
        private Integer ticketPrice;

        private Builder() {}

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder duration(Integer val) {
            duration = val;
            return this;
        }

        public Builder ticketPrice(Integer val) {
            ticketPrice = val;
            return this;
        }

        public Film build() {
            return new Film(this);
        }
    }
}
