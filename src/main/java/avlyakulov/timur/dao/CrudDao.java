package avlyakulov.timur.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> findById(long id);

    List<T> findAll();

    T create(T t);

    T update(T t);
}