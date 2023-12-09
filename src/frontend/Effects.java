package frontend;

import frontend.interfaces.EffectsDrawable;

/**
 * Enum que brinda soporte para aplicar los efectos que corresponda.
 */
public enum Effects {
    SHADOW{
        @Override
        public void apply(EffectsDrawable figure){
            figure.drawShadow();
        }
    },
    GRADIENT{
        @Override
        public void apply(EffectsDrawable figure){
            figure.drawGradient();
        }
    },
    BEZEL{
        @Override
        public void apply(EffectsDrawable figure){
            figure.drawBezel();
        }
    };

    public abstract void apply(EffectsDrawable figure);

}
