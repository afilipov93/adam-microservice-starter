package com.acroteq.ticketing.infrastructure.data.access.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.application.repository.WriteRepository;
import com.acroteq.ticketing.domain.entity.Entity;
import com.acroteq.ticketing.domain.valueobject.BaseId;
import com.acroteq.ticketing.infrastructure.data.access.entity.JpaEntity;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class ReadWriteRepositoryImpl<IdT extends BaseId, EntityT extends Entity<IdT>, JpaT extends JpaEntity>
    implements ReadRepository<IdT, EntityT>, WriteRepository<IdT, EntityT> {

  private final ReadRepository<IdT, EntityT> readRepository;
  private final WriteRepository<IdT, EntityT> writeRepository;

  public ReadWriteRepositoryImpl(final JpaRepository<JpaT, Long> jpaRepository,
                                 final JpaToDomainMapper<JpaT, EntityT> jpaToDomainMapper,
                                 final DomainToJpaMapper<EntityT, JpaT> domainToJpaMapper) {
    readRepository = new ReadRepositoryImpl<>(jpaRepository, jpaToDomainMapper);
    writeRepository = new WriteRepositoryImpl<>(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
  }

  @Override
  public Optional<EntityT> findById(final IdT entityId) {
    return readRepository.findById(entityId);
  }

  @Override
  public boolean existsById(final IdT entityId) {
    return readRepository.existsById(entityId);
  }

  @Override
  public EntityT save(final EntityT entity) {
    return writeRepository.save(entity);
  }

  @Override
  public void deleteById(final IdT entityId) {
    writeRepository.deleteById(entityId);
  }
}
