package com.example.donor_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContactDetails {

    @Id
    private Long contactId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    @JsonIgnoreProperties("contactDetails")
    private Donor donor;

    private String phone1;
    private String phone2;
}
