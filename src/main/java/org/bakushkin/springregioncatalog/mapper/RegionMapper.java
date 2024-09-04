package org.bakushkin.springregioncatalog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bakushkin.springregioncatalog.entity.Region;

import java.util.List;

@Mapper
public interface RegionMapper {

    void save(Region region);

    Region findById(Long id);

    List<Region> findAll();

    void update(Region region);

    void delete(Long id);
}
