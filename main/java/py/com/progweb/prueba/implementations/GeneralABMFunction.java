package py.com.progweb.prueba.implementations;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;

public class GeneralABMFunction<T> {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;
    public void modificar(T existingClient, T updatedClient) throws IllegalAccessException {
        // Iterate through the fields of the updated Client object
        for (Field field : existingClient.getClass().getDeclaredFields()) {
            // Set the field to be accessible, as it may be private or protected
            field.setAccessible(true);

            // Check if the field in the updated Client object is not null
            Object fieldValue = field.get(updatedClient);
            if (fieldValue != null && !field.getName().equals("id")) {
                // Update the corresponding field in the existing Client entity
                field.set(existingClient, fieldValue);
            }
        }

        // Save the updated Client entity back to the database
        em.merge(existingClient);
    }
}