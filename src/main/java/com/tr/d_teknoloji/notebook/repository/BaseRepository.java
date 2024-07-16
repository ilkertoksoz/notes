package com.tr.d_teknoloji.notebook.repository;

import com.tr.d_teknoloji.notebook.exception.AppException;
import com.tr.d_teknoloji.notebook.exception.ResourceNotFoundException;
import com.tr.d_teknoloji.notebook.mapper.BaseEntityMapper;
import com.tr.d_teknoloji.notebook.model.dto.audit.BaseDTO;
import com.tr.d_teknoloji.notebook.model.jpa.audit.BaseEntity;
import com.tr.d_teknoloji.notebook.util.StaticApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<
        T extends BaseEntity,
        D extends BaseDTO,
        I extends Serializable,
        M extends BaseEntityMapper<D, T>>
        extends JpaRepository<T, Serializable> {

    Logger log = LoggerFactory.getLogger(BaseRepository.class);

    default T findByIdEntity(I i) throws ResourceNotFoundException {

        String typeName =
                ((ParameterizedType)
                        ((Class<?>) getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0])
                        .getActualTypeArguments()[0].getTypeName();

        log.debug("Request to get {} : {}", typeName, i);

        Optional<T> result = this.findById(i);
        return result.orElseThrow(
                () -> new ResourceNotFoundException("Resource Type: " + typeName + " - Resource Id: " + i));
    }

    @Transactional(readOnly = true)
    default D findByIdDTO(I i) throws AppException, ClassNotFoundException {
        String mapperName = getMapperName();
        BaseEntityMapper<D, T> mapper = getMapperInstance(mapperName);
        return mapper.toDto(findByIdEntity(i));
    }

    @Transactional
    default D save(D dto) throws ClassNotFoundException {
        String typeName = getMapperName();
        BaseEntityMapper<D, T> mapper = getMapperInstance(typeName);
        log.debug("Request to save D : {}", dto);

        Serializable id = dto.getId();
        T t = (id != null) ? findByIdEntity((I) id) : null;

        if (t == null) {
            t = mapper.toEntity(dto);
        } else {
            t = mapper.toEntity(dto, t);
        }

        T result = save(t);
        return mapper.toDto(result);
    }

    @Transactional
    default List<D> saveAll(List<D> dtoList) throws ClassNotFoundException {
        String typeName = getMapperName();
        BaseEntityMapper<D, T> mapper = getMapperInstance(typeName);
        log.debug("Request to save a list of D : {}", dtoList);

        List<T> entities = new ArrayList<>();
        for (D dto : dtoList) {
            Serializable id = dto.getId();
            T t = (id != null) ? findByIdEntity((I) id) : null;

            if (t == null) {
                t = mapper.toEntity(dto);
            } else {
                t = mapper.toEntity(dto, t);
            }

            entities.add(t);
        }

        List<T> savedEntities = saveAll(entities);
        return mapper.toDto(savedEntities);
    }

    private String getMapperName() {
        return ((ParameterizedType)
                ((Class<?>) getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0])
                .getActualTypeArguments()[3].getTypeName();
    }

    private BaseEntityMapper<D, T> getMapperInstance(String mapperName)
            throws ClassNotFoundException {
        Class<?> mapperClass = Class.forName(mapperName);
        Object mapperObject = StaticApplicationContext.getContext().getBean(mapperClass);

        if (mapperObject instanceof BaseEntityMapper) {
            return castBaseEntityMapper(mapperObject);
        } else {
            throw new IllegalArgumentException(
                    "The bean retrieved from the context is not an instance of BaseEntityMapper.");
        }
    }

    @SuppressWarnings("unchecked")
    private <G1 extends BaseDTO, G2 extends BaseEntity> BaseEntityMapper<G1, G2> castBaseEntityMapper(
            Object mapperObject) {
        return (BaseEntityMapper<G1, G2>) mapperObject;
    }

    @Transactional
    default void delete(I i) {
        this.deleteById(i);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default void deleteAll(List<I> iList) {
        this.deleteAllById(iList);
    }

    @Transactional
    default List<D> findAllD() throws ClassNotFoundException {
        String typeName = getMapperName();
        var mapper = getMapperInstance(typeName);
        return mapper.toDto(this.findAll());
    }

    @Transactional
    default Page<BaseDTO> findAllD(Pageable page) throws ClassNotFoundException {
        String typeName = getMapperName();
        var mapper = getMapperInstance(typeName);
        return this.findAll(page).map(mapper::toDto);
    }
}
