package e.lifeSteal;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private final Map<Class<?>, Object> services = new HashMap<>();

    public <T> void registerService(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }

    public <T> T getService(Class<T> serviceClass) {
        return serviceClass.cast(services.get(serviceClass));
    }
}