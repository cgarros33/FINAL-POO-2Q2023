package frontend.interfaces;

import backend.interfaces.Manipulable;
import frontend.Effects;
import javafx.scene.control.CheckBox;

import java.util.Set;
/**
 * Interfaz que ofrece el comportamiento necesario para
 * aplicar efectos y para agregar tags
 */
public interface EffectApplicableWithTags extends Manipulable {
    /**
     * Asocia el estado del efecto de una figura con el estado de un CheckBox.
     * @param checkBox CheckBox a la cual se asocia el efecto.
     * @param effect Efecto a asociar.
     */
    void bindToCheckBox(CheckBox checkBox, Effects effect);
    void addEffect(Effects effect);
    void removeEffect(Effects effect);
    boolean containsEffect(Effects effect);
    void setTags(Set<String> tags);
    Set<String> getTags();
}
