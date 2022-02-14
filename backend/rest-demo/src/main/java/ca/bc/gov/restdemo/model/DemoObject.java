package ca.bc.gov.restdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * A demo POJO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoObject {

    private UUID id;
    private String name;

}
