package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.model.entity.FieldSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldSettingRepository extends JpaRepository<FieldSetting, Long> {
}
