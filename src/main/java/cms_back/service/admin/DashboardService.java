package cms_back.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cms_back.domain.StudentStatus;
import cms_back.dto.admin.DashboardDtos;
import cms_back.repository.ConferenceRepository;
import cms_back.repository.GalleryAlbumRepository;
import cms_back.repository.FacilityRepository;
import cms_back.repository.JournalRepository;
import cms_back.repository.NewsRepository;
import cms_back.repository.OtherAchievementRepository;
import cms_back.repository.PartnerRepository;
import cms_back.repository.PatentRepository;
import cms_back.repository.ProjectRepository;
import cms_back.repository.QnaPostRepository;
import cms_back.repository.StudentRepository;
import cms_back.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DashboardService {

    private final NewsRepository newsRepository;
    private final JournalRepository journalRepository;
    private final ConferenceRepository conferenceRepository;
    private final PatentRepository patentRepository;
    private final ProjectRepository projectRepository;
    private final OtherAchievementRepository otherRepository;
    private final StudentRepository studentRepository;
    private final FacilityRepository facilityRepository;
    private final GalleryAlbumRepository galleryRepository;
    private final QnaPostRepository qnaRepository;
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;

    public DashboardService(NewsRepository newsRepository,
                            JournalRepository journalRepository,
                            ConferenceRepository conferenceRepository,
                            PatentRepository patentRepository,
                            ProjectRepository projectRepository,
                            OtherAchievementRepository otherRepository,
                            StudentRepository studentRepository,
                            FacilityRepository facilityRepository,
                            GalleryAlbumRepository galleryRepository,
                            QnaPostRepository qnaRepository,
                            PartnerRepository partnerRepository,
                            UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.journalRepository = journalRepository;
        this.conferenceRepository = conferenceRepository;
        this.patentRepository = patentRepository;
        this.projectRepository = projectRepository;
        this.otherRepository = otherRepository;
        this.studentRepository = studentRepository;
        this.facilityRepository = facilityRepository;
        this.galleryRepository = galleryRepository;
        this.qnaRepository = qnaRepository;
        this.partnerRepository = partnerRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public DashboardDtos.Summary summary() {
        long newsCount = newsRepository.count();
        long journalsCount = journalRepository.count();
        long conferencesCount = conferenceRepository.count();
        long patentsCount = patentRepository.count();
        long projectsCount = projectRepository.count();
        long othersCount = otherRepository.count();
        long activeStudents = studentRepository.countByStatus(StudentStatus.ACTIVE);
        long alumniCount = studentRepository.countByStatus(StudentStatus.ALUMNI);
        long facilitiesCount = facilityRepository.count();
        long albumsCount = galleryRepository.count();
        long qnaUnansweredCount = qnaRepository.countByAnsweredFalse();
        long partnersCount = partnerRepository.count();
        long usersCount = userRepository.count();

        List<DashboardDtos.Activity> recent = new ArrayList<>();
        newsRepository.findAllByOrderByPublishedAtDesc().stream().limit(5)
                .forEach(n -> recent.add(new DashboardDtos.Activity(
                        "news", n.getId(), n.getTitle(), n.getUpdatedAt())));
        journalRepository.findAllByOrderByPublishedDateDesc().stream().limit(5)
                .forEach(j -> recent.add(new DashboardDtos.Activity(
                        "journal", j.getId(), j.getTitle1(), j.getUpdatedAt())));
        studentRepository.findAllByOrderByEnrolledAtDesc().stream().limit(5)
                .forEach(s -> recent.add(new DashboardDtos.Activity(
                        "student", s.getId(), s.getKoreanName() + " (" + s.getEnglishName() + ")",
                        s.getUpdatedAt())));

        recent.sort(Comparator.comparing(DashboardDtos.Activity::at).reversed());
        List<DashboardDtos.Activity> top5 = recent.stream().limit(5).toList();

        return new DashboardDtos.Summary(
                newsCount, journalsCount, conferencesCount, patentsCount,
                projectsCount, othersCount, activeStudents, alumniCount,
                facilitiesCount, albumsCount, qnaUnansweredCount, partnersCount,
                usersCount, top5
        );
    }
}
