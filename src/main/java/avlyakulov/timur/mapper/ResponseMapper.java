package avlyakulov.timur.mapper;

public interface ResponseMapper<T, S> {
    S mapToResponse(T t);
}
