package by.pashkevich.mikhail.repository;

import by.pashkevich.mikhail.model.entity.FieldSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldSettingRepository extends JpaRepository<FieldSetting, Long> {
    @Query("""
            select fieldSetting.rowSize
            from FieldSetting fieldSetting
            """)
    List<Integer> findAllRowSize();
}
