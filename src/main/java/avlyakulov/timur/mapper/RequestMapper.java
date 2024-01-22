package avlyakulov.timur.mapper;

public interface RequestMapper <T, S>{
    S mapToEntity(T t);
}
