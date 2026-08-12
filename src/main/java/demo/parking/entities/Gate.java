package demo.parking.entities;

import demo.parking.enums.GateStatus;
import demo.parking.enums.GateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private GateType type;
    @Enumerated(EnumType.STRING)
    private GateStatus status;

}
