package uz.pdp.clickup.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.clickup.domain.ClickApps;
import uz.pdp.clickup.domain.View;
import uz.pdp.clickup.repository.ClickAppsRepo;
import uz.pdp.clickup.repository.ViewRepo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final ViewRepo viewRepo;

    private final ClickAppsRepo clickAppsRepo;

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            List<ClickApps> apps = new ArrayList<>();
            apps.add(new ClickApps("Priority", null));
            apps.add(new ClickApps("Sprints", null));
            apps.add(new ClickApps("Email", null));
            apps.add(new ClickApps("Tags", null));
            apps.add(new ClickApps("Custom fields", null));
            apps.add(new ClickApps("Multiple Assignees", null));
            apps.add(new ClickApps("Time Tracking", null));

            clickAppsRepo.saveAll(apps);

            List<View> views = new ArrayList<>();

            views.add(new View("List", null));
            views.add(new View("Board", null));
            views.add(new View("Calendar", null));
            views.add(new View("Map", null));
            views.add(new View("Activity", null));
            views.add(new View("Box", null));
            views.add(new View("Timeline", null));

            viewRepo.saveAll(views);
        }
    }
}
