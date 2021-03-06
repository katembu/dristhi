package org.ei.drishti.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.LocalDate;
import org.motechproject.model.MotechBaseDataObject;

import java.text.MessageFormat;

@TypeDiscriminator("doc.type === 'Mother'")
public class Mother extends MotechBaseDataObject {
    @JsonProperty
    private String caseId;
    @JsonProperty
    private String thaayiCardNumber;
    @JsonProperty
    private String name;
    @JsonProperty
    private String anmPhoneNumber;
    @JsonProperty
    private LocalDate lmp;

    private Mother() {
    }

    public Mother(String caseId, String thaayiCardNumber, String name) {
        this.caseId = caseId;
        this.thaayiCardNumber = thaayiCardNumber;
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Mother withAnmPhoneNumber(String anmPhoneNumber) {
        this.anmPhoneNumber = anmPhoneNumber;
        return this;
    }

    public Mother withLMP(LocalDate lmp) {
        this.lmp = lmp;
        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Mother: {0} ({1}) [Case ID: {2}, ANM: {3}]", name, thaayiCardNumber, caseId, anmPhoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mother mother = (Mother) o;

        if (!caseId.equals(mother.caseId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return caseId.hashCode();
    }

    private String getCaseId() {
        return caseId;
    }

    public String thaayiCardNo() {
        return thaayiCardNumber;
    }

    public String anmPhoneNo() {
        return anmPhoneNumber;
    }
}
