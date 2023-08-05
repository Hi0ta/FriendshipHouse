package sky.pro.friendshiphouse.serviceTest;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.friendshiphouse.model.Adopter;
import sky.pro.friendshiphouse.model.Volunteer;
import sky.pro.friendshiphouse.service.AdopterService;
import sky.pro.friendshiphouse.service.SchedulingService;
import sky.pro.friendshiphouse.service.VolunteerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulingServiceTests {
    @InjectMocks
    private SchedulingService schedulingService;
    @Mock
    private VolunteerService volunteerService;

    @Mock
    private AdopterService adopterService;

    @Mock
    private TelegramBot telegramBot;

    private Volunteer volunteer = new Volunteer();
    private Adopter adopter = new Adopter();
    private List<Adopter> trialPeriodFinal = new ArrayList<>();

    final Long volunteerChatId = 1234567890L;
    final Long adopterChatId = 9876543210L;
    @Test
    public void testRun() {
        volunteer.setVolunteerChatId(volunteerChatId);
        when(volunteerService.getAllVolunteer()).thenReturn(List.of(volunteer));
        when(volunteerService.getVolunteerById(1)).thenReturn(volunteer);

        adopter.setAdopterChatId(adopterChatId);
        trialPeriodFinal.add(adopter);
        when(adopterService.getAdoptersAvailabilityReport(LocalDate.now())).thenReturn(List.of(adopter));
        when(adopterService.getAdoptersAvailabilityReport(LocalDate.now().minusDays(1))).thenReturn(new ArrayList<>());
        when(adopterService.getAdoptersTrialPeriodFinal()).thenReturn(trialPeriodFinal);

        schedulingService.run();

        verify(telegramBot, times(4)).execute(any(SendMessage.class));
    }
}
