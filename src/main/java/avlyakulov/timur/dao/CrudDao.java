package avlyakulov.timur.dao;

import java.util.List;

public interface CrudDao<T, S> {

    List<T> findAll();

    T create(T t);

}