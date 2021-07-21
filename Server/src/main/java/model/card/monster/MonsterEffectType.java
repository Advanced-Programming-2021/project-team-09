package model.card.monster;

import java.io.Serializable;

public enum MonsterEffectType implements Serializable {
    NONE,
    CONTINUOUS,
    TRIGGER,
    QUICK,
    SPARK,
}
