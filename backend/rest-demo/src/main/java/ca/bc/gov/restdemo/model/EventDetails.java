package ca.bc.gov.restdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Chris.Ditcher
 * Represents event details for display
 * to http clients.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDetails {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="PST")
    private LocalDateTime timestamp;
    private String message;
    private String details;

}
