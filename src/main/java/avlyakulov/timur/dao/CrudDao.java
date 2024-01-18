package avlyakulov.timur.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, S> {

    List<T> findAll();

    Optional<T> findById(S id);

    void delete(S id);

    Optional<T> update(S id, T t);

    Optional<T> create(T t);
}