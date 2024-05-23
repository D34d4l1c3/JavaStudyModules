package org.example.iteration5.clientExporter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Contact {
    Long id;
    String name;
    String lastName;
    String email;

    @Override
    public String toString() {
        return "|" + id +"|" + name + "|" + lastName + "|" + email + '|'+'\n';
    }
}
