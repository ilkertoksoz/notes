package com.tr.d_teknoloji.notebook.service;

import com.tr.d_teknoloji.notebook.exception.AppException;
import com.tr.d_teknoloji.notebook.exception.ResourceNotFoundException;
import com.tr.d_teknoloji.notebook.model.dto.audit.BaseDTO;
import com.tr.d_teknoloji.notebook.repository.BaseRepository;
import com.tr.d_teknoloji.notebook.util.StaticApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
@Service
public class BaseService<T extends BaseDTO, R extends BaseRepository, I extends Serializable> {

    @SuppressWarnings("unchecked")
    public T findById(Long id) throws AppException {
        R repository = StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        try {
            return (T) repository.findByIdDTO(id);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public T save(T t) throws AppException {

        R repository = StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        try {
            return (T) repository.save(t);
        }
        catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> saveAll(List<T> dtoList) throws AppException {
        R repository = StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        try {
            return repository.saveAll(dtoList);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void delete(I i) throws AppException {
        R repository = StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        try {
            repository.delete(i);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void deleteAllById(List<I> iList) {
        R repository = StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        try {
            repository.deleteAll(iList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() throws AppException {
        R repository = StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        try {
            return repository.findAllD();
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Page<T> findAll(Pageable pageable) throws AppException {
        R repository = StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        try {
            return repository.findAllD(pageable);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
    }

    private Class<?> getClassType() throws ClassNotFoundException {
        String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
        return Class.forName(className);
    }

    @SuppressWarnings("unchecked")
    private Class<R> getRGenericTypeClass() {
        try {
            Class<?> clazz = getClassType();
            return (Class<R>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
}
