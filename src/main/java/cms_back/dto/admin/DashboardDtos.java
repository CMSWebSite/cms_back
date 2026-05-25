package cms_back.dto.admin;

import java.time.LocalDateTime;
import java.util.List;

public final class DashboardDtos {

    private DashboardDtos() {}

    public record Summary(
            long newsCount,
            long journalsCount,
            long conferencesCount,
            long patentsCount,
            long projectsCount,
            long othersCount,
            long activeStudentsCount,
            long alumniCount,
            long facilitiesCount,
            long albumsCount,
            long qnaUnansweredCount,
            long partnersCount,
            long usersCount,
            List<Activity> recentActivity
    ) {}

    /**
     * 최근 활동 항목.
     * resource: "news" | "journal" | "student"
     */
    public record Activity(
            String resource,
            Long id,
            String label,
            LocalDateTime at
    ) {}
}
