package internetprovider.common.entity.sequence;

import java.util.Collection;

public class Sequence {
    private int maxId;

    public Sequence(Collection<? extends ProviderEntity> entities) {
        if(entities.isEmpty()){
            maxId = 0;
        } else {
            maxId = Integer.MIN_VALUE;
            Integer id;
            for (ProviderEntity entity : entities) {
                id = entity.getId();
                if(id.compareTo(maxId) > 0)
                    maxId = id;
            }
        }
    }

    public int getNextId(){
        return ++maxId;
    }
}
