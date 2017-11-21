package com.wgl.exam.Repository;


import com.wgl.exam.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByNameAndIsDelete(String name, Integer isDelete);

    Tag findByName(String name);

    @Override
    @Query("select qt from Tag qt where qt.isDelete=0")
    List<Tag> findAll();

    Tag findByIdAndIsDelete(Long id, Integer isDelete);


    @Modifying(clearAutomatically = true)
    @Query("update Tag qt set qt.name = :name where qt.id = :id")
    int update(@Param("id") Long id, @Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Query("update Tag qt set qt.isDelete = 1 where qt.id = :id")
    int del(@Param("id") Long id);

}
