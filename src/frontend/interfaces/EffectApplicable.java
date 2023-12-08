package frontend.interfaces;

import backend.interfaces.Manipulable;
import frontend.Effects;
import javafx.scene.control.CheckBox;

public interface EffectApplicable extends Manipulable {
    void bindToCheckBox(CheckBox checkBox, Effects effect);
    void addEffect(Effects effect);
    void removeEffect(Effects effect);
    boolean containsEffect(Effects effect);
}
