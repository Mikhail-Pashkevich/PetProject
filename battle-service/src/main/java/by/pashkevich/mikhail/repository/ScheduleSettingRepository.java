package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.model.entity.ScheduleSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleSettingRepository extends JpaRepository<ScheduleSetting, Long> {
}
