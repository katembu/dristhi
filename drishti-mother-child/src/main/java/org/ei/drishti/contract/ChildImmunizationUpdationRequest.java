package org.ei.drishti.contract;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ChildImmunizationUpdationRequest {
    String caseId;
    String anmIdentifier;
    String immunizationsProvided;

    public ChildImmunizationUpdationRequest(String caseId, String anmIdentifier, String immunizationsProvided) {
        this.caseId = caseId;
        this.anmIdentifier = anmIdentifier;
        this.immunizationsProvided = immunizationsProvided;
    }

    public boolean isImmunizationProvided(String checkForThisImmunization) {
        return (" " + immunizationsProvided + " ").contains(" " + checkForThisImmunization + " ");
    }

    public String anmIdentifier() {
        return anmIdentifier;
    }

    public String caseId() {
        return caseId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
