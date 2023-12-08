package frontend.interfaces;

import java.util.Set;

public interface Taggable extends EffectApplicable{
    void setTags(Set<String> tags);
    Set<String> getTags();
}
