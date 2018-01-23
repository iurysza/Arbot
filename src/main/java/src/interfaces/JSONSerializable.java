package src.interfaces;

public interface JSONSerializable<T> {

    T fromJSON(String json);
    String toJSON(T object);

}
