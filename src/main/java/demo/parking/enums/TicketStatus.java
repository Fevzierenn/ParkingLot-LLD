package demo.parking.enums;

public enum TicketStatus {
    ACTIVE,
    PARKED,
    PAYMENT_PENDING,
    EXPIRED, //EXPIRED means ticket payment is successful and ticket is no longer valid
}
