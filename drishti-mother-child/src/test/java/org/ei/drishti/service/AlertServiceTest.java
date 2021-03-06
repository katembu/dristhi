package org.ei.drishti.service;

import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.domain.AlertData;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllAlertActions;
import org.ei.drishti.repository.AllMothers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AlertServiceTest {
    @Mock
    private AllAlertActions allAlertActions;
    @Mock
    private AllMothers allMothers;
    private AlertService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new AlertService(allAlertActions, allMothers);
    }

    @Test
    public void shouldSaveAlertActionForMother() throws Exception {
        when(allMothers.findByCaseId("Case X")).thenReturn(new Mother("Case X", "Thaayi 1", "Theresa").withAnmPhoneNumber("ANM phone no"));

        DateTime dueDate = DateTime.now().minusDays(1);
        service.alertForMother("Case X", "ANC 1", "due", dueDate);

        verify(allAlertActions).add(new AlertAction("Case X", "ANM phone no", AlertData.create("Theresa", "Thaayi 1", "ANC 1", "due", dueDate)));
    }

    @Test
    public void shouldSaveAlertActionForChild() throws Exception {
        DateTime dueDate = DateTime.now().minusDays(1);

        service.alertForChild("Case X", "Child 1", "DEMO ANM", "TC 1", "OPV 1", "due", dueDate);

        verify(allAlertActions).add(new AlertAction("Case X", "DEMO ANM", AlertData.create("Child 1", "TC 1", "OPV 1", "due", dueDate)));
    }

    @Test
    public void shouldCreateADeleteActionForAVisitOfAChild() throws Exception {
        service.deleteAlertForVisitForChild("Case X", "ANM 1", "OPV 1");

        verify(allAlertActions).add(new AlertAction("Case X", "ANM 1", AlertData.delete("OPV 1")));
    }

    @Test
    public void shouldCreateADeleteActionForAVisitOfAMother() throws Exception {
        when(allMothers.findByCaseId("Case X")).thenReturn(new Mother("Case X", "Thaayi 1", "Theresa").withAnmPhoneNumber("ANM phone no"));

        service.deleteAlertForVisitForMother("Case X", "ANC 1");

        verify(allAlertActions).add(new AlertAction("Case X", "ANM phone no", AlertData.delete("ANC 1")));
    }

    @Test
    public void shouldCreateADeleteAllActionForAMother() throws Exception {
        when(allMothers.findByCaseId("Case X")).thenReturn(new Mother("Case X", "Thaayi 1", "Theresa").withAnmPhoneNumber("ANM phone no"));

        service.deleteAllAlertsForMother("Case X");

        verify(allAlertActions).add(new AlertAction("Case X", "ANM phone no", AlertData.deleteAll()));
    }

    @Test
    public void shouldCreateADeleteAllActionForAChild() throws Exception {
        service.deleteAllAlertsForChild("Case X", "DEMO ANM");

        verify(allAlertActions).add(new AlertAction("Case X", "DEMO ANM", AlertData.deleteAll()));
    }

    @Test
    public void shouldReturnAlertsBasedOnANMIDAndTimeStamp() throws Exception {
        List<AlertAction> alertActions = Arrays.asList(new AlertAction("Case X", "ANM 1", AlertData.create("Theresa", "Thaayi 1", "ANC 1", "due", DateTime.now())));
        when(allAlertActions.findByANMIDAndTimeStamp("ANM 1", 1010101)).thenReturn(alertActions);

        List<AlertAction> alerts = service.getNewAlertsForANM("ANM 1", 1010101);

        assertEquals(1, alerts.size());
        assertEquals(alertActions, alerts);
    }
}
